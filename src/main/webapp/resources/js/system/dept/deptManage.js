Ext.define("App.System.Department.QueryPanel",{
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
	        		var treeStore = me.up('viewport').gridPanel.getStore();
	        		treeStore.filter('text',data.text);
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

Ext.define('App.System.Department.AddDepartmentWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		Ext.applyIf(config,{
			title : '新增部门',
			modal : true,
			width : 300,
			height : 310,
			layout : 'fit',
			items : [{
				xtype : 'form',
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '父部门名称',
					labelWidth : 60,
					readOnly : true,
					value : config.node.getData().name
				},{
					xtype : 'textfield',
					fieldLabel : '名称',
					labelWidth : 60,
					name : 'name'
				},{
					xtype : 'textfield',
					fieldLabel : '编码',
					labelWidth : 60,
					name : 'code'
				},{
					xtype : 'textfield',
					fieldLabel : '描述',
					labelWidth : 60,
					name : 'description'
				},{
					xtype : 'hidden',
					name : 'pCode',
					value : config.node.getData().code
				}],
				listeners : {
					afterrender : function(form){
						
					}
				}
			}],
			buttons : [{
				text : '保存',
				iconCls : 'fa fa-save fa-lg',
				handler : function(){
					var data = me.down('form').getForm().getValues();
					data.leaf = true;
					data.expanded = false;
					data.id = -1*(config.grid.getStore().getCount()+1);
					
					data.sort = (config.node.lastChild?config.node.lastChild.get('sort'):0)+1;
					
					config.node.set('leaf',false);
					config.node.set('expanded',true);
					config.node.appendChild(data);
					me.close();
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

Ext.define("App.System.Department.ListPanel",{
	extend : 'Ext.tree.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.TreeStore',{
			fields : ['text'],
			proxy : {
				url : basePath + '/dept/queryDeptListByPCode',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json'
				}
			},
			root: {
			    expanded: true,
			    text: "根目录",
			    id : '0',
			    code : '',
			    children: []
			},
			
			nodeParam : 'pCode',
			autoLoad : true,
			filterer : 'bottomup',
			listeners : {
				load : function(store , records , successful , operation , eOpts){
					console.log(records)
					me.expandAll();
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SINGLE'
		});
		
		Ext.applyIf(config,{
			//title : '列表',
			rootVisible : false,
        	columnLines : true,
			rowLines : true,
			viewConfig : {
		    	enableTextSelection : true,
		    	/*plugins: {
		            ptype: 'treeviewdragdrop',
		            dragText: '拖动排序'
		        }*/
		    },
		    plugins: {
		        ptype: 'cellediting',
		        clicksToEdit: 1,
		        listeners : {
		        	edit : function(editor, e){
		        		console.log(editor, e)
		        		e.record.dirty = true;
		        	}
		        }
		    },
		    columns : [{
        		xtype : 'treecolumn',
        		text : '名称',
        		dataIndex : 'name',
        		width : 300,
        		editor : {
        			xtype : 'textfield'
        		}
        	},{
        		text : 'ID',
        		dataIndex : 'id',
        		hidden : true
        	},{
        		text : '编码',
        		dataIndex : 'code',
        		width : 150,
        		editor : {
        			xtype : 'textfield'
        		}
        	},{
        		text : '描述',
        		dataIndex : 'description',
        		flex : 1,
        		editor : {
        			xtype : 'textfield'
        		}
        	},{
        		text : '排序',
        		dataIndex : 'sort',
        		hidden : true
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
							text : '创建子部门',
							iconCls : 'fa fa-edit',
							handler : function(){
								Ext.create('App.System.Department.AddDepartmentWind',{
									node : record,
									grid : me
								}).show();
							}
						},{
							text : '上移',
							iconCls : 'fa fa-level-up',
							handler : function(btn){
								if(record.getData().isFirst){
									Ext.Msg.alert('提示','不能上移！');
									return;
								}
								var sort = record.get('sort');
								record.set('sort',record.previousSibling.get('sort'));
								record.previousSibling.set('sort',sort);
								me.store.sort('sort', 'ASC');
							}
						},{
							text : '下移',
							iconCls : 'fa fa-level-down',
							handler : function(){
								if(record.getData().isLast){
									Ext.Msg.alert('提示','不能下移！');
									return;
								}
								var sort = record.get('sort');
								record.set('sort',record.nextSibling.get('sort'));
								record.nextSibling.set('sort',sort);
								me.store.sort('sort', 'ASC');
							}
						},{
							text : '删除部门',
							iconCls : 'fa fa-remove',
							handler : function(){
								App.confirm('是否需要删除菜单'+record.get('text')+'？',function(){
									var id = record.get('id');
									if(id<0){
										me.store.remove(record);
									}else{
										var ids = new Array();
										ids.push(id);
										App.request('/menu/deleteDepartments',{
											idsJson : Ext.encode(ids)
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
            		
            	}
			},
			tbar : [{
				text : '保存',
				iconCls : 'fa fa-save fa-lg',
				handler : function(){
					var records = me.store.getModifiedRecords();
					console.log(records);
					if(records.length==0){
						App.toast('没有需要修改的数据');
						return;
					}
					var datas = new Array();
					Ext.each(records,function(record,index){
						datas.push(record.getData());
					});
					App.request('/dept/saveDepartment',{
						deptJson : Ext.util.JSON.encode(datas)
					},function(data,success){
						if(data.success){
							App.alert('保存成功！',function(){
								me.store.reload();
							});
						}else{
							App.alert(data.message);
						}
					});
				}
			},{
				text : '全部展开',
				handler : function(){
					me.expandAll();
				}
			},{
				text : '全部收起',
				handler : function(){
					me.collapseAll();
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.System.Department.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.System.Department.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.System.Department.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

