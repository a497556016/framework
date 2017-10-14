package com.framework.bean;

import java.util.List;

public class FormProduceInfo {
	private Integer formId;
	private String className;
	private String packageName;
	private List<String> codeFileType;
	private String javaFilePath;
	private String mapperXmlFilePath;
	private String jsFilePath;
	private String jspFilePath;
	public Integer getFormId() {
		return formId;
	}
	public String getClassName() {
		return className;
	}
	public String getPackageName() {
		return packageName;
	}
	public List<String> getCodeFileType() {
		return codeFileType;
	}
	public String getJavaFilePath() {
		return javaFilePath;
	}
	public String getMapperXmlFilePath() {
		return mapperXmlFilePath;
	}
	public String getJsFilePath() {
		return jsFilePath;
	}
	public String getJspFilePath() {
		return jspFilePath;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public void setCodeFileType(List<String> codeFileType) {
		this.codeFileType = codeFileType;
	}
	public void setJavaFilePath(String javaFilePath) {
		this.javaFilePath = javaFilePath;
	}
	public void setMapperXmlFilePath(String mapperXmlFilePath) {
		this.mapperXmlFilePath = mapperXmlFilePath;
	}
	public void setJsFilePath(String jsFilePath) {
		this.jsFilePath = jsFilePath;
	}
	public void setJspFilePath(String jspFilePath) {
		this.jspFilePath = jspFilePath;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormProduceInfo [formId=");
		builder.append(formId);
		builder.append(", className=");
		builder.append(className);
		builder.append(", packageName=");
		builder.append(packageName);
		builder.append(", codeFileType=");
		builder.append(codeFileType);
		builder.append(", javaFilePath=");
		builder.append(javaFilePath);
		builder.append(", mapperXmlFilePath=");
		builder.append(mapperXmlFilePath);
		builder.append(", jsFilePath=");
		builder.append(jsFilePath);
		builder.append(", jspFilePath=");
		builder.append(jspFilePath);
		builder.append("]");
		return builder.toString();
	}
	
}
