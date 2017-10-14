package com.${packageName}.model;

import java.util.*;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("${form.tableName}")
public class ${className} {
	
	<#list fieldList as field>
	/**
	 *${field.comments}
	 */
	private <#if field.dataType=='integer'>Integer<#elseif field.dataType=='blob'>byte[]<#elseif field.dataType=='timestamp'>Date<#elseif field.dataType=='decimal'>Double<#else>String</#if> ${field.name};
	</#list>
	
	public ${className}() {
	}
	
	<#list fieldList as field>
	
	public <#if field.dataType=='integer'>Integer<#elseif field.dataType=='blob'>byte[]<#elseif field.dataType=='timestamp'>Date<#elseif field.dataType=='decimal'>Double<#else>String</#if> get${field.name?cap_first}(){
		return this.${field.name};
	}

	public void set${field.name?cap_first}(<#if field.dataType=='integer'>Integer<#elseif field.dataType=='blob'>byte[]<#elseif field.dataType=='timestamp'>Date<#elseif field.dataType=='decimal'>Double<#else>String</#if> ${field.name}){
		this.${field.name} = ${field.name};
	}
	</#list>
	
	
}