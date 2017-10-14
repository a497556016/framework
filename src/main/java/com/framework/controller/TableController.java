package com.framework.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.framework.bean.TableColumn;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.model.ProTable;
import com.framework.model.SysUserInfo;
import com.framework.service.TableService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/table")
public class TableController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private TableService tableService;
	
	@RequestMapping("/tableManage")
	public String tableManage(){
		return "onlineProduce/table/tableManage";
	}
	
	@RequestMapping("/toSqlSearch")
	public String toSqlSearch(){
		return "onlineProduce/table/sqlSearch";
	}

	@RequestMapping("/toShowTableColumns")
	public String toShowTableColumns(Model model,String tableName){
		List<TableColumn> tableColumns = new ArrayList<>();
		try {
			tableColumns = tableService.queryTableColumns(tableName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("tableColumns", tableColumns);
		return "onlineProduce/table/tableColumns";
	}
	
	@RequestMapping("/queryTableList")
	@ResponseBody
	public Page<ProTable> queryTableList(Page<ProTable> page,ProTable proTable){
		try{
			tableService.queryTableList(page,proTable);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return page;
	}
	
	@RequestMapping("/refreshTables")
	@ResponseBody
	public ResultMessage<?> refreshTables(){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			tableService.refreshTables();
			resultMessage.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return resultMessage;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ResultMessage<?> update(ProTable proTable){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			tableService.update(proTable);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return resultMessage;
	}
	
	@RequestMapping("/deleteTables")
	@ResponseBody
	public ResultMessage<?> deleteTables(String tables){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			List<ProTable> proTables = gson.fromJson(tables, new TypeToken<List<ProTable>>(){}.getType());
			tableService.deleteTables(proTables);
			resultMessage.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return resultMessage;
	}
	
	@RequestMapping("/sqlSearch")
	@ResponseBody
	public Page<Map<String,Object>> sqlSearch(Page<Map<String,Object>> page,String sql){
		try{
			page = tableService.sqlSearch(sql,page);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			page.setMessage(e.getMessage());
		}
		return page;
	}
	
	@RequestMapping("/sqlUpdate")
	@ResponseBody
	public ResultMessage<?> sqlUpdate(String sql){
		ResultMessage<?> resultMessage = new ResultMessage<>();
		try{
			int i = tableService.sqlUpdate(sql);
			if(i>=0){
				resultMessage.setSuccess(true);
				resultMessage.setMessage(i+"条记录受影响。");
			}else{
				resultMessage.setMessage("更新语句有错误。");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	}
}
