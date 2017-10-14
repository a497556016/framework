package com.framework.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.bean.FormProduceInfo;
import com.framework.bean.common.Page;
import com.framework.dao.ProFieldMapper;
import com.framework.dao.ProFormMapper;
import com.framework.model.ProField;
import com.framework.model.ProForm;
import com.framework.service.FormService;
import com.framework.util.CamelCaseUtils;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class FormServiceImpl implements FormService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ProFieldMapper proFieldMapper;
	@Autowired
	private ProFormMapper proFormMapper;
	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	@Override
	public List<ProField> getTableFields(String tableName) throws Exception {
		List<ProField> fields = proFieldMapper.getTableFields(tableName);
		for (ProField proField : fields) {
			if(!proField.isPri()){
				proField.setGridCol(true);
				proField.setFormField(true);
				proField.setFieldType("textfield");
			}
			proField.setQuery(false);
			proField.setColumnName(proField.getName());
			proField.setName(CamelCaseUtils.toCamelCase(proField.getName()));
		}
		return fields;
	}

	@Override
	public void saveForm(ProForm proForm, List<ProField> fields) throws Exception {
		if(null!=proForm.getId()&&proForm.getId()>0){
			//更新
			proForm.setModifyBy(LoginUtils.getUserInfo().getPersonCode());
			proForm.setModifyDate(new Date());
			proFormMapper.updateByPrimaryKeySelective(proForm);
			for (ProField proField : fields) {
				proField.setModifyBy(LoginUtils.getUserInfo().getPersonCode());
				proField.setModifyDate(new Date());
				proFieldMapper.updateByPrimaryKeySelective(proField);
			}
		}else{
			//新增
			proForm.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
			proForm.setCreateDate(new Date());
			int version = proFormMapper.getTableFormVersion(proForm.getTableName());
			proForm.setVersion(version);
			int i = proFormMapper.insert(proForm);
			if(i>0&&null!=proForm.getId()){
				for (ProField proField : fields) {
					proField.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
					proField.setCreateDate(new Date());
					proField.setFormId(proForm.getId());
					proFieldMapper.insert(proField);
				}
			}
		}
	}

	@Override
	public void queryFormList(Page<ProForm> page, ProForm proForm) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<ProForm> page2 = PageFactory.createFrom(page);
		EntityWrapper<ProForm> entityWrapper = new EntityWrapper<>();
		if(StringUtils.isNotEmpty(proForm.getName())){
			entityWrapper.like("name", proForm.getName());
		}
		if(StringUtils.isNotEmpty(proForm.getTableName())){
			entityWrapper.like("table_name", proForm.getTableName());
		}
		List<ProForm> forms = proFormMapper.selectPage(page2, entityWrapper);
		page.setRows(forms);
		page.setTotal(page2.getTotal());
	}

	@Override
	public ProForm getForm(Integer formId) throws Exception {
		return proFormMapper.selectById(formId);
	}

	@Override
	public List<ProField> getFormFields(Integer formId) throws Exception {
		EntityWrapper<ProField> wrapper = new EntityWrapper<>();
		wrapper.eq("form_id", formId);
		return proFieldMapper.selectList(wrapper);
	}

	@Override
	public void produceCodes(FormProduceInfo formProduceInfo) throws Exception {
		Configuration configuration = freemarkerConfigurer.getConfiguration();
		
		this.createJavaCodes(formProduceInfo,configuration);
		
		
	}

	/**
	 * 
	 * @param formProduceInfo
	 * @param configuration
	 * @throws Exception
	 */
	private void createJavaCodes(FormProduceInfo formProduceInfo, Configuration configuration) throws Exception {
		Map<String, Object> modelMap = new HashMap<>();
		ProForm form = this.getForm(formProduceInfo.getFormId());
		List<ProField> fields = this.getFormFields(formProduceInfo.getFormId());
		ProField priField = new ProField();
		for (ProField proField : fields) {
			if("int".equals(proField.getDataType().toLowerCase())
					||"bigint".equals(proField.getDataType().toLowerCase())){
				proField.setDataType("integer");
			}
			if("datetime".equals(proField.getDataType().toLowerCase())){
				proField.setDataType("timestamp");
			}
			if("mediumtext".equals(proField.getDataType().toLowerCase())){
				proField.setDataType("varchar");
			}
			if(proField.getDataType().toLowerCase().contains("blob")){
				proField.setDataType("blob");
			}
			if(proField.isPri()){
				priField = proField;
			}
		}
		modelMap.put("form", form);
		modelMap.put("priField", priField);
		modelMap.put("className", formProduceInfo.getClassName());
		modelMap.put("packageName", formProduceInfo.getPackageName());
		modelMap.put("fieldList", fields);
		
		if(formProduceInfo.getCodeFileType().contains("java")){
			String filePath = formProduceInfo.getJavaFilePath();
			String packageName = formProduceInfo.getPackageName();
			if(StringUtils.isNotEmpty(packageName)){
				filePath += ("/"+packageName.replace(".", "/"));
			}
			String controllerPath = filePath + "/controller/"+formProduceInfo.getClassName()+"Controller.java";
			writeCodeFile("default_controller_template.ftl",controllerPath,modelMap);
			
			String servicePath = filePath + "/service/"+formProduceInfo.getClassName()+"Service.java";
			writeCodeFile("default_service_template.ftl",servicePath,modelMap);
			
			String modelPath = filePath + "/model/"+formProduceInfo.getClassName()+".java";
			writeCodeFile("default_model_template.ftl",modelPath,modelMap);
			
			String voPath = filePath + "/model/vo/"+formProduceInfo.getClassName()+"VO.java";
			writeCodeFile("default_vo_template.ftl",voPath,modelMap);
			
			String serviceImplPath = filePath + "/service/impl/"+formProduceInfo.getClassName()+"ServiceImpl.java";
			writeCodeFile("default_service_impl_template.ftl",serviceImplPath,modelMap);
			
			String daoPath = filePath + "/dao/"+formProduceInfo.getClassName()+"Mapper.java";
			writeCodeFile("default_dao_template.ftl",daoPath,modelMap);
			
		}
		if(formProduceInfo.getCodeFileType().contains("mapperXml")){
			String filePath = formProduceInfo.getMapperXmlFilePath();
			String packageName = formProduceInfo.getPackageName();
			if(StringUtils.isNotEmpty(packageName)){
				filePath += ("/"+packageName.replace(".", "/"));
			} 
			writeCodeFile("default_mapper_xml_template.ftl",filePath+"/"+formProduceInfo.getClassName()+".xml",modelMap);
		}
		if(formProduceInfo.getCodeFileType().contains("jsp")){
			String filePath = formProduceInfo.getJspFilePath();
			String packageName = formProduceInfo.getPackageName();
			if(StringUtils.isNotEmpty(packageName)){
				filePath += ("/"+packageName.replace(".", "/"));
			} 
			String subPackageName = formProduceInfo.getClassName().substring(0,1).toLowerCase()+formProduceInfo.getClassName().substring(1);
			filePath += ("/"+subPackageName);
			writeCodeFile("default_jsp_template.ftl",filePath+"/"+subPackageName+"Manage.jsp",modelMap);
		}
		if(formProduceInfo.getCodeFileType().contains("js")){
			String filePath = formProduceInfo.getJsFilePath();
			String packageName = formProduceInfo.getPackageName();
			if(StringUtils.isNotEmpty(packageName)){
				filePath += ("/"+packageName.replace(".", "/"));
			} 
			String subPackageName = formProduceInfo.getClassName().substring(0,1).toLowerCase()+formProduceInfo.getClassName().substring(1);
			filePath += ("/"+subPackageName);
			writeCodeFile("default_js_template.ftl",filePath+"/"+subPackageName+"Manage.js",modelMap);
		}
	}

	private void writeCodeFile(String tplName, String path, Map<String, Object> modelMap) {
		try {
			StringWriter swriter = new StringWriter();
			Template template = freemarkerConfigurer.getConfiguration().getTemplate(tplName, "UTF-8");
			template.process(modelMap, swriter);
			
			File file = new File(path);
			FileUtils.writeStringToFile(file, swriter.toString(), "UTF-8");
			
			IOUtils.closeQuietly(swriter);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
}
