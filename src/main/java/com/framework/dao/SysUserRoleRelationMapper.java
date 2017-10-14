package com.framework.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.model.SysRole;
import com.framework.model.SysUserRoleRelation;

public interface SysUserRoleRelationMapper extends BaseMapper<SysUserRoleRelation> {

    int insertSelective(SysUserRoleRelation record);

    int updateByPrimaryKeySelective(SysUserRoleRelation record);
    
	List<SysRole> selectRoleByPersonCode(String personCode);
}