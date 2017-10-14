package com.framework.bean;

public class TableColumn {
	private String name;
	private String comments;
	private String type;
	private String defaultValue;
	public String getName() {
		return name;
	}
	public String getComments() {
		return comments;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getType() {
		return type;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TableColumn [name=");
		builder.append(name);
		builder.append(", comments=");
		builder.append(comments);
		builder.append(", type=");
		builder.append(type);
		builder.append(", defaultValue=");
		builder.append(defaultValue);
		builder.append("]");
		return builder.toString();
	}
}
