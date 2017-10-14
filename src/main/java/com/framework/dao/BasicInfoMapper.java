package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.model.BasicInfo;

public interface BasicInfoMapper extends BaseMapper<BasicInfo> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(BasicInfo record);

    BasicInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasicInfo record);

    int updateByPrimaryKey(BasicInfo record);
    
    List<BasicInfo> queryBasicInfoList(Page<BasicInfo> mppage,Map<String, Object> params);

	List<BasicInfo> getBasicTypes(Page<BasicInfo> mppage, Map<String, Object> params);
}