package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.model.ActReProcdef;

public interface ActReProcdefMapper extends BaseMapper<ActReProcdef> {

    int deleteByPrimaryKey(String id);

    int insertSelective(ActReProcdef record);

    ActReProcdef selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActReProcdef record);

    int updateByPrimaryKey(ActReProcdef record);
    
    List<ActReProcdef> queryActReProcdefList(Page<ActReProcdef> mppage,Map<String, Object> params);
}