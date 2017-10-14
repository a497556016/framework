<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.${packageName}.dao.${className}Mapper" >
  <resultMap id="BaseResultMap" type="com.${packageName}.model.vo.${className}VO" >
    <#list fieldList as field>
    <#if field.pri>
    <id column="${field.columnName}" property="${field.name}" jdbcType="${field.dataType?upper_case}" />
    <#else>
    <result column="${field.columnName}" property="${field.name}" jdbcType="${field.dataType?upper_case}" />
    </#if>
    </#list>
  </resultMap>
  
  <sql id="Base_Column_List" >
  	<#list fieldList as field>
  	${field.columnName}<#if field_index!=fieldList?size-1>,</#if>
  	</#list>
  </sql>
  
  <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.<#if priField.dataType=='int'>Integer<#else>String</#if>" >
    select 
    <include refid="Base_Column_List" />
    from ${form.tableName}
    where ${priField.columnName} = ${'#'}{${priField.name},jdbcType=${priField.dataType?upper_case}}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.<#if priField.dataType=='int'>Integer<#else>String</#if>" >
    delete from ${form.tableName}
   where ${priField.columnName} = ${'#'}{${priField.name},jdbcType=${priField.dataType?upper_case}}
  </delete>
  
  <insert id="insert" useGeneratedKeys="true" keyProperty="id" parameterType="com.${packageName}.model.vo.${className}VO" >
    insert into ${form.tableName} (
		<#list fieldList as field>
		${field.columnName}<#if field_index!=fieldList?size-1>,</#if>
		</#list>
    )
    values (
    	<#list fieldList as field>
    	${'#'}{${field.name},jdbcType=${field.dataType?upper_case}}<#if field_index!=fieldList?size-1>,</#if>
    	</#list>
    )
  </insert>
  <insert id="insertSelective" parameterType="com.${packageName}.model.vo.${className}VO" >
    insert into ${form.tableName}
    <trim prefix="(" suffix=")" suffixOverrides="," >
    	<#list fieldList as field>
    	<if test="${field.name} != null" >
        ${field.columnName},
      	</if>
		</#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides="," >
      	<#list fieldList as field>
      	<if test="${field.name} != null" >
        ${'#'}{${field.name},jdbcType=${field.dataType?upper_case}},
      	</if>
    	</#list>
    </trim>
  </insert>
  
  <update id="updateByPrimaryKeySelective" parameterType="com.${packageName}.model.vo.${className}VO" >
    update ${form.tableName}
    <set >
    	<#list fieldList as field>
    	<#if !field.pri>
    	<if test="${field.name} != null" >
        ${field.columnName} = ${'#'}{${field.name},jdbcType=${field.dataType?upper_case}},
      	</if>
      	</#if>
    	</#list>
    </set>
    where ${priField.columnName} = ${'#'}{${priField.name},jdbcType=${priField.dataType?upper_case}}
  </update>
  <update id="updateByPrimaryKey" parameterType="com.${packageName}.model.vo.${className}VO" >
    update ${form.tableName}
    set 
    	<#list fieldList as field>
    	<#if !field.pri>
        ${field.columnName} = ${'#'}{${field.name},jdbcType=${field.dataType?upper_case}},
      	</#if>
    	</#list>
    where ${priField.columnName} = ${'#'}{${priField.name},jdbcType=${priField.dataType?upper_case}}
  </update>
  <select id="query${className}List" parameterType="HashMap" resultMap="BaseResultMap">
  	select 
    <include refid="Base_Column_List" />
    from ${form.tableName}
    where 1 = 1
    <#list fieldList as field>
	<#if field.query>
	<#if field.queryType=='1'>
	<if test="${field.name} != null and ${field.name} != ''" >
	and ${field.columnName} = ${'#'}{${field.name},jdbcType=${field.dataType?upper_case}}
	</if>
	<#elseif field.queryType=='2'>
	<if test="${field.name} != null and ${field.name} != ''" >
	and ${field.columnName} like '%${'$'}{${field.name}}%'
	</if>
	<#elseif field.queryType=='3'>
	<if test="${field.name}Begin != null and ${field.name}Begin != ''" >
	<![CDATA[and ${field.columnName} >= ${'#'}{${field.name}Begin,jdbcType=${field.dataType?upper_case}}]]>
	</if>
	<if test="${field.name}End != null and ${field.name}End != ''" >
	<![CDATA[and ${field.columnName} <= ${'#'}{${field.name}End,jdbcType=${field.dataType?upper_case}}]]>
	</if>
	</#if>
	</#if>
	</#list>
  </select>
</mapper>