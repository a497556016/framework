package com.framework.bean.common;

import java.util.List;

public class TreeViewNode {
	private String text;
	private String code;
	private List<TreeViewNode> nodes;
	public TreeViewNode(String text, String code) {
		super();
		this.text = text;
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<TreeViewNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeViewNode> nodes) {
		this.nodes = nodes;
	}
	@Override
	public String toString() {
		return "TreeViewNode [text=" + text + ", code=" + code + ", nodes=" + nodes + "]";
	}
}
