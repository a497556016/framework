package com.framework.bean;

import java.util.ArrayList;
import java.util.List;

import com.framework.model.SysUserInfo;

public class UserInfoQueryForm extends SysUserInfo {
	private List<SysUserInfo> userInfos = new ArrayList<SysUserInfo>();

	public List<SysUserInfo> getUserInfos() {
		return userInfos;
	}

	public void setUserInfos(List<SysUserInfo> userInfos) {
		this.userInfos = userInfos;
	}
}
