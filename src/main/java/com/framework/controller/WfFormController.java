package com.framework.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.controller.BaseController;
import com.framework.model.WfForm;
import com.framework.model.vo.WfFormFieldVO;
import com.framework.model.vo.WfFormVO;
import com.framework.service.CommonService;
import com.framework.service.WfFormService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("wfForm")
public class WfFormController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private WfFormService wfFormService;

	@RequestMapping("toWfFormManage")
	public String toWfFormManage(){
		return "framework/wfForm/wfFormManage";
	}

	@RequestMapping("queryWfFormList")
	@ResponseBody
	public Page<WfFormVO> queryWfFormList(Page<WfFormVO> page){
		try {
			Map<String,Object> params = super.getMapParams();
			wfFormService.queryWfFormList(page,params);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
	
	@RequestMapping("addWfForm")
	@ResponseBody
	public ResultMessage<WfFormVO> addWfForm(WfFormVO wfForm,String fieldsJson){
		ResultMessage<WfFormVO> rm = new ResultMessage<>();
		try {
			List<WfFormFieldVO> fields = gson.fromJson(fieldsJson, new TypeToken<List<WfFormFieldVO>>(){}.getType());
			for (WfFormFieldVO wfFormFieldVO : fields) {
				wfFormFieldVO.setEditTask(StringUtils.join(wfFormFieldVO.getEditTasks()));
			}
			wfForm.setFields(fields);
			rm = wfFormService.addWfForm(wfForm);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("updateWfForm")
	@ResponseBody
	public ResultMessage<?> updateWfForm(WfFormVO wfForm){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			rm = wfFormService.updateWfForm(wfForm);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("deleteWfForms")
	@ResponseBody
	public ResultMessage<?> deleteWfForms(String wfFormsJson){
		ResultMessage<?> rm = null;
		try {
			List<WfFormVO> wfForms = gson.fromJson(wfFormsJson, new TypeToken<List<WfFormVO>>(){}.getType());
			rm = wfFormService.deleteWfForms(wfForms);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("getWfForm")
	@ResponseBody
	public ResultMessage<WfFormVO> getWfForm(WfFormVO wfForm){
		ResultMessage<WfFormVO> resultMessage = new ResultMessage<>();
		try {
			WfFormVO wfForm1 = wfFormService.getWfForm(wfForm);
			resultMessage.setModel(wfForm1);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
}
