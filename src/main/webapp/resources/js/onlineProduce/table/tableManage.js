Ext.define("App.LineProduce.TableManage.QueryPanel",{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		Ext.applyIf(config,{
			//title : '查询条件',
        	//collapsible : true,
	        items : [{
	        	layout : {
	        		type : 'table',
	        		columns : 5
	        	},
	        	items : [{
	        		xtype : 'textfield',
	        		fieldLabel : '名称',
	        		labelWidth : 35,
	        		name : 'text',
	        		margin : '5 5 5 5'
	        	}]
	        }],
	        buttonAlign : 'center',
	        buttons : [{
	        	text : '查询',
	        	handler : function(){
	        		var data = me.getForm().getValues();
	        		var store = me.up('viewport').gridPanel.getStore();
	        		Ext.apply(store.getProxy().extraParams,data);
	        		store.loadPage(1);
	        	}
	        },{
	        	text : '重置',
	        	handler : function(){
	        		me.getForm().reset();
	        	}
	        }]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.LineProduce.TableManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/table/queryTableList',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
		
			},
			autoLoad : true,
			remoteSort : true,
			pageSize : 15,
			listeners : {
				load : function(store , records , successful , operation , eOpts){
					console.log(records)
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SINGLE'
		});
		
		Ext.applyIf(config,{
			//title : '列表',
        	columnLines : true,
			rowLines : true,
			viewConfig : {
		    	enableTextSelection : true
		    },
		    plugins: {
		        ptype: 'cellediting',
		        clicksToEdit: 1,
		        listeners : {
		        	edit : function(editor , context , eOpts){
		        		//console.log(editor , context , eOpts)
		        		
		        	}
		        }
		    },
		    columns : [{
		    	xtype : 'rownumberer'
		    },{
        		text : 'ID',
        		dataIndex : 'id',
        		hidden : true
        	},{
        		text : '表名',
        		dataIndex : 'tableName',
        		width : 150
        	},{
        		text : '描述',
        		dataIndex : 'tableDesc',
        		flex : 1
        	}],
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: me.store,   // GridPanel中使用的数据
		        displayInfo: true
			}],
			listeners : {
				
			},
			tbar : [{
				text : '刷新表',
				iconCls : 'fa fa-refresh',
				handler : function(){
					App.request('/table/refreshTables',{},function(re){
						if(re.success){
							me.store.loadPage(1);
						}
					});
				}
			},'-',{
				text : '删除',
				iconCls : 'fa fa-remove ',
				handler : function(){
					
					var records = me.selModel.getSelection();
					if(records.length>0){
						App.confirm('是否确认删除？',function(){
							var users = [];
							Ext.each(records,function(record,index){
								users.push(record.getData());
							})
							App.request('/user/deleteUsers',{
								userInfosJson : Ext.encode(users)
							},function(result){
								if(result.success){
									App.alert('删除成功！',function(){
										me.getStore().reload();
									});
								}else{
									App.toast(result.message);
								}
							});
						})
					}else{
						App.toast('请选择要删除的数据！');
					}
				}
			},'-',{
				text : '创建表单',
				iconCls : 'fa fa-plus',
				handler : function(){
					var record = me.selModel.getLastSelected();
					if(!record){
						App.toast('请选择表');
					}else{
						parent.addTabPanel('创建表单',basePath + '/form/toCreateForm?tableName='+record.get('tableName'))
					}
				}
			},'-',{
				text : 'SQL查询',
				iconCls : 'fa fa-search',
				handler : function(){
					parent.addTabPanel('SQL查询',basePath + '/table/toSqlSearch');
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.LineProduce.TableManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.LineProduce.TableManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.LineProduce.TableManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});