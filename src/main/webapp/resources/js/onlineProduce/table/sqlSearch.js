Ext.define("App.LineProduce.SqlSearch.QueryPanel",{
	extend : 'Ext.form.Panel',
	constructor : function(config){
		var me = this;
		Ext.applyIf(config,{
			//title : '查询条件',
        	//collapsible : true,
	        items : [{
	        	layout : 'fit',
	        	items : [{
	        		xtype : 'textarea',
	        		name : 'sql',
	        		height : 100,
	        		margin : '1 1 1 1'
	        	}]
	        }],
	        buttonAlign : 'center',
	        buttons : [{
	        	text : '查询',
	        	handler : function(){
	        		var data = me.getForm().getValues();
	        		me.up('viewport').gridPanel.excuteSql(data.sql);
	        	}
	        },{
	        	text : '更新',
	        	handler : function(){
	        		var data = me.getForm().getValues();
	        		App.request('/table/sqlUpdate',data,function(result){
	        			App.alert(result.message);
	        		});
	        	}
	        }]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.LineProduce.SqlSearch.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/table/sqlSearch',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
		
			},
			autoLoad : false,
			remoteSort : true,
			pageSize : 20,
			listeners : {
				load : function(store , records , successful , operation , eOpts){
					console.log(records)
				}
			}
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SINGLE'
		});
		
		Ext.applyIf(config,{
			//title : '列表',
			hidden : true,
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
		    columns : [],
			bbar : [{
				xtype: 'pagingtoolbar',
		        store: me.store,   // GridPanel中使用的数据
		        displayInfo: true
			}],
			listeners : {
				
			},
			tbar : []
		});
		this.callParent(arguments);
	},
	excuteSql : function(sql){
		var me = this;
		App.request('/table/sqlSearch',{
			sql : sql,
			limit : 1,
			offset : 0
		},function(result){
			console.log(result);
			var rows = result.rows;
			if(rows.length>0){
				var h = rows[0];
				var columns = [{xtype : 'rownumberer'}];
				for(var i in h){
					columns.push({
						text : i,
						dataIndex : i
					});
				}
				me.setColumns(columns);
				Ext.apply(me.store.proxy.extraParams,{
					sql : sql
				})
				me.store.loadPage(1);
				me.show();
			}
		});
	}
});

Ext.define("App.LineProduce.SqlSearch.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.LineProduce.SqlSearch.QueryPanel',{
			region: 'north',
			split : true
		});
		me.gridPanel = Ext.create('App.LineProduce.SqlSearch.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});