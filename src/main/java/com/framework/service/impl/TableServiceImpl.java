package com.framework.service.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.bean.TableColumn;
import com.framework.bean.common.Page;
import com.framework.dao.ProTableMapper;
import com.framework.model.ProTable;
import com.framework.service.TableService;
import com.framework.util.PageFactory;

@Service
public class TableServiceImpl implements TableService {
	@Autowired
	private ProTableMapper proTableMapper;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public void queryTableList(Page<ProTable> page, ProTable proTable) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<ProTable> page2 = PageFactory.createFrom(page);
		EntityWrapper<ProTable> wrapper = new EntityWrapper<>();
		wrapper.like("table_name", proTable.getTableName());
		List<ProTable> proTables = proTableMapper.selectPage(page2, wrapper);
		
		page.setRows(proTables);
		page.setTotal(page2.getTotal());
		page.setSuccess(true);
	}

	@Override
	public void refreshTables() throws Exception {
		Connection connection = sqlSessionFactory.openSession().getConnection();
		String catalog = connection.getCatalog();
		proTableMapper.refreshTables(catalog);
	}

	@Override
	public void update(ProTable proTable) throws Exception {
		proTableMapper.updateByPrimaryKey(proTable);
	}

	@Override
	public List<TableColumn> queryTableColumns(String tableName) throws Exception {
		List<TableColumn> tableColumns = proTableMapper.queryTableColumns(tableName);
		return tableColumns;
	}

	@Override
	public void deleteTables(List<ProTable> proTables) throws Exception {
		int count = 0;
		for (ProTable proTable : proTables) {
			count += proTableMapper.deleteByPrimaryKey(proTable.getId());
		}
		if(count!=proTables.size()){
			throw new Exception("删除失败！");
		}
	}

	@Override
	public Page<Map<String, Object>> sqlSearch(String sql, Page<Map<String, Object>> page) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("sql", sql);
		params.put("offset", page.getOffset());
		params.put("limit", page.getLimit());
		List<Map<String, Object>> list = proTableMapper.sqlSearch(params);
		long total = proTableMapper.sqlSearchCount(params);
		page.setRows(list);
		page.setTotal(total);
		page.setSuccess(true);
		return page;
	}

	@Override
	public int sqlUpdate(String sql) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("sql", sql);
		int i = proTableMapper.sqlUpdate(params);
		return i;
	}

}
