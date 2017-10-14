package com.framework.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.UserInfoQueryForm;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysUserInfoListVo;
import com.framework.bean.vo.SysUserInfoVo;
import com.framework.constant.CommonConstant.FileBusinessType;
import com.framework.model.SysUserInfo;
import com.framework.service.CommonService;
import com.framework.service.UserService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	

	@RequestMapping("toUserManage")
	public String toUserManage(){
		return "system/userManage/userManage";
	}
	
	@RequestMapping("toAddUser")
	public String toAddUser(){
		return "system/userManage/addUser";
	}
	
	@RequestMapping("toEditUser")
	public String toEditUser(String id){
		SysUserInfo user = null;
		try {
			user = userService.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("user", user);
		return "system/userManage/editUser";
	}
	
	@RequestMapping("toViewUser")
	public String toViewUser(String id){
		SysUserInfo user = null;
		try {
			user = userService.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("user", user);
		return "system/userManage/viewUser";
	}
	
	@RequestMapping("/toRoleAssign")
	public String toRoleAssign(String personCode){
		return "system/userManage/roleAssign";
	}
	
	@RequestMapping("queryUserList")
	@ResponseBody
	public Page<SysUserInfoListVo> queryUserList(Page<SysUserInfoListVo> page,SysUserInfo userInfo){
		try {
			userService.queryUserList(page,userInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping("addUser")
	@ResponseBody
	public ResultMessage<SysUserInfoVo> addUser(SysUserInfoVo userInfo){
		ResultMessage<SysUserInfoVo> rm = new ResultMessage<>();
		try {
			rm = userService.addUser(userInfo);
		} catch (Exception e) {
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("updateUser")
	@ResponseBody
	public ResultMessage<SysUserInfoVo> updateUser(SysUserInfoVo userInfo){
		ResultMessage<SysUserInfoVo> rm = new ResultMessage<>();
		try {
			rm = userService.updateUser(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("deleteUsers")
	@ResponseBody
	public ResultMessage<?> deleteUsers(String userInfosJson){
		ResultMessage<?> rm = null;
		try {
			List<SysUserInfoVo> sysUserInfoVos = gson.fromJson(userInfosJson, new TypeToken<List<SysUserInfoVo>>(){}.getType());
			rm = userService.deleteUsers(sysUserInfoVos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rm;
	}
	
	@RequestMapping("roleAssign")
	@ResponseBody
	public ResultMessage<?> roleAssign(String roleCodes,String personCode){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		List<String> list = gson.fromJson(roleCodes, new TypeToken<List<String>>(){}.getType());
		try {
			userService.roleAssign(list,personCode);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("getUserRoleCodes")
	@ResponseBody
	public Page<String> getUserRoleCodes(String personCode){
		Page<String> page = new Page<>();
		try {
			List<String> roleCodes = userService.getUserRoleCodes(personCode);
			page.setRows(roleCodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping("lockUser")
	@ResponseBody
	public ResultMessage<?> lockUser(Integer id,boolean locked){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try {
			userService.lockUser(id,locked);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("getUser")
	@ResponseBody
	public ResultMessage<SysUserInfoVo> getUser(SysUserInfo user){
		ResultMessage<SysUserInfoVo> resultMessage = new ResultMessage<>();
		try {
			SysUserInfoVo userInfoVo = userService.getUser(user);
			userInfoVo.setPassword(null);
			resultMessage.setModel(userInfoVo);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
}
