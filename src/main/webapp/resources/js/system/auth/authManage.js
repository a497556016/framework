

Ext.define('App.System.AuthManage.AddAuthWind',{
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
				name : 'authName',
				maxLength : 16,
				allowBlank : false
			},{
				xtype : 'textfield',
				fieldLabel : '编码',
				labelWidth : 100,
				name : 'authCode',
				maxLength : 16,
				allowBlank : false
			}],
			listeners : {
				afterrender : function(){
					if(config.auth){
						me.formPanel.getForm().setValues(config.auth);
					}
				}
			}
		});
		me.setSelect = function(records,resCodes,recs){
			Ext.each(records,function(rec){
				Ext.each(resCodes,function(resCode){
					if(resCode==rec.get('resCode')){
						recs.push(rec);
					}
				});
				if(rec.childNodes.length>0){
					me.setSelect(rec.childNodes, resCodes, recs);
				}
			});
		}
		me.resourceGrid = Ext.create('Ext.tree.Panel',{
			selModel : Ext.create('Ext.selection.CheckboxModel',{
				mode : 'SIMPLE'
			}),
			store : Ext.create('Ext.data.TreeStore',{
				proxy : {
					url : basePath + '/res/queryResListByPCode',
					timeout : 60*1000,
					type : 'ajax',
					reader : {
						type : 'json'
					}
			
				},
				root: {
				    expanded: true,
				    id : '0',
				    resCode : '',
				    resName : '根目录',
				    children: []
				},
				nodeParam : 'pResCode',
				autoLoad : true,
				listeners : {
					load : function(store,records){
						me.resourceGrid.expandAll();
						if(config.auth){
							var recs = [];
							me.setSelect(records,config.auth.resCodes,recs);
							me.resourceGrid.selModel.select(recs,true);
						}
					}
				}
			}),
			columns : [{
        		xtype : 'treecolumn',
        		text : '名称',
        		dataIndex : 'resName',
        		width : 300,
        		editor : {
        			xtype : 'textfield'
        		}
        	},{
        		text : 'ID',
        		dataIndex : 'id',
        		hidden : true
        	},{
        		text : '资源编码',
        		dataIndex : 'resCode',
        		width : 150
        	}]
		});
		me.tabPanel = Ext.create('Ext.tab.Panel',{
			items : [{
				title : '权限信息',
				layout : 'fit',
				items : [me.formPanel]
			},{
				title : '资源分配',
				layout : 'fit',
				items : [me.resourceGrid]
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
						var update = config.auth?true:false;
						var data = me.formPanel.getValues();
						var records = me.resourceGrid.selModel.getSelection();
						var resourceCodes = '';
						Ext.each(records,function(re){
							resourceCodes += re.get('resCode')+',';
						});
						data.resourceCodes = resourceCodes;
						App.request('/auth/'+(update?'update':'add')+'Auth.do',data,function(result){
							if(result.success){
								App.alert('保存权限成功！',function(){
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
			}]
		});
		this.callParent(arguments);
	}
});


Ext.define("App.System.AuthManage.QueryPanel",{
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
	        		name : 'authName',
	        		margin : '5 5 5 5'
	        	},{
	        		xtype : 'textfield',
	        		fieldLabel : '编码',
	        		labelWidth : 35,
	        		name : 'authCode',
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

Ext.define("App.System.AuthManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			fields : ['text'],
			proxy : {
				url : basePath + '/auth/queryAuthList',
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
        		text : '权限编码',
        		dataIndex : 'authCode',
        		width : 150
        	},{
        		text : '权限名称',
        		dataIndex : 'authName',
        		width : 150
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
                    tooltip: '编辑权限信息',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = grid.getStore().getAt(rowIndex);
                        App.request('/auth/getAuth',{
                        	id : rec.get('id')
                        },function(re){
                        	if(re.success){
                        		Ext.create('App.System.AuthManage.AddAuthWind',{
            						title : '编辑权限',
            						grid : me,
            						auth : re.model
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
					Ext.create('App.System.AuthManage.AddAuthWind',{
						title : '新增权限',
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
							var auths = [];
							Ext.each(records,function(record,index){
								auths.push(record.get('authCode'));
							})
							App.request('/auth/deleteAuths',{
								authCodes : Ext.encode(auths)
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

Ext.define("App.System.AuthManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.System.AuthManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.System.AuthManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

