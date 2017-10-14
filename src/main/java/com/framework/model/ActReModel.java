package com.framework.model;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("act_re_model")
public class ActReModel {
	
	/**
	 *主键ID
	 */
	private String id;
	/**
	 *
	 */
	private Integer rev;
	/**
	 *名称
	 */
	private String name;
	/**
	 *编码
	 */
	private String key;
	/**
	 *类型
	 */
	private String category;
	/**
	 *创建时间
	 */
	private Date createTime;
	/**
	 *修改时间
	 */
	private Date lastUpdateTime;
	/**
	 *版本
	 */
	private Integer version;
	/**
	 *
	 */
	private String metaInfo;
	/**
	 *部署ID
	 */
	private String deploymentId;
	/**
	 *
	 */
	private String editorSourceValueId;
	/**
	 *
	 */
	private String editorSourceExtraValueId;
	/**
	 *
	 */
	private String tenantId;
	
	public ActReModel() {
	}
	
	
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}
	
	public Integer getRev(){
		return this.rev;
	}

	public void setRev(Integer rev){
		this.rev = rev;
	}
	
	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getKey(){
		return this.key;
	}

	public void setKey(String key){
		this.key = key;
	}
	
	public String getCategory(){
		return this.category;
	}

	public void setCategory(String category){
		this.category = category;
	}
	
	public Date getCreateTime(){
		return this.createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getLastUpdateTime(){
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public Integer getVersion(){
		return this.version;
	}

	public void setVersion(Integer version){
		this.version = version;
	}
	
	public String getMetaInfo(){
		return this.metaInfo;
	}

	public void setMetaInfo(String metaInfo){
		this.metaInfo = metaInfo;
	}
	
	public String getDeploymentId(){
		return this.deploymentId;
	}

	public void setDeploymentId(String deploymentId){
		this.deploymentId = deploymentId;
	}
	
	public String getEditorSourceValueId(){
		return this.editorSourceValueId;
	}

	public void setEditorSourceValueId(String editorSourceValueId){
		this.editorSourceValueId = editorSourceValueId;
	}
	
	public String getEditorSourceExtraValueId(){
		return this.editorSourceExtraValueId;
	}

	public void setEditorSourceExtraValueId(String editorSourceExtraValueId){
		this.editorSourceExtraValueId = editorSourceExtraValueId;
	}
	
	public String getTenantId(){
		return this.tenantId;
	}

	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}
	
	
}