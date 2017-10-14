package com.framework.util;

import org.apache.commons.lang3.StringUtils;

import com.framework.bean.common.Page;

public class SqlFormatUtil {
	public static String formatLike(String field){
		if(StringUtils.isNotEmpty(field)){
			return "%"+field+"%";
		}
		return field;
	}
	
	public static String formatOrderBy(Page<?> page){
		if(null==page){
			throw new NullPointerException("Page is Null!");
		}
		return page.getSort()+" "+page.getOrder();
	}
	
}
