package com.framework.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.model.SysRoleAuthRelation;

public interface SysRoleAuthRelationMapper extends BaseMapper<SysRoleAuthRelation> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysRoleAuthRelation record);

    SysRoleAuthRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAuthRelation record);

    int updateByPrimaryKey(SysRoleAuthRelation record);
}