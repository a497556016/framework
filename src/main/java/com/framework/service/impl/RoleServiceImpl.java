package com.framework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.bean.common.Page;
import com.framework.dao.SysRoleAuthRelationMapper;
import com.framework.dao.SysRoleMapper;
import com.framework.dao.SysUserRoleRelationMapper;
import com.framework.model.SysRole;
import com.framework.model.SysRoleAuthRelation;
import com.framework.model.SysUserRoleRelation;
import com.framework.service.RoleService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleAuthRelationMapper sysRoleAuthRelationMapper;
	@Autowired
	private SysUserRoleRelationMapper sysUserRoleRelationMapper;

	@Override
	public void queryRoleList(Page<SysRole> page, SysRole sysRole) {
		
		com.baomidou.mybatisplus.plugins.Page<SysRole> rowBounds = PageFactory.createFrom(page);
		EntityWrapper<SysRole> wrapper = new EntityWrapper<>();
		if(StringUtils.isNotEmpty(sysRole.getRoleCode())){
			wrapper.eq("role_code", sysRole.getRoleCode());
		}
		if(StringUtils.isNotEmpty(sysRole.getRoleName())){
			wrapper.like("role_name", sysRole.getRoleName());
		}
		List<SysRole> userInfos = sysRoleMapper.selectPage(rowBounds, wrapper);
		page.setRows(userInfos);
		page.setTotal(rowBounds.getTotal());
	}

	@Override
	public void addRole(SysRole role, List<String> authCodeList) {
		role.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
		role.setCreateDate(new Date());
		sysRoleMapper.insert(role);
		for (String authCode : authCodeList) {
			SysRoleAuthRelation record = new SysRoleAuthRelation();
			record.setAuthCode(authCode);
			record.setRoleCode(role.getRoleCode());
			record.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
			record.setCreateDate(new Date());
			sysRoleAuthRelationMapper.insert(record);
		}
	}

	@Override
	public List<SysRole> getAllRole() {
		return sysRoleMapper.selectByMap(new HashMap<>());
	}

	@Override
	public void deleteRoles(List<Integer> roleIdList) {
		for (Integer id : roleIdList) {
			SysRole role = sysRoleMapper.selectByPrimaryKey(id);
			sysRoleMapper.deleteByPrimaryKey(id);
			//删除角色所有权限
			EntityWrapper<SysRoleAuthRelation> raWrapper = new EntityWrapper<>();
			raWrapper.eq("role_code", role.getRoleCode());
			sysRoleAuthRelationMapper.delete(raWrapper);
			//删除用户拥有的该角色
			EntityWrapper<SysUserRoleRelation> wrapper = new EntityWrapper<>();
			wrapper.eq("role_code", role.getRoleCode());
			sysUserRoleRelationMapper.delete(wrapper);
		}
	}

	@Override
	public SysRole getRole(Integer roleId) {
		return sysRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public List<String> getRoleAuths(String roleCode) {
		EntityWrapper<SysRoleAuthRelation> raWrapper = new EntityWrapper<>();
		raWrapper.eq("role_code",roleCode);
		List<SysRoleAuthRelation> authRelations = sysRoleAuthRelationMapper.selectList(raWrapper);
		List<String> authCodes = new ArrayList<>();
		for(SysRoleAuthRelation relation : authRelations){
			authCodes.add(relation.getAuthCode());
		}
		return authCodes;
	}

	@Override
	public void updateRole(SysRole role, List<String> authCodeList) {
		role.setModifyBy(LoginUtils.getUserInfo().getPersonCode());
		role.setModifyDate(new Date());
		sysRoleMapper.updateByPrimaryKey(role);
		//删除原有权限
		EntityWrapper<SysRoleAuthRelation> raWrapper = new EntityWrapper<>();
		raWrapper.eq("role_code",role.getRoleCode());
		sysRoleAuthRelationMapper.delete(raWrapper);
		//重新添加权限
		for (String authCode : authCodeList) {
			SysRoleAuthRelation record = new SysRoleAuthRelation();
			record.setAuthCode(authCode);
			record.setRoleCode(role.getRoleCode());
			record.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
			record.setCreateDate(new Date());
			sysRoleAuthRelationMapper.insert(record);
		}
	}

}
