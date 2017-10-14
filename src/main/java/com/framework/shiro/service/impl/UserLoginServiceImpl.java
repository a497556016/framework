package com.framework.shiro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.bean.vo.SysMenuVo;
import com.framework.model.SysMenu;
import com.framework.model.SysResource;
import com.framework.model.SysUserInfo;
import com.framework.shiro.bean.Menu;
import com.framework.shiro.bean.Resource;
import com.framework.shiro.bean.UserInfo;
import com.framework.shiro.dao.UserLoginMapper;
import com.framework.shiro.service.UserLoginService;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	@Autowired
	private UserLoginMapper userLoginMapper;
	
	public UserLoginMapper getUserLoginMapper() {
		return userLoginMapper;
	}

	public void setUserLoginMapper(UserLoginMapper userLoginMapper) {
		this.userLoginMapper = userLoginMapper;
	}

	public List<Resource> getAllResource() {
		List<Resource> resources = userLoginMapper.getAllResource();
		return resources;
	}

	public List<UserInfo> getUserInfoByPersonCode(String currentUsername) {
		return userLoginMapper.getUserInfoByPersonCode(currentUsername);
	}

	public List<Menu> getMenusByPersonCode(String personCode) {
		// TODO Auto-generated method stub
		List<Menu> rootMenus = userLoginMapper.getMenusByPersonCode(personCode);
		for (Menu menu : rootMenus) {
			Map<String, String> params = new HashMap<>();
			params.put("pCode", menu.getCode());
			params.put("personCode", personCode);
			List<SysMenuVo> childMenus = userLoginMapper.selectChildMenuByPCode(params);
			menu.setChild(childMenus);
		}
		return rootMenus;
	}

}
