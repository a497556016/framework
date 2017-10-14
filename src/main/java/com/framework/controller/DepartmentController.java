package com.framework.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.JsTreeNodeBean;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysDepartmentTreeVo;
import com.framework.bean.vo.SysMenuVo;
import com.framework.model.SysDepartment;
import com.framework.service.DepartmentService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("dept")
public class DepartmentController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping("toDeptManage")
	public String toDeptManage(){
		
		return "system/dept/deptManage";
	}
	
	@RequestMapping("/getDeptWithUserJsTreeData")
	@ResponseBody
	public ResultMessage<JsTreeNodeBean> getDeptWithUserJsTreeData(){
		ResultMessage<JsTreeNodeBean> resultMessage = new ResultMessage<>();
		try{
			JsTreeNodeBean root = departmentService.getDeptWithUserJsTreeData();
			resultMessage.setModel(root);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return resultMessage;
	}
	
	@RequestMapping("queryDeptListByPCode")
	@ResponseBody
	public List<SysDepartmentTreeVo> queryDeptListByPCode(String pCode){
		List<SysDepartmentTreeVo> departmentTrees = new ArrayList<>();
		try {
			departmentTrees = departmentService.queryMenuListByPCode(pCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentTrees;
	}
	
	@RequestMapping("saveDepartment")
	@ResponseBody
	public ResultMessage<?> saveDepartment(String deptJson){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			List<SysDepartment> departments = gson.fromJson(deptJson, new TypeToken<List<SysDepartment>>(){}.getType());
			departmentService.saveDepartment(departments);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return resultMessage;
	}
}
