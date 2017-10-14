package com.framework.bean.common;

import java.util.List;

public class JsTreeNodeBean<T> {
	private String text;
	private Integer id;
	private T data;
	private String icon;
	private State state; 
	private List<JsTreeNodeBean> children;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<JsTreeNodeBean> getChildren() {
		return children;
	}

	public void setChildren(List<JsTreeNodeBean> children) {
		this.children = children;
	}

	public State createState(){
		this.state = new State();
		return this.state;
	}
	
	public class State{
		private boolean opened;
		private boolean disabled;
		private boolean selected;

		public boolean isOpened() {
			return opened;
		}

		public void setOpened(boolean opened) {
			this.opened = opened;
		}

		public boolean isDisabled() {
			return disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
}
