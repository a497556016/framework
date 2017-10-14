package com.framework.shiro.bean;

import java.io.Serializable;
import java.util.List;

import com.framework.bean.vo.SysMenuVo;

public class Menu extends SysMenuVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1075267354603184465L;
	private List<SysMenuVo> child;

	public List<SysMenuVo> getChild() {
		return child;
	}

	public void setChild(List<SysMenuVo> child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "Menu [child=" + child + "]";
	}
}
