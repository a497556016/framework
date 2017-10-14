package com.framework.service;

import java.util.List;

import com.framework.bean.common.Page;
import com.framework.model.SysAuthority;
import com.framework.model.SysRole;

public interface RoleService {

	void queryRoleList(Page<SysRole> page, SysRole sysRole);

	void addRole(SysRole role, List<String> authCodeList);

	List<SysRole> getAllRole();

	void deleteRoles(List<Integer> roleIdList);

	SysRole getRole(Integer roleId);

	List<String> getRoleAuths(String roleCode) throws Exception;

	void updateRole(SysRole role, List<String> authCodeList);

}
