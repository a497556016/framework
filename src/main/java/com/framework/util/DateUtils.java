package com.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {

	public static String format(Date date, String pattern) {
		if(null==date||StringUtils.isEmpty(pattern)){
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	
}
