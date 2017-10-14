package com.framework.bean.vo;

import java.util.List;

import com.framework.model.SysResource;

public class SysResourceTreeVo extends SysResource {
	private List<SysResourceTreeVo> children;

	private boolean leaf;
	
	public List<SysResourceTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysResourceTreeVo> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
}
