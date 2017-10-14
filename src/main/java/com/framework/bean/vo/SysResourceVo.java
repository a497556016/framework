package com.framework.bean.vo;

import com.framework.model.SysResource;

public class SysResourceVo extends SysResource {
	private String authCodes;

	public String getAuthCodes() {
		return authCodes;
	}

	public void setAuthCodes(String authCodes) {
		this.authCodes = authCodes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysResourceVo [authCodes=");
		builder.append(authCodes);
		builder.append("]");
		return builder.toString();
	}
}
