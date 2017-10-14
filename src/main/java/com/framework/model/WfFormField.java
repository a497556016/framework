package com.framework.model;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_wf_form_field")
public class WfFormField {
	
	/**
	 *主键ID
	 */
	private Integer id;
	/**
	 *表单ID
	 */
	private String formId;
	/**
	 *列编码
	 */
	private String columnCode;
	/**
	 *列名称
	 */
	private String columnName;
	/**
	 *字段类型
	 */
	private String fieldType;
	/**
	 *字段宽度
	 */
	private Double fieldWidth;
	/**
	 *基础数据类型
	 */
	private String basicType;
	/**
	 *数据类型
	 */
	private String dataType;
	/**
	 *是否可编辑
	 */
	private String editTask;
	
	public WfFormField() {
	}
	
	
	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}
	
	public String getFormId(){
		return this.formId;
	}

	public void setFormId(String formId){
		this.formId = formId;
	}
	
	public String getColumnCode(){
		return this.columnCode;
	}

	public void setColumnCode(String columnCode){
		this.columnCode = columnCode;
	}
	
	public String getColumnName(){
		return this.columnName;
	}

	public void setColumnName(String columnName){
		this.columnName = columnName;
	}
	
	public String getFieldType(){
		return this.fieldType;
	}

	public void setFieldType(String fieldType){
		this.fieldType = fieldType;
	}
	
	public Double getFieldWidth(){
		return this.fieldWidth;
	}

	public void setFieldWidth(Double fieldWidth){
		this.fieldWidth = fieldWidth;
	}
	
	public String getBasicType(){
		return this.basicType;
	}

	public void setBasicType(String basicType){
		this.basicType = basicType;
	}
	
	public String getDataType(){
		return this.dataType;
	}

	public void setDataType(String dataType){
		this.dataType = dataType;
	}
	
	public String getEditTask(){
		return this.editTask;
	}

	public void setEditTask(String editTask){
		this.editTask = editTask;
	}
	
	
}