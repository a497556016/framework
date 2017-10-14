package com.framework.shiro.bean;

import java.util.List;

import com.framework.model.SysAuthority;
import com.framework.model.SysResource;

public class Resource extends SysResource {
	private List<SysAuthority> authorities;//权限列表

	public List<SysAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<SysAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "Resource [authorities=" + authorities + "]";
	}
}
