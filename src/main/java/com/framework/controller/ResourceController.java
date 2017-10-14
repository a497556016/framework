package com.framework.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysMenuVo;
import com.framework.bean.vo.SysResourceTreeVo;
import com.framework.bean.vo.SysResourceVo;
import com.framework.exception.PrintMessageException;
import com.framework.model.SysResource;
import com.framework.service.ResourceService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("res")
public class ResourceController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping("toResourceManage")
	public String toResourceManage(){
		return "system/res/resourceManage";
	}
	
	@RequestMapping("toAddRes")
	public String toAddRes(){
		return "system/res/addRes";
	}
	
	@RequestMapping("toEditRes")
	public String toEditRes(Model model,Integer id){
		if(null==id||id==0){
			throw new PrintMessageException("输入资源ID为空！");
		}
		SysResource resource = resourceService.selectResourceById(id);
		model.addAttribute("resource", resource);
		return "system/res/editRes";
	}
	
	@RequestMapping("queryResList")
	@ResponseBody
	public Page<SysResource> queryResList(Page<SysResource> page,SysResource resource){
		resourceService.queryResList(page,resource);
		return page;
	}
	
	@RequestMapping("/queryResListByPCode")
	@ResponseBody
	public List<SysResourceTreeVo> queryResListByPCode(String pResCode){
		List<SysResourceTreeVo> menus = new ArrayList<>();
		try {
			menus = resourceService.queryResListByPCode(pResCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}
	
	@RequestMapping("addRes")
	@ResponseBody
	public ResultMessage<SysResourceVo> addRes(SysResourceVo resource){
		ResultMessage<SysResourceVo> rm = resourceService.addRes(resource);
		return rm;
	}
	
	@RequestMapping("updateRes")
	@ResponseBody
	public ResultMessage<SysResource> updateRes(SysResourceVo resource){
		ResultMessage<SysResource> rm = resourceService.updateRes(resource);
		return rm;
	}
	
	@RequestMapping("deleteResources")
	@ResponseBody
	public ResultMessage<?> deleteResources(String resCodesJson){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			List<String> resCodes = gson.fromJson(resCodesJson, new TypeToken<List<Integer>>(){}.getType());
			boolean f = resourceService.deleteResources(resCodes);
			resultMessage.setSuccess(f);
		}catch(Exception e){
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("getAuthCodes")
	@ResponseBody
	public ResultMessage<List<String>> getAuthCodes(String resCode){
		ResultMessage<List<String>> resultMessage = new ResultMessage<>();
		try{
			List<String> authCodes = resourceService.getAuthCodes(resCode);
			resultMessage.setModel(authCodes);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
}
