package com.framework.servlet;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServletContextManager {
	private static ServletContext servletContext;
	
	public static ServletContext getServletContext(){
		if(null==servletContext){
			throw new RuntimeException("....servletContext没有初始化...");
		}
		return servletContext;
	}
	
	public static void setServletContext(ServletContext sc){
		servletContext = sc;
	}

	
	public static WebApplicationContext getWebApplicationContext(){
		if(null==servletContext){
			throw new RuntimeException("....servletContext没有初始化...");
		}
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return applicationContext;
	}
}
