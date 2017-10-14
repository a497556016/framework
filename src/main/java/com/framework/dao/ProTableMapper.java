package com.framework.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.bean.TableColumn;
import com.framework.model.ProTable;

public interface ProTableMapper extends BaseMapper<ProTable> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(ProTable record);

    ProTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProTable record);

    int updateByPrimaryKey(ProTable record);

	void refreshTables(String catalog);

	List<TableColumn> queryTableColumns(String tableName);

	List<Map<String, Object>> sqlSearch(Map<String, Object> params);

	long sqlSearchCount(Map<String, Object> params);

	int sqlUpdate(Map<String, Object> params);
}