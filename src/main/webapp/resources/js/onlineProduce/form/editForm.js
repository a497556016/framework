Ext.define("App.LineProduce.EditForm.BaseInfoPanel",{
	extend : "Ext.form.Panel",
	constructor : function(config){
		var me = this;
		
		Ext.applyIf(config,{
			title : '表单基础信息',
			border : false,
			layout : 'column',
			defaults : {
				margin : '5 5 5 5',
				xtype : 'textfield',
				columnWidth : 0.3333
			},
			items : [{
				xtype : 'hidden',
				name : 'id'
			},{
				fieldLabel : '表名',
				name : 'tableName',
				readOnly : true
			},{
				fieldLabel : '表单名',
				name : 'name',
				allowBlank : false
			},{
				fieldLabel : '描述',
				name : 'description'
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.LineProduce.EditForm.FieldListPanel",{
	extend : "Ext.grid.Panel",
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			data : [],
			listeners : {
				update : function(store , record , operation , modifiedFieldNames , details , eOpts){
					if(modifiedFieldNames.indexOf('isQuery')>-1){
						var value = record.get('isQuery');
						record.set('queryType',value?'1':'');
					}
					if(modifiedFieldNames.indexOf('isFormField')>-1){
						var value = record.get('isFormField');
						record.set('fieldType',value?'textfield':'');
					}
				}
			}
		});
		Ext.applyIf(config,{
			title : '表单字段配置',
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
		    columns : [{
		    	xtype : 'rownumberer'
		    },{
		    	text : 'ID',
		    	dataIndex : 'id',
		    	hidden : true
		    },{
		    	text : '是否主键',
		    	dataIndex : 'isPri',
		    	hidden : true
		    },{
		    	text : '字段名',
		    	dataIndex : 'name'
		    },{
		    	text : '字段描述',
		    	dataIndex : 'comments',
		    	editor : {
		    		xtype : 'textfield'
		    	}
		    },{
		    	xtype : 'checkcolumn',
		    	text : '是否列表显示',
		    	dataIndex : 'isGridCol',
		    	editor : {
		    		xtype : 'checkbox'
		    	}
		    },{
		    	xtype : 'checkcolumn',
		    	text : '是否查询项',
		    	dataIndex : 'isQuery',
		    	editor : {
		    		xtype : 'checkbox'
		    	}
		    },{
		    	text : '查询类型',
		    	dataIndex : 'queryType',
		    	editor : {
		    		xtype : 'appCombobox',
		    		data : [{name:'--请选择--',value:''},{name:'普通查询',value:'1'},{name:'模糊查询',value:'2'},{name:'范围查询',value:'3'}],
		    		value : '',
		    		editable : false
		    	},
		    	renderer : App.gridComboColumnRender
		    },{
		    	xtype : 'checkcolumn',
		    	text : '是否表单项',
		    	dataIndex : 'isFormField',
		    	editor : {
		    		xtype : 'checkbox'
		    	}
		    },{
		    	text : '输入类型',
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
		    		value : '',
		    		editable : false
		    	},
		    	renderer : App.gridComboColumnRender
		    },{
		    	text : '基本数据类型',
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
		    	text : '扩张参数',
		    	dataIndex : 'expandParam',
		    	editor : {
		    		xtype : 'textarea',
		    		grow : true
		    	},
		    	flex : 1
		    }],
		    listeners : {
		    	afterrender : function(){
		    		var view = this.getView();
		    	}
		    }
		});
		this.callParent(arguments);
	}
});

Ext.define("App.LineProduce.EditForm.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.formPanel = Ext.create('App.LineProduce.EditForm.BaseInfoPanel',{
			columnWidth : 1
		});
		me.gridPanel = Ext.create('App.LineProduce.EditForm.FieldListPanel',{
			columnWidth : 1,
			scrollable : true
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [{
		    	region: 'center',
		    	layout : 'column',
		    	scrollable : true,
		    	items : [me.formPanel,me.gridPanel],
			    buttonAlign : 'center',
			    buttons : [{
			    	text : '保存',
			    	iconCls : 'fa fa-save',
			    	handler : function(){
			    		App.confirm('是否需要保存?',function(y){
			    			if(y){
			    				var form = me.formPanel.getForm();
			    				if(form.isValid()){
				    				var data = form.getValues();
				    				var fieldData = App.storeToArray(me.gridPanel.getStore());
				    				for(var i in fieldData){
				    					if(!Ext.isNumber(fieldData[i].id)){
				    						fieldData[i].id = null;
				    					}
				    				}
				    				data.fieldsJson = Ext.encode(fieldData);
				    				App.request('/form/saveForm',data,function(re){
				    					if(re.success){
				    						App.alert('保存成功！');
				    						
				    					}
				    				});
			    				}
			    			}
			    		})
			    	}
			    }]
		    }],
		    listeners : {
		    	afterrender : function(){
		    		me.loadFormData();
		    	}
		    }
		});
		this.callParent(arguments);
	},
	loadFormData : function(){
		var me = this;
		var formData;
		if(me.type=='add'){
			formData = {
				tableName : tableName
			}
		}else if(me.type=='update'){
			formData = form;
		}
		me.formPanel.getForm().setValues(formData);
		me.gridPanel.getStore().loadData(fields);
	}
});