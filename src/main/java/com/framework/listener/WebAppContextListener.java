package com.framework.listener;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.framework.servlet.PropertiesManger;
import com.framework.servlet.ServletContextManager;

public class WebAppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("========应用程序关闭。。。。。。");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("===========================================");
		System.out.println();
		System.out.println("	          	欢迎使用framework		 		");
		System.out.println();
		System.out.println("===========================================");
		
		ServletContext servletContext = event.getServletContext();
		servletContext.setAttribute("ctx", servletContext.getContextPath());
		ServletContextManager.setServletContext(servletContext);
		/**
		 * 加载属性配置文件
		 */
		PropertiesManger.init();
		Set<Entry<String,String>> entryKey = PropertiesManger.getPropMap().entrySet();
		Iterator<Entry<String,String>> iterator = entryKey.iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry = iterator.next();
			servletContext.setAttribute(entry.getKey(), entry.getValue());
		}
		
	}
	

}
