Ext.define('App.Framework.ActReProcdefManage.AddActReProcdefWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.formPanel = Ext.create('Ext.form.Panel',{
			layout : 'form',
			defaults : {
				xtype : 'textfield',
				labelWidth : 100
			},
			items : [
				{
					xtype : 'hidden',
					name : 'id'
				},
				{
					xtype : 'appCombobox',
					fieldLabel : '流程类型',
					name : 'category',
					allowBlank : true,
					basicType : 'processCategory',
				},
				{
					xtype : 'textfield',
					fieldLabel : '流程名称',
					name : 'name',
					allowBlank : true,
				},
				{
					xtype : 'textfield',
					fieldLabel : '流程ID',
					name : 'key',
					allowBlank : true,
				},
				{
					xtype : 'textfield',
					fieldLabel : '版本',
					name : 'version',
					allowBlank : true,
				},
				{
					xtype : 'textfield',
					fieldLabel : '部署ID',
					name : 'deploymentId',
					allowBlank : true,
				},
				{
					xtype : 'textfield',
					fieldLabel : '源',
					name : 'resourceName',
					allowBlank : true,
				},
				{
					xtype : 'textfield',
					fieldLabel : '图片源',
					name : 'dgrmResourceName',
					allowBlank : true,
				},
				{
					xtype : 'textfield',
					fieldLabel : '描述',
					name : 'description',
					allowBlank : true,
				},
			]
		});
		
		Ext.applyIf(config,{
			width : 550,
			layout : 'fit',
			modal : true,
			items : [me.formPanel],
			buttons : [{
				text : '保存',
				iconCls : 'Accept',
				handler : function(){
					var valid = me.formPanel.isValid();
					if(valid){
						var update = config.role?true:false;
						var data = me.formPanel.getValues();
						
						App.request('/actReProcdef/'+(update?'update':'add')+'ActReProcdef.do',data,function(result){
							if(result.success){
								App.alert('保存模板管理成功！',function(){
									config.grid.getStore().reload();
									me.close();
								});
							}else{
								App.alert(result.message);
							}
						});
					}
				}
			},{
				text : '取消',
				iconCls : 'Cancel',
				handler : function(){
					me.close();
				}
			}],
			listeners : {
				afterrender : function(){
					if(config.role){
						me.formPanel.getForm().setValues(config.role);
					}
				}
			}
		});
		this.callParent(arguments);
	}
});


Ext.define("App.Framework.ActReProcdefManage.QueryPanel",{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		Ext.applyIf(config,{
			//title : '查询条件',
        	//collapsible : true,
	        items : [{
	        	layout : {
	        		type : 'column'
	        	},
	        	defaults : {
	        		xtype : 'textfield',
	        		labelWidth : 60,
	        		margin : '5 5 5 5',
	        		columnWidth : 0.333
	        	},
	        	items : [
		        	{
		        		xtype : 'appCombobox',
		        		fieldLabel : '流程类型',
		        		name : 'category',
						basicType : 'processCategory',
					},
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '流程名称',
		        		name : 'name',
					},
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '版本',
		        		name : 'version',
					},
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '描述',
		        		name : 'description',
					},
	        	]
	        }],
	        buttonAlign : 'center',
	        buttons : [{
	        	text : '查询',
	        	iconCls : 'fa fa-search',
	        	handler : function(){
	        		var data = me.getForm().getValues();
	        		var store = me.up('viewport').gridPanel.getStore();
	        		Ext.apply(store.getProxy().extraParams,data);
	        		store.loadPage(1);
	        	}
	        },{
	        	text : '重置',
	        	iconCls : 'fa fa-refresh',
	        	handler : function(){
	        		me.getForm().reset();
	        	}
	        }]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.Framework.ActReProcdefManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/actReProcdef/queryActReProcdefList.do',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			},
			autoLoad : true,
			remoteSort : true,
			pageSize : 10,
			listeners : {
				load : function(store , records , successful , operation , eOpts){
					console.log(records)
				},
				update : function(store, record , operation , modifiedFieldNames , details , eOpts){
					console.log(record.getData(),operation,modifiedFieldNames)
					
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			//mode : 'SINGLE'
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
		    columns : [
			{
				text : 'ID',
				dataIndex : 'id',
				width : 100
			},
		    {
		    	text : '流程类型',
		    	dataIndex : 'category',
		    },
		    {
		    	text : '流程名称',
		    	dataIndex : 'name',
		    },
		    {
		    	text : '流程ID',
		    	dataIndex : 'key',
		    },
		    {
		    	text : '版本',
		    	dataIndex : 'version',
		    },
		    {
		    	text : '部署ID',
		    	dataIndex : 'deploymentId',
		    },
		    {
		    	text : '源',
		    	dataIndex : 'resourceName',
		    },
		    {
		    	text : '图片源',
		    	dataIndex : 'dgrmResourceName',
		    },
		    {
		    	text : '描述',
		    	dataIndex : 'description',
		    },
		    {
        		xtype : 'actioncolumn',
        		align : 'center',
        		text : '查看流程图',
        		items : [{
        			iconCls : 'Applicationviewgallery',
                    tooltip: '查看流程图',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = grid.getStore().getAt(rowIndex);
                    	Ext.create('Ext.window.Window',{
                    		title : '流程图',
                    		layout : 'fit',
                    		maximized : true,
                    		items : [{
                    			xtype : 'uxiframe',
                    			src : basePath + '/act/diagram-viewer/index.html?processDefinitionId='+rec.get('id'),
                    			listeners : {
                    				load : function(src){
                    					console.log(src)
                    				}
                    			}
                    			
                    		}]	
                    	}).show();
                    }
        		}]
        	}],
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: me.store,   // GridPanel中使用的数据
		        displayInfo: true
			}],
			listeners : {
				
			}
		});
		this.callParent(arguments);
	}
});

Ext.define("App.Framework.ActReProcdefManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.Framework.ActReProcdefManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.Framework.ActReProcdefManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

