package com.${packageName}.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.controller.BaseController;
import com.${packageName}.model.${className};
import com.${packageName}.model.vo.${className}VO;
import com.framework.service.CommonService;
import com.${packageName}.service.${className}Service;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("${className?uncap_first}")
public class ${className}Controller extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private ${className}Service ${className?uncap_first}Service;

	@RequestMapping("to${className}Manage")
	public String to${className}Manage(){
		return "${packageName}/${className?uncap_first}/${className?uncap_first}Manage";
	}

	@RequestMapping("query${className}List")
	@ResponseBody
	public Page<${className}VO> query${className}List(Page<${className}VO> page){
		try {
			Map<String,Object> params = super.getMapParams();
			${className?uncap_first}Service.query${className}List(page,params);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
	
	@RequestMapping("add${className}")
	@ResponseBody
	public ResultMessage<${className}VO> add${className}(${className}VO ${className?uncap_first}){
		ResultMessage<${className}VO> rm = new ResultMessage<>();
		try {
			rm = ${className?uncap_first}Service.add${className}(${className?uncap_first});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("update${className}")
	@ResponseBody
	public ResultMessage<?> update${className}(${className}VO ${className?uncap_first}){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			rm = ${className?uncap_first}Service.update${className}(${className?uncap_first});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("delete${className}s")
	@ResponseBody
	public ResultMessage<?> delete${className}s(String ${className?uncap_first}sJson){
		ResultMessage<?> rm = null;
		try {
			List<${className}VO> ${className?uncap_first}s = gson.fromJson(${className?uncap_first}sJson, new TypeToken<List<${className}VO>>(){}.getType());
			rm = ${className?uncap_first}Service.delete${className}s(${className?uncap_first}s);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("get${className}")
	@ResponseBody
	public ResultMessage<${className}VO> get${className}(${className}VO ${className?uncap_first}){
		ResultMessage<${className}VO> resultMessage = new ResultMessage<>();
		try {
			${className}VO ${className?uncap_first}1 = ${className?uncap_first}Service.get${className}(${className?uncap_first});
			resultMessage.setModel(${className?uncap_first}1);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
}
