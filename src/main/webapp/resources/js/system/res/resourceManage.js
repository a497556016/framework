Ext.define('App.System.Resource.AddResWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		Ext.applyIf(config,{
			title : '新增资源',
			modal : true,
			width : 500,
			height : 400,
			layout : 'fit',
			items : [{
				xtype : 'form',
				layout : 'form',
				items : [{
					xtype : 'hidden',
					name : 'id'
				},{
					xtype : 'textfield',
					fieldLabel : '父栏目名称',
					labelWidth : 60,
					readOnly : true,
					value : config.node.getData().resName
				},{
					xtype : 'textfield',
					fieldLabel : '名称',
					labelWidth : 60,
					name : 'resName',
					allowBlank : false
				},{
					xtype : 'textfield',
					fieldLabel : '编码',
					labelWidth : 60,
					name : 'resCode',
					allowBlank : false,
					readOnly : config.resource,
					fieldStyle : 'background:#eee;'
				},{
					xtype : 'textfield',
					fieldLabel : '链接',
					labelWidth : 60,
					name : 'resUrl',
					allowBlank : false
				},{
					xtype : 'appCombobox',
					fieldLabel : '资源类型',
					labelWidth : 60,
					name : 'type',
					data : [{name : '资源',value : 'resource'},{name : '菜单',value : 'menu'}],
					editable : false,
					value : 'resource'
				},{
					xtype : 'hidden',
					name : 'pResCode',
					value : config.node.getData().resCode
				},{
					id : config.id + '_auth_codes',
					xtype : 'hidden',
					name : 'authCodes'
				},{
					xtype : 'textTrigger',
					fieldLabel : '权限',
					labelWidth : 100,
					queryMode : 'remote',
					url : '/auth/queryAllAuthes',
					singleSelect : false,
					valueInputId : config.id + '_auth_codes',
					valueField : 'authCode',
					displayField : 'authName',
					queryItems : [{
						fieldLabel : '权限名称',
						name : 'authName'
					}],
					listColumns : [{
		        		text : '权限编码',
		        		dataIndex : 'authCode',
		        		width : 150
		        	},{
		        		text : '权限名称',
		        		dataIndex : 'authName',
		        		flex : 1
		        	}],
					allowBlank : false
				}],
				listeners : {
					afterrender : function(form){
						if(config.resource){
							form.getForm().setValues(config.resource);
						}
					}
				}
			}],
			buttons : [{
				text : '保存',
				iconCls : 'fa fa-save fa-lg',
				handler : function(){
					var update = config.resource?true:false;
					
					var data = me.down('form').getForm().getValues();
					data.leaf = true;
					data.expanded = false;
					if(!update){
						data.id = -1*(config.grid.getStore().getCount()+1);
					}
					
					App.request('/res/'+(update?'update':'add')+'Res.do',data,function(re,success){
						if(re.success){
							App.alert('保存成功！',function(){
								config.grid.getStore().reload();
								me.close();
							});
						}
					});
				}
			},'-',{
				text : '取消',
				iconCls : 'fa fa-cancel fa-lg',
				handler : function(){
					me.close();
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.System.Resource.QueryPanel",{
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
	        		name : 'resName',
	        		margin : '5 5 5 5'
	        	}]
	        }],
	        buttonAlign : 'center',
	        buttons : [{
	        	text : '查询',
	        	handler : function(){
	        		var data = me.getForm().getValues();
	        		var treeStore = me.up('viewport').gridPanel.getStore();
	        		treeStore.filter('resName',data.resName);
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

Ext.define("App.System.Resource.ListPanel",{
	extend : 'Ext.tree.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.TreeStore',{
			fields : ['text'],
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
				load : function(store , records , successful , operation , eOpts){
					console.log(records)
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
        		xtype : 'treecolumn',
        		text : '名称',
        		dataIndex : 'resName',
        		width : 300
        	},{
        		text : 'ID',
        		dataIndex : 'id',
        		hidden : true
        	},{
        		text : '资源编码',
        		dataIndex : 'resCode',
        		width : 150
        	},{
        		text : '资源链接',
        		dataIndex : 'resUrl',
        		width : 250
        	},{
        		text : '资源类型',
        		dataIndex : 'type'
        	}],
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: me.store,   // GridPanel中使用的数据
		        displayInfo: true
			}],
			listeners : {
				itemcontextmenu : function(view , record , item , index , e , eOpts){
					console.log(record)
					
					var menu = Ext.create('Ext.menu.Menu',{
						renderTo : Ext.getBody(),
						items : [{
							text : '创建子资源',
							iconCls : 'fa fa-plus',
							handler : function(){
								Ext.create('App.System.Resource.AddResWind',{
									node : record,
									grid : me
								}).show();
							}
						},'-',{
							text : '编辑资源',
							iconCls : 'fa fa-edit',
							handler : function(){
								App.request('/res/getAuthCodes',{
									resCode : record.get('resCode')
								},function(re){
									if(re.success){
										var resource = record.getData();
										resource.authCodes = re.model;
										Ext.create('App.System.Resource.AddResWind',{
											node : record.parentNode,
											grid : me,
											resource : resource,
											title : '编辑资源'
										}).show();
									}
								});
								
							}
						},'-',{
							text : '删除资源',
							iconCls : 'fa fa-remove',
							handler : function(){
								App.confirm('是否需要删除资源'+record.get('resName')+'？',function(){
									var id = record.get('id');
									if(id<0){
										me.store.remove(record);
									}else{
										var resCodes = new Array();
										resCodes.push(record.get('resCode'));
										App.request('/res/deleteResources',{
											resCodesJson : Ext.encode(resCodes)
										},function(data,success){
											if(data.success){
												App.alert('删除成功！',function(){
													me.store.reload();
												});
											}else{
												App.alert(data.message);
											}
										});
									}
								});
							}
						}]
					}).showAt(e.getXY());
					e.preventDefault();
				},
				drop : function(node , data , overModel , dropPosition , eOpts){
            		console.log(node , data , overModel , dropPosition , eOpts);
            		
            	},
            	itemexpand : function(node){
            		node.dirty = true;
            	},
            	itemcollapse : function(node){
            		node.dirty = true;
            	}
			},
			tbar : [{
				text : '全部展开',
				iconCls : 'Sectioncollapsed',
				handler : function(){
					me.expandAll();
				}
			},'-',{
				text : '全部收起',
				iconCls : 'Sectionexpanded',
				handler : function(){
					me.collapseAll();
				}
			},'-','<span style="color:red;">右键资源进行添加、编辑或删除操作！</span>']
		});
		this.callParent(arguments);
	}
});

Ext.define("App.System.Resource.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.System.Resource.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.System.Resource.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});