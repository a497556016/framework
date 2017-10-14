package com.framework.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.model.SysAuthResRelation;

public interface SysAuthResRelationMapper extends BaseMapper<SysAuthResRelation> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysAuthResRelation record);

    SysAuthResRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAuthResRelation record);

    int updateByPrimaryKey(SysAuthResRelation record);
}