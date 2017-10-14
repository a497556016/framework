package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.model.ActReModel;

public interface ActReModelMapper extends BaseMapper<ActReModel> {

    int deleteByPrimaryKey(String id);

    int insertSelective(ActReModel record);

    ActReModel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActReModel record);

    int updateByPrimaryKey(ActReModel record);
    
    List<ActReModel> queryActReModelList(Page<ActReModel> mppage,Map<String, Object> params);
}