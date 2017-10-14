package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.TableColumn;
import com.framework.bean.common.Page;
import com.framework.model.ProTable;

public interface TableService {

	void queryTableList(Page<ProTable> page, ProTable proTable) throws Exception;

	void refreshTables() throws Exception;

	void update(ProTable proTable) throws Exception;

	List<TableColumn> queryTableColumns(String tableName) throws Exception;

	void deleteTables(List<ProTable> proTables) throws Exception;

	Page<Map<String, Object>> sqlSearch(String sql, Page<Map<String, Object>> page) throws Exception;

	int sqlUpdate(String sql) throws Exception;

}
