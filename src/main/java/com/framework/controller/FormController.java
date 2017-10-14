package com.framework.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.framework.bean.FormProduceInfo;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.model.ProField;
import com.framework.model.ProForm;
import com.framework.service.FormService;
import com.google.gson.reflect.TypeToken;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
@RequestMapping("/form")
public class FormController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private FormService formService;
	
	@RequestMapping("/toFormProduce")
	public String toFormProduce(){
		return "onlineProduce/form/formProduce";
	}
	
	@RequestMapping("/toCreateForm")
	public ModelAndView toCreateForm(String tableName) throws Exception{
		ModelAndView view = new ModelAndView("onlineProduce/form/createForm");
		
		List<ProField> fields = formService.getTableFields(tableName);
		view.addObject("fields", gson.toJson(fields));
		view.addObject("tableName", tableName);
		
		return view;
	}
	
	@RequestMapping("/toEditForm")
	public ModelAndView toEditForm(Integer formId) throws Exception{
		ModelAndView view = new ModelAndView("onlineProduce/form/editForm");
		
		ProForm form = formService.getForm(formId);
		List<ProField> fields = formService.getFormFields(formId);
		view.addObject("fields", gson.toJson(fields));
		view.addObject("form", gson.toJson(form));
		
		return view;
	}
	
	@RequestMapping("/queryFormList")
	@ResponseBody
	public Page<ProForm> queryFormList(Page<ProForm> page,ProForm proForm){
		try{
			formService.queryFormList(page,proForm);
			page.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return page;
	}
	
	@RequestMapping("/produceCodes")
	@ResponseBody
	public ResultMessage<?> produceCodes(FormProduceInfo formProduceInfo){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			formService.produceCodes(formProduceInfo);
			
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("/saveForm")
	@ResponseBody
	public ResultMessage<?> saveForm(ProForm proForm,String fieldsJson){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			List<ProField> fields = gson.fromJson(fieldsJson, new TypeToken<List<ProField>>(){}.getType());
			formService.saveForm(proForm,fields);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
}
