package com.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.framework.servlet.PropertiesManger;

public class JdbcUtil {

	public static Connection getConn(){
		Connection connection = null;
		try{
			Class.forName(PropertiesManger.get("jdbc.driver"));
			connection = DriverManager.getConnection(PropertiesManger.get("jdbc.url"), PropertiesManger.get("jdbc.username"), PropertiesManger.get("jdbc.password"));
		}catch(Exception e){
			e.printStackTrace();
		} 
		return connection;
	}
}
