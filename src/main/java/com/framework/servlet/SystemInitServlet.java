package com.framework.servlet;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.io.FileUtils;

import com.framework.util.JdbcUtil;

/**
 * Servlet implementation class SystemInitServlet
 */
public class SystemInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SystemInitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		/**
		 * 数据库建表
		 */
		try {
			//initDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void initDatabase() throws Exception{
		String create = PropertiesManger.get("db.auto.create");
		if("true".equals(create)){
			Connection connection = JdbcUtil.getConn();
			
			String classPath = getClass().getResource("/").toString();
			if(classPath.startsWith("file:\\")){
				classPath = classPath.substring(6, classPath.length());
			}
			
			String dbType = PropertiesManger.get("db.type");
			String filePath = null;
			File file = null;
			boolean init = false;
			if("mysql".equals(dbType)){
				filePath = classPath+"db/createMySql.sql";
				String catelog = connection.getCatalog();
				PreparedStatement ps = connection.prepareStatement("select count(0) from INFORMATION_SCHEMA. TABLES where TABLE_SCHEMA = '"+catelog+"'");
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					int i = rs.getInt(1);
					init = i==0;
				}
			}
			if(init&&null!=filePath){
				file = new File(filePath);
				String sql = FileUtils.readFileToString(file);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.execute();
			}
		}
	}

}
