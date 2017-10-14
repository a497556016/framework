package com.framework.bean.vo;

import java.util.List;

import com.framework.model.SysDepartment;

public class SysDepartmentTreeVo extends SysDepartment {
	private List<SysDepartmentTreeVo> children;
	private boolean leaf;

	public List<SysDepartmentTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysDepartmentTreeVo> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
}
