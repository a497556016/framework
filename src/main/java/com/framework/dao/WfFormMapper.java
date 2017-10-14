package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.model.WfForm;
import com.framework.model.vo.WfFormVO;

public interface WfFormMapper extends BaseMapper<WfForm> {

    int deleteByPrimaryKey(String id);

    int insertSelective(WfFormVO record);

    WfFormVO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WfFormVO record);

    int updateByPrimaryKey(WfFormVO record);
    
    List<WfFormVO> queryWfFormList(Page<WfFormVO> mppage,Map<String, Object> params);
}