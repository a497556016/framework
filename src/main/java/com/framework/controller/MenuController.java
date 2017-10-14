package com.framework.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.common.TreeViewNode;
import com.framework.bean.vo.SysMenuVo;
import com.framework.model.SysMenu;
import com.framework.service.MenuService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/toMenuManage")
	public String toMenuManage(){
		return "system/menu/menuManage";
	}
	
	@RequestMapping("/toAddMenu")
	public String toAddMenu(String pCode){
		setRequestAttributes();
		return "system/menu/addMenu";
	}
	
	@RequestMapping("/queryMenuList")
	@ResponseBody
	public Page<SysMenu> queryMenuList(Page<SysMenu> page,SysMenu menu){
		try {
			menuService.queryMenuList(page,menu);
			page.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping("/queryMenuListByPCode")
	@ResponseBody
	public List<SysMenuVo> queryMenuListByPCode(String pCode){
		List<SysMenuVo> menus = new ArrayList<>();
		try {
			menus = menuService.queryMenuListByPCode(pCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}
	
	@RequestMapping("/queryMenuTree")
	@ResponseBody
	public List<TreeViewNode> queryMenuTree(){
		List<TreeViewNode> list = new ArrayList<>();
		try {
			list = menuService.queryMenuTree();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping("/deleteMenus")
	@ResponseBody
	public ResultMessage<?> deleteMenus(String idsJson){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			List<Integer> ids = gson.fromJson(idsJson, new TypeToken<List<Integer>>(){}.getType());
			boolean f = menuService.deleteMenus(ids);
			resultMessage.setSuccess(f);
		}catch(Exception e){
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("/addMenu")
	@ResponseBody
	public ResultMessage<SysMenu> addMenu(SysMenuVo menu){
		ResultMessage<SysMenu> resultMessage = new ResultMessage<>();
		try{
			menuService.addMenu(menu);
			resultMessage.setSuccess(true);
			resultMessage.setModel(menu);
		}catch(Exception e){
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
	
	@RequestMapping("/saveMenu")
	@ResponseBody
	public ResultMessage<?> saveMenu(String menuJson){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			List<SysMenuVo> menus = gson.fromJson(menuJson, new TypeToken<List<SysMenuVo>>(){}.getType());
			menuService.saveMenu(menus);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
}
