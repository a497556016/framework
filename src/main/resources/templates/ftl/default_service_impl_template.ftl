package com.${packageName}.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.${packageName}.dao.${className}Mapper;
import com.${packageName}.model.${className};
import com.${packageName}.model.vo.${className}VO;
import com.framework.service.CommonService;
import com.${packageName}.service.${className}Service;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class ${className}ServiceImpl implements ${className}Service {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ${className}Mapper ${className?uncap_first}Mapper;
	@Autowired
	private CommonService commonService;
	
	public void query${className}List(Page<${className}VO> page, Map<String,Object> params) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<${className}VO> mppage = PageFactory.createFrom(page);
		
		List<${className}VO> list = ${className?uncap_first}Mapper.query${className}List(mppage, params);

		page.setRows(list);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<${className}VO> add${className}(${className}VO ${className?uncap_first}) throws Exception {
		ResultMessage<${className}VO> resultMessage = new ResultMessage<>();
		int i = ${className?uncap_first}Mapper.insertSelective(${className?uncap_first});
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}


	public ResultMessage<?> delete${className}s(List<${className}VO> ${className?uncap_first}s) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (${className}VO ${className?uncap_first} : ${className?uncap_first}s) {
			int i = ${className?uncap_first}Mapper.deleteById(${className?uncap_first}.getId());
			count += i;
		}
		if(count==${className?uncap_first}s.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}

	@Override
	public ResultMessage<?> update${className}(${className}VO ${className?uncap_first}) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int i = 0;
		if(null==${className?uncap_first}.get${priField.name?cap_first}()||${className?uncap_first}.get${priField.name?cap_first}()<=0){
			i += ${className?uncap_first}Mapper.insertSelective(${className?uncap_first});
		}else{
			i += ${className?uncap_first}Mapper.updateByPrimaryKeySelective(${className?uncap_first});
		}
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}

	@Override
	public ${className}VO get${className}(${className}VO ${className?uncap_first}) throws Exception {
		
		return ${className?uncap_first}Mapper.selectByPrimaryKey(${className?uncap_first}.get${priField.name?cap_first}());
	}


}
