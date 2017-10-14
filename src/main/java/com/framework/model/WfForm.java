package com.framework.model;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_wf_form")
public class WfForm {
	
	/**
	 *主键ID
	 */
	private String id;
	/**
	 *表单名称
	 */
	private String formName;
	/**
	 *关联流程
	 */
	private String processKey;
	/**
	 *创建时间
	 */
	private Date createDate;
	/**
	 *创建人
	 */
	private String createBy;
	/**
	 *修改时间
	 */
	private Date modifyDate;
	/**
	 *修改人
	 */
	private String modifyBy;
	
	public WfForm() {
	}
	
	
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}
	
	public String getFormName(){
		return this.formName;
	}

	public void setFormName(String formName){
		this.formName = formName;
	}
	
	public String getProcessKey(){
		return this.processKey;
	}

	public void setProcessKey(String processKey){
		this.processKey = processKey;
	}
	
	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	
	public Date getModifyDate(){
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	
	public String getModifyBy(){
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy){
		this.modifyBy = modifyBy;
	}
	
	
}