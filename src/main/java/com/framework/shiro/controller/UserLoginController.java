package com.framework.shiro.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.ResultMessage;
import com.framework.constant.CommonConstant;
import com.framework.controller.BaseController;
import com.framework.model.SysUserInfo;
import com.framework.util.MD5Util;

@Controller
public class UserLoginController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(UserLoginController.class); 

	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	public void login(SysUserInfo user){
		log.info("Login user=====" + user);
		ResultMessage rm = new ResultMessage();
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getPersonCode(), MD5Util.encode(user.getPassword()));
		
		String needCode = WebUtils.getCleanParam(request, "needCode");
		
		String remember = WebUtils.getCleanParam(request, "online");
		log.info("remember=" + remember);
		
		String reqCheckCode = WebUtils.getCleanParam(request, "checkCode");
		String checkCode = (String) session.getAttribute(CommonConstant.CHECK_CODE);
		if("1".equals(needCode)&&(null==reqCheckCode||!reqCheckCode.trim().toLowerCase().equals(checkCode.toLowerCase()))){
			rm.setMessage("验证码错误！");
			write(rm);
		}else{
			try {
		    	if(remember != null && remember.equalsIgnoreCase("1")) {
		    	    token.setRememberMe(true);
		    	}else{
		    		token.setRememberMe(false);
		    	}
				subject.login(token);
				rm.setSuccess(true);
			} catch(UnknownAccountException ue) {
				token.clear();
				rm.setMessage("登录失败，您输入的账号不存在");
			} catch(IncorrectCredentialsException ie) {
				token.clear();
				rm.setMessage("登录失败，密码不匹配");
			} catch(RuntimeException re) {
				token.clear();
				rm.setMessage("登录失败");
			}
			write(rm);
		}
	}
	
	@RequestMapping(value="/user/login",method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	/**
	 * 转到提示登录超时页面
	 * @return
	 */
	@RequestMapping(value="/user/toLogin",method=RequestMethod.GET)
	public String toLogin(){
		return "timeout";
	}
	
	@RequestMapping("/user/logout")
	@ResponseBody
	public ResultMessage<?> logout(){
		ResultMessage<?> message = new ResultMessage<>();
		try{
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			message.setSuccess(true);
		}catch(Exception e){
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
}
