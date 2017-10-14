package com.framework.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysRoleVo;
import com.framework.bean.vo.SysUserInfoVo;
import com.framework.model.SysAuthority;
import com.framework.model.SysRole;
import com.framework.model.SysUserInfo;
import com.framework.service.RoleService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RoleService roleService;
	private BeanCopier roleModelToVo = BeanCopier.create(SysRole.class, SysRoleVo.class, false);
	
	@RequestMapping("/toRoleManage")
	public String toRoleManage(){
		logger.info("转到角色管理页面");
		return "system/role/roleManage";
	}
	
	@RequestMapping("/toAddRole")
	public String toAddRole(){
		logger.info("转到角色新增页面");
		return "system/role/editRole";
	}
	
	@RequestMapping("/toEditRole")
	public String toEditRole(HttpServletRequest request,Integer roleId){
		logger.info("转到角色编辑页面");
		SysRole role = roleService.getRole(roleId);
		request.setAttribute("role", role);
		return "system/role/editRole";
	}
	
	@RequestMapping("/getRoleAuths")
	@ResponseBody
	public ResultMessage<List<String>> getRoleAuths(String roleCode){
		ResultMessage<List<String>> resultMessage = new ResultMessage<>();
		List<String> authCodes = new ArrayList<>();
		try {
			authCodes = roleService.getRoleAuths(roleCode);
			resultMessage.setModel(authCodes);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("/getAllRole")
	@ResponseBody
	public List<SysRole> getAllRole(){
		List<SysRole> roles = new ArrayList<>();
		try {
			roles = roleService.getAllRole();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}
	
	@RequestMapping("/queryRoleList")
	@ResponseBody
	public Page<SysRole> queryRoleList(Page<SysRole> page,SysRole sysRole){
		roleService.queryRoleList(page,sysRole);
		page.setSuccess(true);
		return page;
	}
	
	@RequestMapping("/addRole")
	@ResponseBody
	public ResultMessage<SysRole> addRole(SysRole role,String authCodes){
		ResultMessage<SysRole> resultMessage = new ResultMessage<>();
		try {
			List<String> authCodeList = gson.fromJson(authCodes, new TypeToken<List<String>>(){}.getType());
			roleService.addRole(role,authCodeList);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
	
	@RequestMapping("/updateRole")
	@ResponseBody
	public ResultMessage<SysRole> updateRole(SysRole role,String authCodes){
		ResultMessage<SysRole> resultMessage = new ResultMessage<>();
		try {
			List<String> authCodeList = gson.fromJson(authCodes, new TypeToken<List<String>>(){}.getType());
			roleService.updateRole(role,authCodeList);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
	
	@RequestMapping("/deleteRoles")
	@ResponseBody
	public ResultMessage<?> deleteRoles(String roleIds){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try {
			List<Integer> roleIdList = gson.fromJson(roleIds, new TypeToken<List<Integer>>(){}.getType());
			roleService.deleteRoles(roleIdList);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("/getRole")
	@ResponseBody
	public ResultMessage<SysRoleVo> getRole(Integer id){
		ResultMessage<SysRoleVo> resultMessage = new ResultMessage<>();
		try{
			SysRole role = roleService.getRole(id);
			SysRoleVo sysRoleVo = new SysRoleVo();
			roleModelToVo.copy(role, sysRoleVo, null);
			List<String> authCodes = roleService.getRoleAuths(role.getRoleCode());
			sysRoleVo.setAuthCodes(authCodes);
			resultMessage.setModel(sysRoleVo);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return resultMessage;
	}
}
