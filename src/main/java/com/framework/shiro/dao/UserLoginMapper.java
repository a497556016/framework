package com.framework.shiro.dao;

import java.util.List;
import java.util.Map;

import com.framework.bean.vo.SysMenuVo;
import com.framework.model.SysMenu;
import com.framework.model.SysResource;
import com.framework.model.SysRole;
import com.framework.model.SysUserInfo;
import com.framework.shiro.bean.Menu;
import com.framework.shiro.bean.Resource;
import com.framework.shiro.bean.UserInfo;

public interface UserLoginMapper {

	List<Resource> getAllResource();

	List<UserInfo> getUserInfoByPersonCode(String currentUsername);

	List<Menu> getMenusByPersonCode(String personCode);

	List<SysMenuVo> selectChildMenuByPCode(Map<String, String> params);

}
