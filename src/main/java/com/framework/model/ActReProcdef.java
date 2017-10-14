package com.framework.model;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("act_re_procdef")
public class ActReProcdef {
	
	/**
	 *主键ID
	 */
	private String id;
	/**
	 *
	 */
	private Integer rev;
	/**
	 *流程类型
	 */
	private String category;
	/**
	 *流程名称
	 */
	private String name;
	/**
	 *流程ID
	 */
	private String key;
	/**
	 *版本
	 */
	private Integer version;
	/**
	 *部署ID
	 */
	private String deploymentId;
	/**
	 *源
	 */
	private String resourceName;
	/**
	 *图片源
	 */
	private String dgrmResourceName;
	/**
	 *描述
	 */
	private String description;
	/**
	 *
	 */
	private String hasStartFormKey;
	/**
	 *
	 */
	private Integer suspensionState;
	/**
	 *
	 */
	private String tenantId;
	/**
	 *
	 */
	private String hasGraphicalNotation;
	
	public ActReProcdef() {
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
	
	public String getCategory(){
		return this.category;
	}

	public void setCategory(String category){
		this.category = category;
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
	
	public Integer getVersion(){
		return this.version;
	}

	public void setVersion(Integer version){
		this.version = version;
	}
	
	public String getDeploymentId(){
		return this.deploymentId;
	}

	public void setDeploymentId(String deploymentId){
		this.deploymentId = deploymentId;
	}
	
	public String getResourceName(){
		return this.resourceName;
	}

	public void setResourceName(String resourceName){
		this.resourceName = resourceName;
	}
	
	public String getDgrmResourceName(){
		return this.dgrmResourceName;
	}

	public void setDgrmResourceName(String dgrmResourceName){
		this.dgrmResourceName = dgrmResourceName;
	}
	
	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}
	
	public String getHasStartFormKey(){
		return this.hasStartFormKey;
	}

	public void setHasStartFormKey(String hasStartFormKey){
		this.hasStartFormKey = hasStartFormKey;
	}
	
	public Integer getSuspensionState(){
		return this.suspensionState;
	}

	public void setSuspensionState(Integer suspensionState){
		this.suspensionState = suspensionState;
	}
	
	public String getTenantId(){
		return this.tenantId;
	}

	public void setTenantId(String tenantId){
		this.tenantId = tenantId;
	}
	
	public String getHasGraphicalNotation(){
		return this.hasGraphicalNotation;
	}

	public void setHasGraphicalNotation(String hasGraphicalNotation){
		this.hasGraphicalNotation = hasGraphicalNotation;
	}
	
	
}