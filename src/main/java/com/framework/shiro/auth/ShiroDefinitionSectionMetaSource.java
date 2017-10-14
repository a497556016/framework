package com.framework.shiro.auth;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import com.framework.model.SysAuthority;
import com.framework.shiro.bean.Resource;
import com.framework.shiro.service.UserLoginService;

public class ShiroDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
	private static final Logger log = LoggerFactory.getLogger(ShiroDefinitionSectionMetaSource.class);
	private UserLoginService userLoginService;
	//注入默认的授权定义
	private String filterChainDefinitions;
	//格式化数据，符合shiro的格式，如：perms["admin"]
	public static final String PREMISSION_FORMAT = "perms[\"{0}\"]";
	public Section getObject() throws Exception {
		Ini ini = new Ini();
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		
		//注入资源权限
		List<Resource> resources = userLoginService.getAllResource();
		for (Resource resource : resources) {
			String url = resource.getResUrl();
			if(StringUtils.isNotEmpty(url)){
				List<SysAuthority> authorities = resource.getAuthorities();
				for (SysAuthority sysAuthority : authorities) {
					String authCode = sysAuthority.getAuthCode();
					if(url.contains(";")){
						for(String u : url.split(";")){
							putDefinitionSection(section, u, authCode);
						}
					}else{
						putDefinitionSection(section, url, authCode);
					}
				}
			}
		}
		
		section.put("/**", "user");
		return section;
	}
	
	private void putDefinitionSection(Section section, String key, String value) {
	    log.info("加载数据库权限：【key=" + key + "\tvalue=" + value + "】");
	    section.put(key, MessageFormat.format(PREMISSION_FORMAT, value));
	}

	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return this.getClass();
	}

	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getFilterChainDefinitions() {
		return filterChainDefinitions;
	}

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public UserLoginService getUserLoginService() {
		return userLoginService;
	}

	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

}
