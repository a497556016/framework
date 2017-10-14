package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.model.SysAuthority;

public interface SysAuthorityMapper extends BaseMapper<SysAuthority> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysAuthority record);

    SysAuthority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAuthority record);

    int updateByPrimaryKey(SysAuthority record);

	List<SysAuthority> queryAllAuthes(Map<String, Object> params);

	int countAllAuthes(Map<String, Object> params);
}