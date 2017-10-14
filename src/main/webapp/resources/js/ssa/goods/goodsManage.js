Ext.define('App.Ssa.GoodsManage.AddGoodsWind',{
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
				xtype : 'textfield',
				fieldLabel : '名称',
				name : 'name',
				allowBlank : true
			},
			{
				xtype : 'textfield',
				fieldLabel : '类别',
				name : 'categoryId',
				allowBlank : true
			},
			{
				xtype : 'textfield',
				fieldLabel : '编码',
				name : 'code',
				allowBlank : true
			},
			{
				xtype : 'textfield',
				fieldLabel : '分组',
				name : 'group',
				allowBlank : true
			},
			{
				xtype : 'textfield',
				fieldLabel : '单价',
				name : 'price',
				allowBlank : true
			},
			{
				xtype : 'textfield',
				fieldLabel : '原价',
				name : 'lastPrice',
				allowBlank : true
			},
			{
				xtype : 'textfield',
				fieldLabel : '上架状态',
				name : 'shelvesStatus',
				allowBlank : true
			},
			{
				xtype : 'datefield',
				format : 'Y-m-d',
				editable : false,
				fieldLabel : '上架时间',
				name : 'shelvesTime',
				allowBlank : true
			},
			{
				xtype : 'textfield',
				fieldLabel : '折扣',
				name : 'discount',
				allowBlank : true
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
						
						App.request('/goods/'+(update?'update':'add')+'Goods.do',data,function(result){
							if(result.success){
								App.alert('保存商品管理成功！',function(){
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


Ext.define("App.Ssa.GoodsManage.QueryPanel",{
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
	        	defaults : {
	        		xtype : 'textfield',
	        		labelWidth : 60,
	        		margin : '5 5 5 5'
	        	},
	        	items : [
	        	{
	        		fieldLabel : '名称',
	        		name : 'name'
	        	},
	        	{
	        		fieldLabel : '编码',
	        		name : 'code'
	        	},
	        	]
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

Ext.define("App.Ssa.GoodsManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			fields : ['text'],
			proxy : {
				url : basePath + '/goods/queryGoodsList',
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
		    	text : '类别',
		    	dataIndex : 'categoryId',
		    	
		    },
		    {
		    	text : '编码',
		    	dataIndex : 'code',
		    	
		    },
		    {
		    	text : '分组',
		    	dataIndex : 'group',
		    	
		    },
		    {
		    	text : '单价',
		    	dataIndex : 'price',
		    	
		    },
		    {
		    	text : '原价',
		    	dataIndex : 'lastPrice',
		    	
		    },
		    {
		    	text : '上架状态（上架、定时上架、库存）',
		    	dataIndex : 'shelvesStatus',
		    	
		    },
		    {
		    	text : '上架时间',
		    	dataIndex : 'shelvesTime',
		    	
		    },
		    {
		    	text : '折扣',
		    	dataIndex : 'discount',
		    	
		    },
		    {
        		xtype : 'actioncolumn',
        		align : 'center',
        		text : '编辑',
        		items : [{
        			iconCls : 'Applicationedit',
                    tooltip: '编辑商品管理信息',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = grid.getStore().getAt(rowIndex);
                        App.request('/role/getRole',{
                        	id : rec.get('id')
                        },function(re){
                        	if(re.success){
                        		Ext.create('App.Ssa.GoodsManage.AddGoodsWind',{
            						title : '编辑商品管理',
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
					Ext.create('App.Ssa.GoodsManage.AddGoodsWind',{
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

Ext.define("App.Ssa.GoodsManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.Ssa.GoodsManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.Ssa.GoodsManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

