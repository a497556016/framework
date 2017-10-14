package com.${packageName}.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.${packageName}.model.${className};
import com.${packageName}.model.vo.${className}VO;

public interface ${className}Mapper extends BaseMapper<${className}> {

    int deleteByPrimaryKey(<#if priField.dataType=='integer'>Integer<#elseif priField.dataType=='timestamp'>Date<#elseif priField.dataType=='decimal'>Double<#else>String</#if> ${priField.name});

    int insertSelective(${className}VO record);

    ${className}VO selectByPrimaryKey(<#if priField.dataType=='integer'>Integer<#elseif priField.dataType=='timestamp'>Date<#elseif priField.dataType=='decimal'>Double<#else>String</#if> ${priField.name});

    int updateByPrimaryKeySelective(${className}VO record);

    int updateByPrimaryKey(${className}VO record);
    
    List<${className}VO> query${className}List(Page<${className}VO> mppage,Map<String, Object> params);
}