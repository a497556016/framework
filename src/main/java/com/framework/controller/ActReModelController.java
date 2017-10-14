package com.framework.controller;

import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.Model;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.service.ActModelService;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.controller.BaseController;
import com.framework.model.ActReModel;
import com.framework.service.CommonService;
import com.framework.service.ActReModelService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("actReModel")
public class ActReModelController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private ActReModelService actReModelService;
	@Autowired
	private ActModelService actModelService;

	@RequestMapping("toActReModelManage")
	public String toActReModelManage(){
		return "framework/actReModel/actReModelManage";
	}

	@RequestMapping("queryActReModelList")
	@ResponseBody
	public Page<ActReModel> queryActReModelList(Page<ActReModel> page){
		try {
			Map<String,Object> params = super.getMapParams();
			actReModelService.queryActReModelList(page,params);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
	
	@RequestMapping("addActReModel")
	@ResponseBody
	public ResultMessage<String> addActReModel(String name, String key, String description, String category){
		ResultMessage<String> rm = new ResultMessage<>();
		try {
			Model model = actModelService.create(name, key, description, category);
			rm.setModel(model.getId());
			rm.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("updateActReModel")
	@ResponseBody
	public ResultMessage<?> updateActReModel(ActReModel actReModel){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			actModelService.updateCategory(actReModel.getId(), actReModel.getCategory());
			actReModelService.updateActReModel(actReModel);
			rm.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("deleteActReModels")
	@ResponseBody
	public ResultMessage<?> deleteActReModels(String actReModelsJson){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			List<ActReModel> actReModels = gson.fromJson(actReModelsJson, new TypeToken<List<ActReModel>>(){}.getType());
			for (ActReModel actReModel : actReModels) {
				actModelService.delete(actReModel.getId());
			}
			rm.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("getActReModel")
	@ResponseBody
	public ResultMessage<ActReModel> getActReModel(ActReModel actReModel){
		ResultMessage<ActReModel> resultMessage = new ResultMessage<>();
		try {
			ActReModel actReModel1 = actReModelService.getActReModel(actReModel);
			resultMessage.setModel(actReModel1);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
	
	@RequestMapping("deploy")
	@ResponseBody
	public ResultMessage<?> deploy(String id){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			actModelService.deploy(id);
			rm.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
}
