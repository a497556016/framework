package com.framework.bean.common;

import java.util.List;

import com.framework.model.SysResource;
import com.framework.model.SysRole;
import com.framework.model.SysUserInfo;

public class LoginUserInfo {
	private SysUserInfo userInfo;
	private List<SysResource> resources;
	private List<SysRole> roles;
	public SysUserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(SysUserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<SysResource> getResources() {
		return resources;
	}
	public void setResources(List<SysResource> resources) {
		this.resources = resources;
	}
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "LoginUserInfo [userInfo=" + userInfo + ", resources=" + resources + ", roles=" + roles + "]";
	}
}
