package com.framework.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.framework.constant.CommonConstant;
import com.framework.shiro.bean.UserInfo;

import net.sf.json.JSONObject;

public class LoginUtils {
	public static UserInfo getUserInfo(){
		Subject currentUser = SecurityUtils.getSubject();
		String userInfoJson = (String) currentUser.getSession().getAttribute(CommonConstant.LOGIN_USER_INFO);
		
		UserInfo userInfo = (UserInfo) JSONObject.toBean(JSONObject.fromObject(userInfoJson), UserInfo.class);
		return userInfo;
	}
}
