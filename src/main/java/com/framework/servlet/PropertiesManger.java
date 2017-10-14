package com.framework.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesManger {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesManger.class);
	
	private static final Map<String, String> propMap = new HashMap<>();
	
	public static void init(){
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
			for(Object obj : properties.keySet()){
				String key = null==obj?"":obj.toString();
				String value = properties.getProperty(key);
				logger.info("---------加载配置："+key+"\t"+value);
				propMap.put(key, value);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String get(String key){
		return propMap.get(key);
	}
	
	public static Integer getInt(String key){
		String value = get(key);
		if(null==value){
			return null;
		}else{
			return Integer.parseInt(value);
		}
	}
	
	public static Map<String, String> getPropMap(){
		return propMap;
	}
}
