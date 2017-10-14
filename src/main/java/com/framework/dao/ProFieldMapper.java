package com.framework.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.model.ProField;

public interface ProFieldMapper extends BaseMapper<ProField> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(ProField record);

    ProField selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProField record);

    int updateByPrimaryKey(ProField record);

	List<ProField> getTableFields(String tableName);
}