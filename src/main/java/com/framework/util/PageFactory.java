package com.framework.util;

import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

import com.framework.bean.common.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PageFactory {
	/**
	 * 创建mybatis-plus分页对象
	 * @param page1
	 * @return
	 */
	public static <T> com.baomidou.mybatisplus.plugins.Page<T> createFrom(Page<T> page){
		com.baomidou.mybatisplus.plugins.Page<T> page2 = new com.baomidou.mybatisplus.plugins.Page<>(page.getPage(),page.getLimit());
		
		String sort = page.getSort();
		if(StringUtils.isNotEmpty(sort)){
			JSONArray jsonArray = JSONArray.fromObject(sort);
			ListIterator<JSONObject> listIterator = jsonArray.listIterator();
			while(listIterator.hasNext()){
				JSONObject jsonObject = listIterator.next();
				String property = jsonObject.getString("property");
				String direction = jsonObject.getString("direction");
				page2.setOrderByField(com.baomidou.mybatisplus.toolkit.StringUtils.camelToUnderline(property));
				page2.setAsc("ASC".equals(direction.toUpperCase()));
			}
			
		}
		
		page2.setCurrent(page.getOffset());
		page2.setSize(page.getLimit());
		return page2;
	}
}
