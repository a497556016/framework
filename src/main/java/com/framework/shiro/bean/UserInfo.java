package com.framework.shiro.bean;

import java.io.Serializable;
import java.util.List;

import com.framework.bean.vo.SysUserInfoVo;

public class UserInfo extends SysUserInfoVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 292783775029743035L;
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserInfo [roles=" + roles + "]";
	}
}
