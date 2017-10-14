package com.framework.model;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_pro_table")
public class ProTable {
    private Integer id;

    private String tableName;

    private String tableDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }
}