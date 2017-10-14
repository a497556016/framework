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
import com.framework.model.BasicInfo;
import com.framework.service.BasicInfoService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("basicInfo")
public class BasicInfoController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private BasicInfoService basicInfoService;

	@RequestMapping("toBasicInfoManage")
	public String toBasicInfoManage(){
		return "framework/basicInfo/basicInfoManage";
	}

	@RequestMapping("queryBasicInfoList")
	@ResponseBody
	public Page<BasicInfo> queryBasicInfoList(Page<BasicInfo> page){
		try {
			Map<String,Object> params = super.getMapParams();
			basicInfoService.queryBasicInfoList(page,params);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
	
	@RequestMapping("addBasicInfo")
	@ResponseBody
	public ResultMessage<BasicInfo> addBasicInfo(String itemsJson){
		ResultMessage<BasicInfo> rm = new ResultMessage<>();
		try {
			List<BasicInfo> basicInfos = gson.fromJson(itemsJson, new TypeToken<List<BasicInfo>>(){}.getType());
			rm = basicInfoService.addBasicInfo(basicInfos);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("updateBasicInfo")
	@ResponseBody
	public ResultMessage<?> updateBasicInfo(String itemsJson){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			List<BasicInfo> basicInfos = gson.fromJson(itemsJson, new TypeToken<List<BasicInfo>>(){}.getType());
			rm = basicInfoService.updateBasicInfo(basicInfos);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("deleteBasicInfos")
	@ResponseBody
	public ResultMessage<?> deleteBasicInfos(String basicInfosJson){
		ResultMessage<?> rm = null;
		try {
			List<BasicInfo> basicInfos = gson.fromJson(basicInfosJson, new TypeToken<List<BasicInfo>>(){}.getType());
			rm = basicInfoService.deleteBasicInfos(basicInfos);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("getBasicInfo")
	@ResponseBody
	public ResultMessage<BasicInfo> getBasicInfo(BasicInfo basicInfo){
		ResultMessage<BasicInfo> resultMessage = new ResultMessage<>();
		try {
			BasicInfo basicInfo1 = basicInfoService.getBasicInfo(basicInfo);
			resultMessage.setModel(basicInfo1);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
	
	@RequestMapping("getBasicTypes")
	@ResponseBody
	public Page<BasicInfo> getBasicTypes(Page<BasicInfo> page){
		try {
			Map<String,Object> params = super.getMapParams();
			List<BasicInfo> basicInfos = basicInfoService.getBasicTypes(page,params);
			page.setSuccess(true);
			page.setRows(basicInfos);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
}
