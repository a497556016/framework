Ext.define('App.Demo.Grid.AddPanel',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.formPanel = Ext.create('Ext.form.Panel',{
			layout : {
				type : 'form'
			},
			items : [{
				xtype : 'textfield',
				fieldLabel : '姓名',
				name : 'username'
			},{
				xtype : 'textareafield',
				fieldLabel : '描述',
				name : 'remarks'
			},{
				xtype : 'checkbox',
				fieldLabel : '有效',
				name : 'active'
			}]
		});
		Ext.applyIf(config,{
			width : 400,
			height : 300,
			modal : true,
			items : [me.formPanel],
			layout : 'fit',
			buttonAlign : 'right',
			buttons : [{
				text : '保存',
				iconCls : 'fa fa-save',
				handler : function(){
					var values = me.formPanel.getForm().getValues();
					if(config.title=='修改'){
						config.rowRecord.set('username',values.username);
						config.rowRecord.set('remarks',values.remarks);
						config.rowRecord.set('active',values.active);
						
						Ext.toast('修改成功！');
						me.close();
					}else{
						config.gridStore.insert(0,values);
						
						Ext.toast('新增成功！');
						me.close();
					}
				}
			},'-',{
				text : '取消',
				iconCls : 'fa fa-times',
				handler : function(){
					me.close();
				}
			}],
			listeners : {
				afterRender : function(){
					if(config.rowRecord){
						me.formPanel.getForm().setValues(config.rowRecord.getData());
					}
					
				}
			}
		});
		this.callParent(arguments);
	}
});

Ext.define('App.Demo.Grid.ListPanel',{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			fields : [],
			proxy : {
				url : 'gridData.json',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
		
			},
			autoLoad : true,
    		pageSize : 5
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SINGLE'
		});
		me.editorPlugins = Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit: 2,
			listeners : {
				
			}
		});
		Ext.applyIf(config,{
			title : '列表',
        	columnLines : true,
			rowLines : true,
			viewConfig : {
		    	enableTextSelection : true
		    },
		    plugins : [me.editorPlugins],
        	columns : [{
        		xtype : 'rownumberer',
        		width : 35
        	},{
        		text : '姓名',
        		dataIndex : 'username',
        		editor : {
        			xtype : 'textfield'
        		}
        	},{
        		text : '描述',
        		dataIndex : 'remarks',
        		flex : 1,
        		editor : {
        			xtype : 'textareafield'
        		}
        	},{ 
        		xtype: 'checkcolumn', 
        		text: '有效', 
        		dataIndex: 'active' 
        	}],
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: me.store,   // GridPanel中使用的数据
		        displayInfo: true
			}],
			tbar : [{
				text : '新增',
				iconCls : 'Add',
				handler : function(){
					Ext.create('App.Demo.Grid.AddPanel',{
						title : '新增',
						gridStore : me.store
					}).show();
				}
			},'-',{
				text : '修改',
				iconCls : 'Tableedit',
				handler : function(){
					var rowRecords = me.selModel.getSelection();
					if(rowRecords.length==0){
						Ext.Msg.alert('提示','请选择一条数据！');
					}else{
						Ext.create('App.Demo.Grid.AddPanel',{
							title : '修改',
							rowRecord : rowRecords[0],
							gridStore : me.store
						}).show();
					}
					
				}
			},'-',{
				text : '删除',
				iconCls : 'Delete',
				handler : function(){
					var rowRecords = me.selModel.getSelection();
					if(rowRecords.length==0){
						Ext.Msg.alert('提示','请选择一条数据！');
					}else{
						Ext.Msg.confirm('提示','是否删除？',function(y){
							console.log(y)
							if(y=='yes'){
								me.store.remove(rowRecords[0]);
							}
						})
						
					}
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.Demo.Grid.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.gridPanel = Ext.create('App.Demo.Grid.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [{
		        region: 'north',
		        title : '查询条件',
	        	collapsible : true,
		        items : [{
		        	id : 'query-form',
		        	xtype : 'form',
		        	layout : {
		        		type : 'table',
		        		columns : 5
		        	},
		        	items : [{
		        		xtype : 'textfield',
		        		fieldLabel : '姓名',
		        		labelWidth : 35,
		        		name : 'username',
		        		margin : '5 5 5 5'
		        	},{
		        		xtype : 'datefield',
		        		format : 'Y-m-d',
		        		editable : false,
		        		fieldLabel : '创建时间',
		        		labelWidth : 70,
		        		name : 'createTime',
		        		margin : '5 5 5 5'
		        	},{
		        		xtype : 'textfield',
		        		fieldLabel : '描述',
		        		labelWidth : 35,
		        		name : 'remarks',
		        		margin : '5 5 5 5'
		        	}]
		        }],
		        buttonAlign : 'center',
		        buttons : [{
		        	text : '查询',
		        	handler : function(btn){
		        		var data = Ext.getCmp('query-form').getForm().getValues();
		        		console.log(data);
		        		console.log(Ext.encode(data));
		        	}
		        },{
		        	text : '重置'
		        }]
		    },me.gridPanel]
		});
		this.callParent(arguments);
	}
});

Ext.onReady(function(){
	var viewport = Ext.create("App.Demo.Grid.Viewport",{
		
	});
});