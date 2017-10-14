function editModel(modelId){
	parent.addTabPanel('编辑流程模型',basePath + '/act/process-editor/modeler.jsp?modelId='+modelId);
}

Ext.define('App.Framework.ActReModelManage.AddActReModelWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.formPanel = Ext.create('Ext.form.Panel',{
			layout : 'form',
			defaults : {
				xtype : 'textfield',
				labelWidth : 100,
				msgTarget : 'side'
			},
			items : [
				{
					xtype : 'hidden',
					name : 'id'
				},
				{
					xtype : 'textfield',
					fieldLabel : '名称',
					name : 'name',
					allowBlank : false,
				},
				{
					xtype : 'textfield',
					fieldLabel : '编码',
					name : 'key',
					allowBlank : false,
				},
				{
					xtype : 'appCombobox',
					fieldLabel : '类型',
					name : 'category',
					allowBlank : false,
					editable : false,
					basicType : 'processCategory'
				},
				{
					xtype : 'textarea',
					fieldLabel : '描述',
					name : 'description',
					allowBlank : true,
				}
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
						
						App.request('/actReModel/'+(update?'update':'add')+'ActReModel.do',data,function(result){
							if(result.success){
								App.alert('保存流程模型成功！',function(){
									var modelId = result.model;
									editModel(modelId);
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


Ext.define("App.Framework.ActReModelManage.QueryPanel",{
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
		        		xtype : 'textfield',
		        		fieldLabel : '名称',
		        		name : 'name',
					},
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '编码',
		        		name : 'key',
					},
		        	{
		        		xtype : 'numberfield',
		        		fieldLabel : '版本',
		        		name : 'version',
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

Ext.define("App.Framework.ActReModelManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/actReModel/queryActReModelList',
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
		    	text : '名称',
		    	dataIndex : 'name',
		    },
		    {
		    	text : '编码',
		    	dataIndex : 'key',
		    },
		    {
		    	text : '类型',
		    	dataIndex : 'category',
		    },
		    {
		    	text : '创建时间',
		    	dataIndex : 'createTime',
		    },
		    {
		    	text : '修改时间',
		    	dataIndex : 'lastUpdateTime',
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
        		xtype : 'actioncolumn',
        		align : 'center',
        		text : '编辑',
        		items : [{
        			iconCls : 'Applicationedit',
                    tooltip: '编辑流程模型信息',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = grid.getStore().getAt(rowIndex);
                    	editModel(rec.get('id'));
                    }
        		},'-',{
        			iconCls : 'Pagesave',
        			tooltip : '部署流程',
        			handler : function(grid, rowIndex, colIndex){
        				var rec = grid.getStore().getAt(rowIndex);
        				App.request('/actReModel/deploy',{
        					id : rec.get('id')
        				},function(result){
        					if(result.success){
        						App.alert('流程部署成功！',function(){
        							grid.getStore().reload();
        						});
        					}
        				});
        			}
        		}]
        	}],
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: me.store,   // GridPanel中使用的数据
		        displayInfo: true
			}],
			listeners : {
				
			},
			tbar : [{
				text : '新增',
				iconCls : 'Add',
				handler : function(){
					Ext.create('App.Framework.ActReModelManage.AddActReModelWind',{
						title : '新增流程模型',
						grid : me
					}).show();
				}
			},{
				text : '删除',
				iconCls : 'Delete',
				handler : function(){
					
					var records = me.selModel.getSelection();
					if(records.length>0){
						App.confirm('是否确认删除？',function(ok){
							if(ok){
								var datas = [];
								Ext.each(records,function(record,index){
									datas.push({id:record.get('id')});
								})
								App.request('/actReModel/deleteActReModels',{
									actReModelsJson : Ext.encode(datas)
								},function(result){
									if(result.success){
										App.alert('删除成功！',function(){
											me.getStore().reload();
										});
									}else{
										App.toast(result.message);
									}
								});
							}
						})
					}else{
						App.toast('请选择要删除的数据！');
					}
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.Framework.ActReModelManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.Framework.ActReModelManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.Framework.ActReModelManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

