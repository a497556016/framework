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
import com.framework.model.ActReProcdef;
import com.framework.service.CommonService;
import com.framework.service.ActReProcdefService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("actReProcdef")
public class ActReProcdefController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private ActReProcdefService actReProcdefService;

	@RequestMapping("toActReProcdefManage")
	public String toActReProcdefManage(){
		return "framework/actReProcdef/actReProcdefManage";
	}

	@RequestMapping("queryActReProcdefList")
	@ResponseBody
	public Page<ActReProcdef> queryActReProcdefList(Page<ActReProcdef> page){
		try {
			Map<String,Object> params = super.getMapParams();
			actReProcdefService.queryActReProcdefList(page,params);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
	
	@RequestMapping("addActReProcdef")
	@ResponseBody
	public ResultMessage<ActReProcdef> addActReProcdef(ActReProcdef actReProcdef){
		ResultMessage<ActReProcdef> rm = new ResultMessage<>();
		try {
			rm = actReProcdefService.addActReProcdef(actReProcdef);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("updateActReProcdef")
	@ResponseBody
	public ResultMessage<?> updateActReProcdef(ActReProcdef actReProcdef){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			rm = actReProcdefService.updateActReProcdef(actReProcdef);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("deleteActReProcdefs")
	@ResponseBody
	public ResultMessage<?> deleteActReProcdefs(String actReProcdefsJson){
		ResultMessage<?> rm = null;
		try {
			List<ActReProcdef> actReProcdefs = gson.fromJson(actReProcdefsJson, new TypeToken<List<ActReProcdef>>(){}.getType());
			rm = actReProcdefService.deleteActReProcdefs(actReProcdefs);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("getActReProcdef")
	@ResponseBody
	public ResultMessage<ActReProcdef> getActReProcdef(ActReProcdef actReProcdef){
		ResultMessage<ActReProcdef> resultMessage = new ResultMessage<>();
		try {
			ActReProcdef actReProcdef1 = actReProcdefService.getActReProcdef(actReProcdef);
			resultMessage.setModel(actReProcdef1);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
}
