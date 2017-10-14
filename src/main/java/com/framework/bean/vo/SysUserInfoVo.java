package com.framework.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.framework.model.SysCommonFileInfo;
import com.framework.model.SysUserInfo;

public class SysUserInfoVo extends SysUserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 858555766416358274L;
	private List<String> roleCodes; 
    private SysCommonFileInfo userPhoto;
    
    public SysCommonFileInfo getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(SysCommonFileInfo userPhoto) {
		this.userPhoto = userPhoto;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}
}
