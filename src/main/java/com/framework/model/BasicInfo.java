package com.framework.model;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("sys_basic_info")
public class BasicInfo {
	
	/**
	 *主键ID
	 */
	private Integer id;
	/**
	 *类型
	 */
	private String type;
	/**
	 *编码
	 */
	private String code;
	/**
	 *名称
	 */
	private String name;
	/**
	 *排序序列
	 */
	private Integer seq;
	/**
	 *备注
	 */
	private String remark;
	/**
	 *有效状态
	 */
	private Boolean enable;
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

	
	public BasicInfo() {
	}
	
	
	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}
	
	public String getType(){
		return this.type;
	}

	public void setType(String type){
		this.type = type;
	}
	
	public String getCode(){
		return this.code;
	}

	public void setCode(String code){
		this.code = code;
	}
	
	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public Integer getSeq(){
		return this.seq;
	}

	public void setSeq(Integer seq){
		this.seq = seq;
	}
	
	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}
	
	public Boolean getEnable() {
		return enable;
	}


	public void setEnable(Boolean enable) {
		this.enable = enable;
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