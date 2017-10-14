Ext.define("App.LineProduce.FormProduce.ProductCodeWind",{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.tableName = config.formData.tableName;
		me.formId = config.formData.id;
		
		me.formPanel = Ext.create('Ext.form.Panel',{
			layout : 'form',
			defaults : {
				xtype : 'textfield'
			},
			items : [{
				xtype : 'hidden',
				name : 'formId',
				value : me.formId
			},{
				fieldLabel : '表名',
				value : me.tableName,
				readOnly : true,
				fieldStyle : 'background:#eee'
			},{
				fieldLabel : '类名',
				name : 'className',
				value : App.CamelCase.hyphenToCamel(me.tableName)
			},{
				fieldLabel : '包名',
				name : 'packageName'
			},{
	            xtype: 'fieldcontainer',
	            fieldLabel: '编码文件类型',
	            layout: 'hbox',
	            defaultType: 'checkboxfield',
	            defaults : {
	            	margin : '0 0 0 10'
	            },
	            items: [
	                {
	                    boxLabel  : 'java',
	                    name      : 'codeFileType',
	                    inputValue: 'java',
	                    checked   : true
	                },{
	                    boxLabel  : 'mapperXML',
	                    name      : 'codeFileType',
	                    inputValue: 'mapperXml',
	                    checked   : true
	                }, {
	                    boxLabel  : 'jsp',
	                    name      : 'codeFileType',
	                    inputValue: 'jsp',
	                    checked   : true
	                }, {
	                    boxLabel  : 'js',
	                    name      : 'codeFileType',
	                    inputValue: 'js',
	                    checked   : true
	                }
	            ]
	        },{
	        	xtype : 'selectLocalFileField',
	        	fieldLabel : 'java文件路径',
	        	name : 'javaFilePath',
	        	windTitle : '选择代码生成路径',
	        	rootPath : projectWorkPath
	        },{
	        	xtype : 'selectLocalFileField',
	        	fieldLabel : 'mapperXML文件路径',
	        	name : 'mapperXmlFilePath',
	        	windTitle : '选择代码生成路径',
	        	rootPath : projectWorkPath
	        },{
	        	xtype : 'selectLocalFileField',
	        	fieldLabel : 'js文件路径',
	        	name : 'jsFilePath',
	        	windTitle : '选择代码生成路径',
	        	rootPath : projectWorkPath
	        },{
	        	xtype : 'selectLocalFileField',
	        	fieldLabel : 'jsp文件路径',
	        	name : 'jspFilePath',
	        	windTitle : '选择代码生成路径',
	        	rootPath : projectWorkPath
	        }]
		});
		Ext.applyIf(config,{
			title : '代码生成',
			width : 500,
			modal : true,
			layout : 'fit',
			items : [me.formPanel],
			buttons : [{
				text : '确认',
				iconCls : 'fa fa-save',
				handler : function(){
					me.formPanel.getForm().submit({
						clientValidation : true,
						url : basePath + '/form/produceCodes',
						success : function(form, action){
							if(action.result.success){
								App.alert('代码生成成功！');
							}else{
								App.alert(action.result.message);
							}
						}
					});
				}
			},'-',{
				text : '取消',
				iconCls : 'fa ',
				handler : function(){
					me.close();
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.LineProduce.FormProduce.QueryPanel",{
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
	        		margin : '5 5 5 5',
	        		labelWidth : 60
	        	},
	        	items : [{
	        		fieldLabel : '表名',
	        		name : 'tableName'
	        	},{
	        		fieldLabel : '表单名称',
	        		name : 'name'
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

Ext.define("App.LineProduce.FormProduce.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/form/queryFormList',
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}
		
			},
			autoLoad : true,
			remoteSort : true,
			pageSize : 15,
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
		    	xtype : 'rownumberer'
		    },{
        		text : 'ID',
        		dataIndex : 'id',
        		hidden : true
        	},{
        		text : '表单名',
        		dataIndex : 'name',
        		width : 150
        	},{
        		text : '表名',
        		dataIndex : 'tableName',
        		width : 150
        	},{
        		text : '版本',
        		dataIndex : 'version',
        		width : 150
        	},{
        		text : '描述',
        		dataIndex : 'tableDesc',
        		flex : 1
        	},{
        		xtype : 'actioncolumn',
        		text : '编辑',
        		align : 'center',
        		items : [{
        			iconCls : 'Applicationedit',
        			handler : function(grid, rowIndex, colIndex){
        				var record = grid.getStore().getAt(rowIndex);
        				parent.addTabPanel('编辑表单',basePath + '/form/toEditForm?formId='+record.get('id'));
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
				text : '代码生成',
				iconCls : 'fa fa-plus',
				handler : function(){
					var record = me.selModel.getLastSelected();
					if(!record){
						App.toast('请选择表单');
					}else{
						Ext.create('App.LineProduce.FormProduce.ProductCodeWind',{
							formData : record.getData()
						}).show();
					}
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.LineProduce.FormProduce.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.LineProduce.FormProduce.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.LineProduce.FormProduce.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});