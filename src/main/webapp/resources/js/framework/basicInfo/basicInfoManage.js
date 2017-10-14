Ext.define('App.Framework.BasicInfoManage.AddBasicInfoWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.formPanel = Ext.create('Ext.form.Panel',{
			region : 'north',
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
					fieldLabel : '类型',
					name : 'type',
					allowBlank : false,
				},
				{
					fieldLabel : '备注',
					name : 'remark',
					allowBlank : false,
				}
			]
		});
		me.itemPanel = Ext.create('Ext.grid.Panel',{
			region : 'center',
			columnLines : true,
			rowLines : true,
			store : {
				xtype : 'datastore',
				data : []
			},
			selModel : {
				selType : 'checkboxmodel'
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
				text : '编码',
				dataIndex : 'code',
				flex : 0.2,
				editor : {
					xtype : 'textfield',
					allowBlank : false
				}
			},{
				text : '名称',
				dataIndex : 'name',
				flex : 0.4,
				editor : {
					xtype : 'textfield',
					allowBlank : false
				}
			},{
				text : '排序序列',
				dataIndex : 'seq',
				flex : 0.2,
				editor : {
					xtype : 'numberfield',
					allowBlank : false
				}
			},{
				xtype : 'checkcolumn',
				text : '有效状态',
				dataIndex : 'enable',
				flex : 0.2,
				editor : {
					xtype : 'checkbox'
				}
			}],
			tbar : [{
				text : '添加',
				iconCls : 'fa fa-plus',
				handler : function(){
					var store = me.itemPanel.getStore();
					var count = store.getCount();
					store.insert(count,{
						seq : count,
						enable : true
					});
				}
			},'-',{
				text : '删除',
				iconCls : 'fa fa-remove',
				handler : function(){
					var selModel = me.itemPanel.selModel;
					var store = me.itemPanel.getStore();
					store.remove(selModel.getSelection());
					store.each(function(record,index){
						record.set('seq',index);
					});
				}
			}]
		});
		Ext.applyIf(config,{
			width : 550,
			height : 500,
			layout : 'border',
			modal : true,
			items : [me.formPanel,me.itemPanel],
			buttons : [{
				text : '保存',
				iconCls : 'Accept',
				handler : function(){
					var valid = me.formPanel.isValid();
					if(valid){
						var update = config.role?true:false;
						var data = me.formPanel.getValues();
						if(!data.id){
							delete data.id;
						}
						var items = App.storeToArray(me.itemPanel.getStore(),['id']);
						Ext.each(items,function(item){
							Ext.apply(item,data);
						})
						console.log(items);
						App.request('/basicInfo/'+(update?'update':'add')+'BasicInfo.do',{
							itemsJson : Ext.encode(items)
						},function(result){
							if(result.success){
								App.alert('保存数据字典成功！',function(){
									config.grid.getStore().reload({
										callback : function(records){
											config.grid.selModel.select(records[records.length-1]);
										}
									});
									
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


Ext.define("App.Framework.BasicInfoManage.QueryPanel",{
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
	        		columnWidth:0.25
	        	},
	        	items : [
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '类型',
		        		name : 'type'
					},
		        	{
		        		fieldLabel : '备注',
		        		name : 'remark'
					},
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '编码',
		        		name : 'code'
					},
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '名称',
		        		name : 'name'
					}
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
	        		
	        		var store1 = me.up('viewport').basicTypePanel.getStore();
	        		Ext.apply(store1.getProxy().extraParams,data);
	        		store1.loadPage(1);
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

Ext.define("App.Framework.BasicInfoManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/basicInfo/queryBasicInfoList',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			},
			autoLoad : false,
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
		    	text : '类型',
		    	dataIndex : 'type',
		    },
		    {
		    	text : '备注',
		    	dataIndex : 'remark',
		    },
		    {
		    	text : '编码',
		    	dataIndex : 'code',
		    	editor : {
		    		xtype : 'textfield'
		    	}
		    },
		    {
		    	text : '名称',
		    	dataIndex : 'name',
		    	editor : {
		    		xtype : 'textfield'
		    	}
		    },
		    {
		    	text : '排序序列',
		    	dataIndex : 'seq',
		    	editor : {
		    		xtype : 'numberfield'
		    	}
		    },
		    {
		    	xtype : 'checkcolumn',
		    	text : '有效状态',
		    	dataIndex : 'enable',
		    	editor : {
		    		xtype : 'checkbox'
		    	}
		    },
		    {
		    	text : '创建时间',
		    	dataIndex : 'createDate',
		    },
		    {
		    	text : '创建人',
		    	dataIndex : 'createBy',
		    },
		    {
		    	text : '修改时间',
		    	dataIndex : 'modifyDate',
		    },
		    {
		    	text : '修改人',
		    	dataIndex : 'modifyBy',
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
				iconCls : 'fa fa-plus',
				handler : function(){
					var selRecord = me.up('viewport').basicTypePanel.selModel.getLastSelected();
					if(!selRecord){
						App.alert('请选择数据类型！');
						return;
					}
					me.store.add({
						id : -1*me.store.getCount(),
						enable : '1',
						seq : me.store.getCount(),
						type : selRecord.get('type'),
						remark : selRecord.get('remark')
					});
				}
			},'-',{
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
								App.request('/basicInfo/deleteBasicInfos',{
									basicInfosJson : Ext.encode(datas)
								},function(result){
									if(result.success){
										App.alert('删除成功！',function(){
											me.getStore().reload();
											me.up('viewport').basicTypePanel.getStore().reload();
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
			},'-',{
				text : '保存',
				iconCls : 'fa fa-save',
				handler : function(){
					var records = me.store.getModifiedRecords();
					if(records.length>0){
						var datas = [];
						Ext.each(records,function(record){
							var data = record.getData();
							if(data.id<=0){
								delete data.id;
							}
							datas.push(data);
						});
						App.request('/basicInfo/updateBasicInfo',{
							itemsJson : Ext.encode(datas)
						},function(result){
							if(result.success){
								App.alert('保存成功！',function(){
									me.store.reload();
								});
							}
						});
					}else{
						App.toast('没有需要保存的数据');
					}
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.Framework.BasicInfoManage.BasicTypePanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/basicInfo/getBasicTypes',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
			},
			autoLoad : true,
			remoteSort : true,
			pageSize : 100,
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
			listeners : {
				select : function( selection , record , index , eOpts){
					var gridStore = me.up('viewport').gridPanel.getStore();
					Ext.apply(gridStore.getProxy().extraParams,{
						type : record.get('type')
					});
					gridStore.loadPage(1);
				}
			}
		});
		Ext.applyIf(config,{
			//title : '列表',
        	columnLines : true,
			rowLines : true,
			viewConfig : {
		    	enableTextSelection : true
		    },
		    columns : [
		    {
		    	text : '类型',
		    	dataIndex : 'type',
		    	flex : 0.5
		    },
		    {
		    	text : '备注',
		    	dataIndex : 'remark',
		    	flex : 0.5
		    }],
			tbar : [{
				text : '新增',
				iconCls : 'Add',
				handler : function(){
					Ext.create('App.Framework.BasicInfoManage.AddBasicInfoWind',{
						title : '新增数据字典',
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
									datas.push({type:record.get('type')});
								})
								App.request('/basicInfo/deleteBasicInfos',{
									basicInfosJson : Ext.encode(datas)
								},function(result){
									if(result.success){
										App.alert('删除成功！',function(){
											me.getStore().reload();
											me.up('viewport').gridPanel.getStore().removeAll();
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

Ext.define("App.Framework.BasicInfoManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.Framework.BasicInfoManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.Framework.BasicInfoManage.ListPanel',{
			title : '数据明细',
			region: 'center'
		});
		me.basicTypePanel = Ext.create('App.Framework.BasicInfoManage.BasicTypePanel',{
			title : '数据类型',
			region: 'west',
			width : 250,
			split : true
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel,me.basicTypePanel]
		});
		this.callParent(arguments);
	}
});

