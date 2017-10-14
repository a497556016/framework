package com.framework.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.model.ProForm;

public interface ProFormMapper extends BaseMapper<ProForm> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(ProForm record);

    ProForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProForm record);

    int updateByPrimaryKey(ProForm record);

	int getTableFormVersion(String tableName);
}