package com.framework.bean.vo;

import java.util.List;

import com.framework.model.SysAuthority;

public class SysAuthorityVo extends SysAuthority {
	private List<String> resCodes;

	public List<String> getResCodes() {
		return resCodes;
	}

	public void setResCodes(List<String> resCodes) {
		this.resCodes = resCodes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysAuthorityVo [resCodes=");
		builder.append(resCodes);
		builder.append("]");
		return builder.toString();
	}
}
