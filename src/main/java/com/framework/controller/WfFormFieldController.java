package com.framework.controller;

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
import com.framework.model.WfFormField;
import com.framework.model.vo.WfFormFieldVO;
import com.framework.service.CommonService;
import com.framework.service.WfFormFieldService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("wfFormField")
public class WfFormFieldController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private WfFormFieldService wfFormFieldService;

	@RequestMapping("toWfFormFieldManage")
	public String toWfFormFieldManage(){
		return "framework/wfFormField/wfFormFieldManage";
	}

	@RequestMapping("queryWfFormFieldList")
	@ResponseBody
	public Page<WfFormFieldVO> queryWfFormFieldList(Page<WfFormFieldVO> page){
		try {
			Map<String,Object> params = super.getMapParams();
			wfFormFieldService.queryWfFormFieldList(page,params);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
	
	@RequestMapping("addWfFormField")
	@ResponseBody
	public ResultMessage<WfFormFieldVO> addWfFormField(WfFormFieldVO wfFormField){
		ResultMessage<WfFormFieldVO> rm = new ResultMessage<>();
		try {
			rm = wfFormFieldService.addWfFormField(wfFormField);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("updateWfFormField")
	@ResponseBody
	public ResultMessage<?> updateWfFormField(WfFormFieldVO wfFormField){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			rm = wfFormFieldService.updateWfFormField(wfFormField);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("deleteWfFormFields")
	@ResponseBody
	public ResultMessage<?> deleteWfFormFields(String wfFormFieldsJson){
		ResultMessage<?> rm = null;
		try {
			List<WfFormFieldVO> wfFormFields = gson.fromJson(wfFormFieldsJson, new TypeToken<List<WfFormFieldVO>>(){}.getType());
			rm = wfFormFieldService.deleteWfFormFields(wfFormFields);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("getWfFormField")
	@ResponseBody
	public ResultMessage<WfFormFieldVO> getWfFormField(WfFormFieldVO wfFormField){
		ResultMessage<WfFormFieldVO> resultMessage = new ResultMessage<>();
		try {
			WfFormFieldVO wfFormField1 = wfFormFieldService.getWfFormField(wfFormField);
			resultMessage.setModel(wfFormField1);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
}
