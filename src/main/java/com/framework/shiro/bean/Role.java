package com.framework.shiro.bean;

import java.io.Serializable;
import java.util.List;

import com.framework.model.SysAuthority;
import com.framework.model.SysRole;

public class Role extends SysRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1094584645383814280L;
	private List<SysAuthority> authorities;

	public List<SysAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<SysAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "Role [authorities=" + authorities + "]";
	}
}
