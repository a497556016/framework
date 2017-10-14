Ext.define('App.${packageName?cap_first}.${className}Manage.Add${className}Wind',{
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
					xtype : 'hidden',
					name : '${priField.name}'
				},
			<#list fieldList as field>
			<#if field.formField>
				{
					xtype : '${field.fieldType}',
					<#if field.fieldType == "datefield">
					format : 'Y-m-d',
					editable : false,
					</#if>
					fieldLabel : '${field.comments}',
					name : '${field.name}',
					allowBlank : true,
					<#if field.basicType??>
					basicType : '${field.basicType}',
					</#if>
				<#if field.fieldType=='checkbox'>
					inputValue : '1'
				},
				<#else>
				},
				</#if>
			</#if>
			</#list>
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
						
						App.request('/${className?uncap_first}/'+(update?'update':'add')+'${className}.do',data,function(result){
							if(result.success){
								App.alert('保存${form.name}成功！',function(){
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


Ext.define("App.${packageName?cap_first}.${className}Manage.QueryPanel",{
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
	        	<#list fieldList as field>
		        	<#if field.query>
		        	<#if field.queryType=='3'>
		        	{
		        		xtype : '${field.fieldType}',
		        		<#if field.fieldType == "datefield">
						format : 'Y-m-d',
						editable : false,
						</#if>
		        		fieldLabel : '${field.comments}',
		        		name : '${field.name}Begin',
		        		<#if field.basicType??>
						basicType : '${field.basicType}',
						</#if>
		        	},
		        	{
		        		xtype : '${field.fieldType}',
		        		<#if field.fieldType == "datefield">
						format : 'Y-m-d',
						editable : false,
						</#if>
		        		fieldLabel : '到',
		        		name : '${field.name}End',
		        		<#if field.basicType??>
						basicType : '${field.basicType}',
						</#if>
		        	},
		        	<#else>
		        	{
		        		xtype : '${field.fieldType}',
		        		<#if field.fieldType == "datefield">
						format : 'Y-m-d',
						editable : false,
						</#if>
		        		fieldLabel : '${field.comments}',
		        		name : '${field.name}',
		        		<#if field.basicType??>
						basicType : '${field.basicType}',
						</#if>
		        	<#if field.fieldType=='checkbox'>
						inputValue : '1'
					},
					<#else>
					},
					</#if>
		        	</#if>
		        	</#if>
	        	</#list>
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

Ext.define("App.${packageName?cap_first}.${className}Manage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			proxy : {
				url : basePath + '/${className?uncap_first}/query${className}List.do',
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
		    <#list fieldList as field>
		    <#if field.gridCol>
		    {
		    	text : '${field.comments}',
		    	dataIndex : '${field.name}',
		    <#if field.pri>
		    	hidden : true,
		    },
		    <#else>
		    },
		    </#if>
		    </#if>
		    </#list>
		    {
        		xtype : 'actioncolumn',
        		align : 'center',
        		text : '编辑',
        		items : [{
        			iconCls : 'Applicationedit',
                    tooltip: '编辑${form.name}信息',
                    handler: function(grid, rowIndex, colIndex) {
                    	var rec = grid.getStore().getAt(rowIndex);
                        App.request('/${className?uncap_first}/get${className}.do',{
                        	id : rec.get('id')
                        },function(re){
                        	if(re.success){
                        		Ext.create('App.${packageName?cap_first}.${className}Manage.Add${className}Wind',{
            						title : '编辑${form.name}',
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
					Ext.create('App.${packageName?cap_first}.${className}Manage.Add${className}Wind',{
						title : '新增${form.name}',
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
									datas.push({${priField.name}:record.get('${priField.name}')});
								})
								App.request('/${className?uncap_first}/delete${className}s.do',{
									${className?uncap_first}sJson : Ext.encode(datas)
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

Ext.define("App.${packageName?cap_first}.${className}Manage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.${packageName?cap_first}.${className}Manage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.${packageName?cap_first}.${className}Manage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

