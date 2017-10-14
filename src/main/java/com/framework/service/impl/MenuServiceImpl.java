package com.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.bean.common.Page;
import com.framework.bean.common.TreeViewNode;
import com.framework.bean.vo.SysMenuVo;
import com.framework.dao.SysAuthResRelationMapper;
import com.framework.dao.SysMenuMapper;
import com.framework.dao.SysResourceMapper;
import com.framework.model.SysAuthResRelation;
import com.framework.model.SysMenu;
import com.framework.model.SysResource;
import com.framework.service.MenuService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class MenuServiceImpl implements MenuService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Autowired
	private SysResourceMapper sysResourceMapper;
	
	@Autowired
	private SysAuthResRelationMapper sysAuthResRelationMapper;

	@Override
	public void queryMenuList(Page<SysMenu> page, SysMenu menu) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<SysMenu> mpPage = PageFactory.createFrom(page);
		mpPage.setSize(page.getLimit());
		List<SysMenu> menus = sysMenuMapper.queryMenuList(mpPage, menu);
		page.setRows(menus);
		page.setTotal(mpPage.getTotal());
	}

	@Override
	public List<TreeViewNode> queryMenuTree() throws Exception {
		EntityWrapper<SysMenu> wrapper = new EntityWrapper<>();
		wrapper.isNull("pCode").or().eq("pCode", "");
		List<SysMenu> rootMenus = sysMenuMapper.selectList(wrapper);
		List<TreeViewNode> rootNodes = new ArrayList<>();
		for (SysMenu sysMenu : rootMenus) {
			TreeViewNode rootNode = new TreeViewNode(sysMenu.getText(), sysMenu.getCode());
			//二级菜单
			EntityWrapper<SysMenu> wrapper1 = new EntityWrapper<>();
			wrapper1.eq("pCode",sysMenu.getCode());
			List<SysMenu> childMenus = sysMenuMapper.selectList(wrapper1);
			List<TreeViewNode> childNodes = new ArrayList<>();
			for (SysMenu childMenu : childMenus) {
				TreeViewNode childNode = new TreeViewNode(childMenu.getText(), childMenu.getCode());
				childNodes.add(childNode);
			}
			rootNode.setNodes(childNodes);
			
			rootNodes.add(rootNode);
		}
		return rootNodes;
	}

	@Override
	public boolean deleteMenus(List<Integer> ids) throws Exception {
		int count = 0;
		for (Integer id : ids) {
			SysMenu menu = sysMenuMapper.selectByPrimaryKey(id);
			int i = sysMenuMapper.deleteByPrimaryKey(id);
			EntityWrapper<SysResource> wrapper = new EntityWrapper<>();
			wrapper.eq("menu_code", menu.getCode());
			List<SysResource> resources = sysResourceMapper.selectList(wrapper);
			for (SysResource sysResource : resources) {
				sysResourceMapper.deleteByPrimaryKey(sysResource.getId());
				EntityWrapper<SysAuthResRelation> arWrapper = new EntityWrapper<>();
				arWrapper.eq("res_code", sysResource.getResCode());
				sysAuthResRelationMapper.delete(arWrapper);
			}
			count += i;
		}
		return count==ids.size();
	}

	@Override
	public void addMenu(SysMenuVo menu) throws Exception {
		sysMenuMapper.insert(menu);
		//添加资源
		SysResource resource = new SysResource();
		resource.setResName(menu.getText());
		resource.setResCode(menu.getCode());
		resource.setpResCode(menu.getpCode());
		resource.setResUrl(menu.getUrl());
		resource.setMenuCode(menu.getCode());
		resource.setType("menu");
		sysResourceMapper.insert(resource);
	}

	@Override
	public List<SysMenuVo> queryMenuListByPCode(String pCode) throws Exception {
		
		List<SysMenuVo> menus = sysMenuMapper.queryMenuListByPCode(pCode);
		for (SysMenuVo sysMenu : menus) {
			if(!sysMenu.getLeaf()){
				sysMenu.setChildren(queryMenuListByPCode(sysMenu.getCode()));
			}
		}
		return menus;
	}

	@Override
	public void saveMenu(List<SysMenuVo> menus) throws Exception {
		for (SysMenuVo sysMenuVo : menus) {
			sysMenuVo.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
		}
		sysMenuMapper.saveMenu(menus);
	}
	
	
}
