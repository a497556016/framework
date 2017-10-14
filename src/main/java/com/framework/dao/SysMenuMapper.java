package com.framework.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.framework.bean.vo.SysMenuVo;
import com.framework.model.SysMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

	List<SysMenu> queryMenuList(Pagination mpPage, SysMenu menu);

	List<SysMenuVo> queryMenuListByPCode(String pCode);

	void saveMenu(List<SysMenuVo> menus);
}