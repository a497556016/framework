package com.framework.shiro.service;

import java.util.List;

import com.framework.bean.vo.SysUserInfoVo;
import com.framework.model.SysResource;
import com.framework.model.SysUserInfo;
import com.framework.shiro.bean.Menu;
import com.framework.shiro.bean.Resource;
import com.framework.shiro.bean.UserInfo;

public interface UserLoginService {

	List<Resource> getAllResource();

	List<UserInfo> getUserInfoByPersonCode(String currentUsername);

	List<Menu> getMenusByPersonCode(String personCode);

}
