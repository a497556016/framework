/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.framework.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.common.mapper.JsonMapper;
import com.framework.dao.BasicInfoMapper;
import com.framework.model.BasicInfo;
import com.framework.servlet.ServletContextManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static BasicInfoMapper dictDao = ServletContextManager.getWebApplicationContext().getBean(BasicInfoMapper.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (BasicInfo dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getCode())){
					return dict.getName();
				}
			}
		}
		return defaultValue;
	}
	
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (BasicInfo dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getName())){
					return dict.getCode();
				}
			}
		}
		return defaultLabel;
	}
	
	public static List<BasicInfo> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<BasicInfo>> dictMap = (Map<String, List<BasicInfo>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (BasicInfo dict : dictDao.selectList(new EntityWrapper<>())){
				List<BasicInfo> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<BasicInfo> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}
	
}
