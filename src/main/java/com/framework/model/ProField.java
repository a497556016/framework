package com.framework.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_pro_field")
public class ProField {
    private Integer id;

    private Integer formId;

    private String name;

    private String comments;

    private boolean isPri;

    private boolean isGridCol;

    private boolean isFormField;

    private String fieldType;

    private boolean isQuery;

    private String queryType;

    private String basicType;

    private String expandParam;
    
    private String columnName;//数据库表中的列名称
    
    private String dataType;//数据类型

    private Date createDate;

    private String createBy;

    private Date modifyDate;

    private String modifyBy;

	public Integer getId() {
		return id;
	}

	public Integer getFormId() {
		return formId;
	}

	public String getName() {
		return name;
	}

	public String getComments() {
		return comments;
	}

	public boolean isPri() {
		return isPri;
	}

	public boolean isGridCol() {
		return isGridCol;
	}

	public boolean isFormField() {
		return isFormField;
	}

	public String getFieldType() {
		return fieldType;
	}

	public boolean isQuery() {
		return isQuery;
	}

	public String getQueryType() {
		return queryType;
	}

	public String getBasicType() {
		return basicType;
	}

	public String getExpandParam() {
		return expandParam;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setPri(boolean isPri) {
		this.isPri = isPri;
	}

	public void setGridCol(boolean isGridCol) {
		this.isGridCol = isGridCol;
	}

	public void setFormField(boolean isFormField) {
		this.isFormField = isFormField;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public void setQuery(boolean isQuery) {
		this.isQuery = isQuery;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public void setBasicType(String basicType) {
		this.basicType = basicType;
	}

	public void setExpandParam(String expandParam) {
		this.expandParam = expandParam;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}