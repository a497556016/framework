package com.framework.bean.vo;

import java.util.List;

import com.framework.model.SysRole;

public class SysRoleVo extends SysRole {
	private List<String> authCodes;

	public List<String> getAuthCodes() {
		return authCodes;
	}

	public void setAuthCodes(List<String> authCodes) {
		this.authCodes = authCodes;
	}

	
}
