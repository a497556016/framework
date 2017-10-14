package com.act.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.service.ActProcessService;
import com.framework.bean.common.Page;
import com.framework.bean.vo.ComboVo;
import com.framework.controller.BaseController;

@Controller
@RequestMapping("/actProcess")
public class ActProcessController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ActProcessService actProcessService;
	
	@RequestMapping("/getProcessKeys")
	@ResponseBody
	public Page<ComboVo> getProcessKeys(){
		Page<ComboVo> page = new Page<>();
		try{
			List<ComboVo> list = actProcessService.getProcessKeys();
			page.setRows(list);
			page.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return page;
	}
	
	@RequestMapping("/getProcessTasks")
	@ResponseBody
	public Page<ComboVo> getProcessTasks(String processKey){
		Page<ComboVo> page = new Page<>();
		try{
			List<ComboVo> list = actProcessService.getProcessTasks(processKey);
			page.setRows(list);
			page.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return page;
	}
}
