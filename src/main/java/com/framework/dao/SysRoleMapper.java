package com.framework.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.model.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}