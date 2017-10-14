

Ext.define('App.System.RoleManage.AddRoleWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.formPanel = Ext.create('Ext.form.Panel',{
			layout : 'form',
			items : [{
				xtype : 'hidden',
				name : 'id'
			},{
				xtype : 'textfield',
				fieldLabel : '名称',
				labelWidth : 100,
				name : 'roleName',
				maxLength : 16,
				allowBlank : false
			},{
				xtype : 'textfield',
				fieldLabel : '编码',
				labelWidth : 100,
				name : 'roleCode',
				maxLength : 16,
				allowBlank : false,
				readOnly : config.role,
				fieldStyle : 'background:#eee;'
			},{
				xtype : 'textareafield',
				fieldLabel : '描述',
				labelWidth : 100,
				name : 'remark',
				maxLength : 16,
				allowBlank : true
			}]
		});
		me.authGrid = Ext.create('Ext.grid.Panel',{
			selModel : Ext.create('Ext.selection.CheckboxModel',{
				mode : 'MULTI'
			}),
			store : Ext.create('Ext.data.Store',{
				fields : ['text'],
				proxy : {
					url : basePath + '/auth/queryAllAuthes',
					timeout : 60*1000,
					type : 'ajax',
					reader : {
						type : 'json',
						rootProperty : 'rows'
					}
			
				},
				autoLoad : true,
				listeners : {
					load : function(store , records , successful , operation , eOpts){
						if(config.role){
							var selRecs = [];
							store.each(function(record,index){
								Ext.each(config.role.authCodes,function(authCode){
									if(record.get('authCode')==authCode){
										selRecs.push(record);
									}
								})
							});
							me.authGrid.selModel.select(selRecs);
						}
					}
				}
			}),
			columns : [{
        		text : 'ID',
        		dataIndex : 'id',
        		hidden : true
        	},{
        		text : '权限编码',
        		dataIndex : 'authCode',
        		width : 150
        	},{
        		text : '权限名称',
        		dataIndex : 'authName',
        		width : 150
        	}]
		});
		me.tabPanel = Ext.create('Ext.tab.Panel',{
			items : [{
				title : '角色信息',
				layout : 'fit',
				items : [me.formPanel]
			},{
				title : '权限分配',
				layout : 'fit',
				items : [me.authGrid]
			}]
		});
		Ext.applyIf(config,{
			height : 450,
			width : 550,
			layout : 'fit',
			modal : true,
			items : [me.tabPanel],
			buttons : [{
				text : '保存',
				iconCls : 'Accept',
				handler : function(){
					var valid = me.formPanel.isValid();
					if(valid){
						var update = config.role?true:false;
						var data = me.formPanel.getValues();
						var records = me.authGrid.selModel.getSelection();
						var authCodes = [];
						Ext.each(records,function(re){
							authCodes.push(re.get('authCode'));
						});
						data.authCodes = Ext.encode(authCodes);
						App.request('/role/'+(update?'update':'add')+'Role.do',data,function(result){
							if(result.success){
								App.alert('保存角色成功！',function(){
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


Ext.define("App.System.RoleManage.QueryPanel",{
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
	        		name : 'roleName',
	        		margin : '5 5 5 5'
	        	},{
	        		xtype : 'textfield',
	        		fieldLabel : '编码',
	        		labelWidth : 35,
	        		name : 'roleCode',
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

Ext.define("App.System.RoleManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			fields : ['text'],
			proxy : {
				url : basePath + '/role/queryRoleList',
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
					if(modifiedFieldNames.join(',').indexOf('locked')>-1){
						App.request('/user/lockUser',{
							id : record.get('id'),
							locked : record.get('locked')
						},function(result,success){
							if(result.success){
								
							}else{
								App.toast(result.message);
								store.reload();
							}
						});
					}
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
		    columns : [{
        		text : 'ID',
        		dataIndex : 'id',
        		hidden : true
        	},{
        		text : '角色编码',
        		dataIndex : 'roleCode',
        		width : 150
        	},{
        		text : '角色名称',
        		dataIndex : 'roleName',
        		width : 150
        	},{
        		text : '角色描述',
        		dataIndex : 'remark',
        		flex : 1
        	},{
        		text : '创建时间',
        		dataIndex : 'createDate',
        		width : 150
        	},{
        		text : '创建人',
        		dataIndex : 'createBy'
        	},{
        		xtype : 'actioncolumn',
        		align : 'center',
        		text : '编辑',
        		items : [{
        			iconCls : 'Applicationedit',
                    tooltip: '编辑角色信息',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = grid.getStore().getAt(rowIndex);
                        App.request('/role/getRole',{
                        	id : rec.get('id')
                        },function(re){
                        	if(re.success){
                        		Ext.create('App.System.RoleManage.AddRoleWind',{
            						title : '编辑角色',
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
					Ext.create('App.System.RoleManage.AddRoleWind',{
						title : '新增角色',
						grid : me
					}).show();
				}
			},{
				text : '删除',
				iconCls : 'Delete',
				handler : function(){
					
					var records = me.selModel.getSelection();
					if(records.length>0){
						App.confirm('是否确认删除？',function(){
							var roleIds = [];
							Ext.each(records,function(record,index){
								roleIds.push(record.get('id'));
							})
							App.request('/role/deleteRoles',{
								roleIds : Ext.encode(roleIds)
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
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.System.RoleManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.System.RoleManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.System.RoleManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

