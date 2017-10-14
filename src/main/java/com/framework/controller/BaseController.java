package com.framework.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.framework.common.mapper.DateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class BaseController {
	protected HttpServletRequest request; 
	protected HttpServletResponse response;  
    protected HttpSession session;  
    
    @Autowired
    protected Gson gson;
    
    public BaseController() {
    	gson = new GsonBuilder()
//				.setPrettyPrinting()
				.registerTypeAdapter(Date.class,new DateAdapter())
				.serializeNulls().create();
	}
    
    @InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }  
      
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    }
    
    protected void write(String jsonStr){
    	try {
			this.response.getWriter().print(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected void setRequestAttributes(){
    	Enumeration<String> paramNames = this.request.getParameterNames();
    	while(paramNames.hasMoreElements()){
    		String paramName = paramNames.nextElement();
    		String paramValue = this.request.getParameter(paramName);
    		this.request.setAttribute(paramName, paramValue);
    	}
    }
    
    protected void write(Object obj){
    	String jsonStr = gson.toJson(obj);
    	this.write(jsonStr);
    }
    
    protected Map<String, Object> getMapParams(){
    	Map<String, Object> map = new HashMap<>();
    	Map<String, String[]> parameterMap = this.request.getParameterMap();
    	Set<String> keySet = parameterMap.keySet();
    	for (String key : keySet) {
			String[] values = parameterMap.get(key);
			if(values.length==1){
				map.put(key, values[0]);
			}else{
				List<String> valueList = Arrays.asList(values);
				map.put(key, valueList);
			}
		}
    	return map;
    }

}
