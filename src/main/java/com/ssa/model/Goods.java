package com.ssa.model;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_goods")
public class Goods {
	
	/**
	 *主键
	 */
	private Integer id;
	/**
	 *名称
	 */
	private String name;
	/**
	 *类别
	 */
	private Integer categoryId;
	/**
	 *编码
	 */
	private String code;
	/**
	 *分组
	 */
	private String group;
	/**
	 *单价
	 */
	private Double price;
	/**
	 *原价
	 */
	private Double lastPrice;
	/**
	 *上架状态（上架、定时上架、库存）
	 */
	private String shelvesStatus;
	/**
	 *上架时间
	 */
	private Date shelvesTime;
	/**
	 *折扣
	 */
	private Double discount;
	/**
	 *
	 */
	private String createBy;
	/**
	 *
	 */
	private Date createDate;
	/**
	 *
	 */
	private String modifyBy;
	/**
	 *
	 */
	private Date modifyDate;
	
	public Goods() {
	}
	
	
	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public Integer getCategoryId(){
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
	}
	
	public String getCode(){
		return this.code;
	}

	public void setCode(String code){
		this.code = code;
	}
	
	public String getGroup(){
		return this.group;
	}

	public void setGroup(String group){
		this.group = group;
	}
	
	public Double getPrice(){
		return this.price;
	}

	public void setPrice(Double price){
		this.price = price;
	}
	
	public Double getLastPrice(){
		return this.lastPrice;
	}

	public void setLastPrice(Double lastPrice){
		this.lastPrice = lastPrice;
	}
	
	public String getShelvesStatus(){
		return this.shelvesStatus;
	}

	public void setShelvesStatus(String shelvesStatus){
		this.shelvesStatus = shelvesStatus;
	}
	
	public Date getShelvesTime(){
		return this.shelvesTime;
	}

	public void setShelvesTime(Date shelvesTime){
		this.shelvesTime = shelvesTime;
	}
	
	public Double getDiscount(){
		return this.discount;
	}

	public void setDiscount(Double discount){
		this.discount = discount;
	}
	
	public String getCreateBy(){
		return this.createBy;
	}

	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	
	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	
	public String getModifyBy(){
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy){
		this.modifyBy = modifyBy;
	}
	
	public Date getModifyDate(){
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}
	
	
}