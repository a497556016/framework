package com.framework.bean.vo;

import java.io.Serializable;
import java.util.List;

import com.framework.model.SysMenu;

public class SysMenuVo extends SysMenu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5051906483886934983L;

	private String url;
	
	private List<SysMenuVo> children;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<SysMenuVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuVo> children) {
		this.children = children;
	}
	
}
