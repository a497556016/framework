package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.model.WfFormField;
import com.framework.model.vo.WfFormFieldVO;

public interface WfFormFieldMapper extends BaseMapper<WfFormField> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(WfFormFieldVO record);

    WfFormFieldVO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WfFormFieldVO record);

    int updateByPrimaryKey(WfFormFieldVO record);
    
    List<WfFormFieldVO> queryWfFormFieldList(Page<WfFormFieldVO> mppage,Map<String, Object> params);
}