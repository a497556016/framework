package com.framework.service;

import java.util.List;

import com.framework.bean.common.Page;
import com.framework.bean.common.TreeViewNode;
import com.framework.bean.vo.SysMenuVo;
import com.framework.model.SysMenu;

public interface MenuService {

	void queryMenuList(Page<SysMenu> page, SysMenu menu) throws Exception;
	
	List<TreeViewNode> queryMenuTree() throws Exception;

	boolean deleteMenus(List<Integer> ids) throws Exception;

	void addMenu(SysMenuVo menu) throws Exception;

	List<SysMenuVo> queryMenuListByPCode(String pCode) throws Exception;

	void saveMenu(List<SysMenuVo> menus) throws Exception;

}
