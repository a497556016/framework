Ext.define('App.Framework.WfFormManage.AddWfFormWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.formPanel = Ext.create('Ext.form.Panel',{
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				labelWidth : 100,
				columnWidth : 0.5,
				margin : '5 5 5 5'
			},
			items : [
				{
					xtype : 'hidden',
					name : 'id'
				},
				{
					xtype : 'textfield',
					fieldLabel : '表单名称',
					name : 'formName',
					allowBlank : true,
				},
				{
					id : 'process_key',
					xtype : 'appCombobox',
					fieldLabel : '关联流程',
					name : 'processKey',
					url : '/actProcess/getProcessKeys'
				}
			]
		});
		me.fieldPanel = Ext.create('Ext.grid.Panel',{
			title : '编辑表单字段',
			columnLines : true,
			rowLines : true,
			scrollable : true,
			viewConfig : {
		    	enableTextSelection : true,
		    	getRowClass: function(record, rowIndex, rowParams, store){
		            return record.get("isPri") ? "row-orange" : "";
		        }
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
			store : Ext.create('Ext.data.Store',{
				
			}),
			columns : [{
				text : 'ID',
				dataIndex : 'id',
				hidden : true
			},{
				text : '列名称',
				dataIndex : 'columnName',
				editor : {
					xtype : 'textfield'
				},
				width : 150
			},{
				text : '列编码',
				dataIndex : 'columnCode',
				editor : {
					xtype : 'textfield'
				},
				width : 150
			},{
				text : '数据类型',
				dataIndex : 'dataType',
		    	editor : {
		    		xtype : 'appCombobox',
		    		data : [{name:'--请选择--',value:''},
		    		        {name:'字符串',value:'varchar2'},
		    		        {name:'整型',value:'int'},
		    		        {name:'浮点型',value:'decimal'},
		    		        {name:'日期',value:'datetime'},
		    		        {name:'长文本',value:'text'}],
		    		editable : false
		    	},
		    	renderer : App.gridComboColumnRender
			},{
				text : '字段类型',
				dataIndex : 'fieldType',
		    	editor : {
		    		xtype : 'appCombobox',
		    		data : [{name:'--请选择--',value:''},
		    		        {name:'文本框',value:'textfield'},
		    		        {name:'数字框',value:'numberfield'},
		    		        {name:'文本域',value:'textarea'},
		    		        {name:'下拉框',value:'appCombobox'},
		    		        {name:'日期选择框',value:'datefield'},
		    		        {name:'单选框',value:'radio'},
		    		        {name:'复选框',value:'checkbox'}],
		    		editable : false
		    	},
		    	renderer : App.gridComboColumnRender
			},{
				text : '字段宽度',
				dataIndex : 'fieldWidth',
				editor : {
					xtype : 'numberfield'
				}
			},{
				text : '基础数据类型',
				dataIndex : 'basicType',
		    	editor : {
		    		xtype : 'appCombobox',
		    		url : '/basicInfo/getBasicTypes',
		    		displayField : 'remark',
		    		valueField : 'type',
		    		value : '',
		    		editable : false
		    	},
		    	renderer : App.gridComboColumnRender
			},{
				text : '可编辑任务',
				dataIndex : 'editTasks',
				flex : 1,
				editor : {
					xtype : 'appCombobox',
					url : '/actProcess/getProcessTasks',
					multiSelect : true,
					beforeload : function(store){
						Ext.apply(store.getProxy().extraParams,{
							processKey : Ext.getCmp('process_key').getValue()
						});
					}
				},
				renderer : App.gridComboColumnRender
			}],
			tbar : [{
				text : '新增',
				iconCls : 'fa fa-plus',
				handler : function(){
					var processKey = Ext.getCmp('process_key').getValue();
					if(!processKey){
						App.alert('请选择关联流程！');
						return;
					}
					var store = me.fieldPanel.getStore();
					store.insert(store.getCount(),{
						dataType : 'varchar2',
						fieldType : 'textfield'
					})
				}
			}]
		});
		
		Ext.applyIf(config,{
			width : 550,
			layout : 'fit',
			modal : true,
			items : [{
				xtype : 'form',
				layout : 'column',
				defaults : {
					columnWidth : 1
				},
				items : [me.formPanel,me.fieldPanel]
			}],
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls : 'Accept',
				handler : function(){
					var valid = me.formPanel.isValid();
					if(valid){
						var update = config.role?true:false;
						var data = me.formPanel.getValues();
						var fields = App.storeToArray(me.fieldPanel.getStore(),['id']);
						data.fieldsJson = Ext.JSON.encode(fields);
						
						App.request('/wfForm/'+(update?'update':'add')+'WfForm.do',data,function(result){
							if(result.success){
								App.alert('保存流程表单成功！',function(){
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
				text : '返回',
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


Ext.define("App.Framework.WfFormManage.QueryPanel",{
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
		        		fieldLabel : '主键ID',
		        		name : 'id',
					},
		        	{
		        		xtype : 'textfield',
		        		fieldLabel : '表单名称',
		        		name : 'formName',
					},
		        	{
		        		xtype : 'appCombobox',
		        		fieldLabel : '关联流程',
		        		name : 'processKey',
						url : '/actProcess/getProcessKeys',
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

Ext.define("App.Framework.WfFormManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/wfForm/queryWfFormList.do',
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
		    	text : '主键ID',
		    	dataIndex : 'id',
		    	hidden : true,
		    },
		    {
		    	text : '表单名称',
		    	dataIndex : 'formName',
		    },
		    {
		    	text : '关联流程',
		    	dataIndex : 'processKey',
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
		    },
		    {
        		xtype : 'actioncolumn',
        		align : 'center',
        		text : '编辑',
        		items : [{
        			iconCls : 'Applicationedit',
                    tooltip: '编辑流程表单信息',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = grid.getStore().getAt(rowIndex);
                        App.request('/wfForm/getWfForm.do',{
                        	id : rec.get('id')
                        },function(re){
                        	if(re.success){
                        		Ext.create('App.Framework.WfFormManage.AddWfFormWind',{
            						title : '编辑流程表单',
            						maximized : true,
            						grid : me,
            						role : re.model
            					}).show();
                        	}else{
                        		App.alert(re.message);
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
					Ext.create('App.Framework.WfFormManage.AddWfFormWind',{
						title : '新增流程表单',
						maximized : true,
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
								App.request('/wfForm/deleteWfForms.do',{
									wfFormsJson : Ext.encode(datas)
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

Ext.define("App.Framework.WfFormManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.Framework.WfFormManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.Framework.WfFormManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

