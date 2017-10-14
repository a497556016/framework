// custom Vtype for vtype:''
Ext.define('Override.form.field.VTypes', {
    override: 'Ext.form.field.VTypes',

    // vtype validation function
    mobilePhone: function(value) {
        return this.mobilePhoneRe.test(value);
    },
    // RegExp for the value to be tested against within the validation function
    mobilePhoneRe: /^([0-9]{11})$/i,
    // vtype Text property: The error text to display when the validation function returns false
    mobilePhoneText: '手机号码必须为11位数字',
    // vtype Mask property: The keystroke filter mask
    mobilePhoneMask: /[0-9]/i,
    
    cardId : function(value){
    	return this.cardIdRe.test(value);
    },
    cardIdRe : /^([1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2})$/i,
    cardIdText : '身份证号码格式不正确'
});

/*Ext.define('App.button.Button',{
	override: "Ext.button.Button",
	width: 80
});*/

Ext.define('App.Common.TrriggerWindow',{
	extend : 'Ext.window.Window',
	alias : 'widget.trriggerWindow',
	constructor : function(config){
		var me = this;
		me.trigger = config.trigger;
		me.store = me.trigger.store;
		Ext.applyIf(config,{
			title : '查询',
			closeAction : 'hide',
			height : 450,
			width : 400,
			layout : 'border',
			items : [{
				xtype : 'form',
				region : 'north',
				layout : {
					type : 'table',
					columns : 3
				},
				items : me.trigger.queryItems,
				buttonAlign : 'center',
				buttons : [{
					text : '查询',
					iconCls : 'Zoom',
					handler : function(){
						Ext.apply(me.store.getProxy().extraParams,me.down('form').getValues());
						me.store.reload();
					}
				},{
					text : '重置',
					iconCls : 'Revert',
					handler : function(){
						me.down('form').getForm().reset();
					}
				}]
			},{
				xtype : 'grid',
				region : 'center',
				columnLines : true,
				rowLines : true,
				store : me.store,
				selModel : Ext.create('Ext.selection.CheckboxModel',{
					mode : me.trigger.singleSelect?'SINGLE':'MULTI'
				}),
				columns : me.trigger.listColumns,
				bbar : [{
					xtype: 'pagingtoolbar',
			        store: me.store,   // GridPanel中使用的数据
			        displayInfo: true
				}]
			}],
			buttons : [{
				text : '确认选择',
				iconCls : 'Accept',
				handler : function(){
					var records = me.down('grid').getSelectionModel().getSelection();
					var data;
					if(me.trigger.xtype=='textTrigger'){
						data = '';
						var values = '';
						Ext.each(records,function(record,i){
							var value = record.get(me.trigger.valueField);
							var text = record.get(me.trigger.displayField);
							data += text;
							values += value;
							if(i!=records.length-1){
								data += ',';
								values += ',';
							}
						});
						Ext.getCmp(me.trigger.valueInputId).setValue(values);
					}
					if(me.trigger.xtype=='comboTrigger'){
						data = [];
						Ext.each(records,function(record,i){
							var value = record.get(me.trigger.valueField);
							data.push(value);
						});
					}
					me.trigger.setValue(data);
					me.close();
				}
			},'-',{
				text : '取消',
				iconCls : 'Cancel',
				handler : function(){
					me.close();
				}
			}],
			listeners : {
				show : function(){
					var selModel = me.down('grid').selModel;
					var value = me.trigger.getValue();
					if(me.trigger.xtype=='textTrigger'){
						value = Ext.getCmp(me.trigger.valueInputId).getValue().split(',');
					}
					if(me.trigger.xtype=='comboTrigger'){
						value = me.trigger.getValue();
					}
					console.log(value)
					if(typeof value != 'object'){
						value = [value];
					}
					var res = [];
					selModel.store.each(function(record){
						var v = record.get(me.trigger.valueField);;
						
						Ext.each(value,function(i){
							if(i==v){
								res.push(record);
							}
						})
					});
					selModel.select(res);
				}
			}
		});
		this.callParent(arguments);
	}
});

/**
 * 自定义Trigger
 */
Ext.define('App.Common.ComboTrigger',{
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.comboTrigger',
	
	queryMode : 'remote',//remote local
	data : null,
	url : null,
	
	constructor : function(config){
		var me = this;
		
		me.url = config.url;
		me.valueField = config.valueField;
		me.displayField = config.displayField;
		me.singleSelect = config.singleSelect==null?true:config.singleSelect;
		/**
		 * [
		 * 	{
		 * 		fieldLabel : '姓名',
		 * 		name : 'lastName'
		 * 	}
		 * ]
		 */
		me.queryItems = [];
		Ext.each(config.queryItems,function(item,i){
			me.queryItems.push({
				xtype : 'textfield',
				fieldLabel : item.fieldLabel,
				labelWidth : 100,
				name : item.name,
				margin : '5 5 5 5'
			});
		});
		config.queryItems = me.queryItems;
		/**
		 * [
		 * 	{
		 * 		text : '姓名',
		 * 		dataIndex : 'lastName'
		 * 	}
		 * ]
		 */
		me.listColumns = config.listColumns;
		
		me.store = Ext.create('Ext.data.Store', {
		    fields: [],
		    proxy : {
				url : basePath + me.url,
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}	
			},
			autoLoad : true
		});
		
		
		
		Ext.applyIf(config,{
			queryMode : 'remote',
			queryParam : me.displayField,
			minChars : 1,
		    valueField : me.valueField,
		    displayField : me.displayField,
		    multiSelect : me.singleSelect?false:true,
		    /*// Template for the dropdown menu.
		    // Note the use of the "x-list-plain" and "x-boundlist-item" class,
		    // this is required to make the items selectable.
		    tpl: Ext.create('Ext.XTemplate',
		        '<ul class="x-list-plain"><tpl for=".">',
		            '<li role="option" class="x-boundlist-item">{abbr} - {name}</li>',
		        '</tpl></ul>'
		    ),
		    // template for the content inside text field
		    displayTpl: Ext.create('Ext.XTemplate',
		        '<tpl for=".">',
		            '{abbr} - {name}',
		        '</tpl>'
		    ),*/
		    triggers : {
		        foo: {
		            cls: 'x-form-search-trigger',
		            handler: function() {
		            	//清除下拉框查询条件
		            	Ext.apply(me.store.getProxy().extraParams,{});
		            	me.store.loadPage(1);
		            	me.queryWind = me.queryWind?me.queryWind:Ext.create('App.Common.TrriggerWindow',{
		        			trigger : me
		        		});
		            	me.queryWind.show();
						
		            }
		        }
		    },
			listeners : {
				afterrender : function(){
					
				}
			}
		});
		this.callParent(arguments);
	}
});

/**
 * 自定义Trigger
 */
Ext.define('App.Common.TextTrigger',{
	extend : 'Ext.form.field.Text',
	alias : 'widget.textTrigger',
	
	queryMode : 'remote',//remote local
	data : null,
	url : null,
	
	constructor : function(config){
		var me = this;
		
		me.url = config.url;
		me.valueField = config.valueField;
		me.displayField = config.displayField;
		me.singleSelect = config.singleSelect==null?true:config.singleSelect;
		me.valueInputId = config.valueInputId;//设值值的文本ID
		/**
		 * [
		 * 	{
		 * 		fieldLabel : '姓名',
		 * 		name : 'lastName'
		 * 	}
		 * ]
		 */
		me.queryItems = [];
		Ext.each(config.queryItems,function(item,i){
			me.queryItems.push({
				xtype : 'textfield',
				fieldLabel : item.fieldLabel,
				labelWidth : 100,
				name : item.name,
				margin : '5 5 5 5'
			});
		});
		config.queryItems = me.queryItems;
		/**
		 * [
		 * 	{
		 * 		text : '姓名',
		 * 		dataIndex : 'lastName'
		 * 	}
		 * ]
		 */
		me.listColumns = config.listColumns;
		
		me.store = Ext.create('Ext.data.Store', {
		    fields: [],
		    proxy : {
				url : basePath + me.url,
				timeout : 60*1000,
				type : 'ajax',
				reader : {
					type : 'json',
					rootProperty : 'rows'
				}	
			},
			autoLoad : true,
			listeners : {
				load : function(store,records){
					var field = Ext.getCmp(me.valueInputId);
					var values = field.getValue().split(',');
					var text = [];
					store.each(function(record){
						Ext.each(values,function(v){
							if(record.get(me.valueField)==v){
								text.push(record.get(me.displayField));
							}
						});
					});
					me.setValue(text);
					console.log('1111111111'+text)
				}
			}
		});
		
		Ext.applyIf(config,{
		    triggers : {
		        foo: {
		            cls: 'x-form-search-trigger',
		            handler: function() {
		            	//me.store.loadPage(1);
		            	me.queryWind = me.queryWind?me.queryWind:Ext.create('App.Common.TrriggerWindow',{
		        			trigger : me
		        		});
		            	me.queryWind.show();
		            }
		        }
		    },
			listeners : {
				afterrender : function(){
					
				}
			}
		});
		
		this.callParent(arguments);
	}
});

Ext.define('App.Common.Combobox',{
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.appCombobox',
	basicUrl : '/basicInfo/queryBasicInfoList',
	constructor : function(config){
		var me = this;
		
		if(config.basicType){
			config.valueField = 'code';
		}
		
		me.storelisteners = {
			load : function(store,records){
				var blankData = {};
				blankData[config.displayField||'name'] = '--请选择--';
				blankData[config.valueField||'value'] = '';
				store.insert(0,blankData);
			},
			beforeload : function(store , operation , eOpts){
				if(config.beforeload){
					config.beforeload(store , operation , eOpts);
				}
			}
		}
		
		
		Ext.applyIf(config,{
			store : me.createStore(config,me.storelisteners),
		    queryMode: config.url?'remote':'local',
		    displayField: config.displayField||'name',
		    valueField: config.valueField||'value',
		    listeners : {
		    	afterrender : function(){
		    		
		    	},
		    	focus : function(){
		    		//如果在表格中，获取焦点时展开
		    		if(this.column){
		    			me.expand();
		    		}
		    	}
		    }
		});
		this.callParent(arguments);
	},
	createStore : function(config,storelisteners){
		var me = this,store;
		if(config.data){
			store = Ext.create('Ext.data.Store',{
				data : config.data,
				listeners : storelisteners
			});
		}else{
			if(config.basicType){
				config.url = me.basicUrl+'?type='+config.basicType;
			}
			store = Ext.create('Ext.data.Store',{
				proxy : {
					url : basePath + config.url,
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
				listeners : storelisteners
			});
		}
		return store;
	}
});

/**
 * 文件上传组件
 */
Ext.define('App.Common.FileUpload',{
	extend : 'Ext.form.Panel',
	alias : 'widget.appfileUpload',
	constructor : function(config){
		var me = this;
		me.multiUpload = config.multiUpload||false;
		me.data = config.data;
		console.log(me.data)
		Ext.applyIf(config,{
			title : config.fieldLabel,
			height : 150,
			layout : {
				type : 'table',
				columns : 5
			},
			items : [{
				html : '<div id="'+config.id+'-add-file" style="width:120px;height:120px;background:#aaa;margin:5px 5px;text-align:center;font-size:76px;padding:16px;color:white;cursor: pointer;"><i class="fa fa-plus fa-lg"></i></div>'
			}],
			listeners : {
				afterrender : function(panel){
					Ext.getDom(panel.id+'-add-file').onclick = function(){
						me.beginUpload();
					};
					Ext.each(me.data,function(fileInfo){
                    	me.addFile(fileInfo);
                    });
				}
			}
		});
		this.callParent(arguments);
	},
	beginUpload : function(){
		var filePanel = this;
		var formPanel = Ext.create('Ext.form.Panel',{
			tbar : [{
				text : '添加',
				hidden : !filePanel.multiUpload,
				handler : function(){
					var index = this.up('form').items.length;
					this.up('form').add({
						xtype : 'filefield',
						fieldLabel : '文件'+(index+1),
						buttonText : '选择文件'
					});
				}
			}],
			layout : 'form',
			items : [{
				xtype : 'filefield',
				fieldLabel : '文件1',
				buttonText : '选择文件'
			}],
			buttons : [{
				text : '上传',
				handler : function(){
					var form = this.up('form').getForm();
		            if(form.isValid()) {
		                form.submit({
		                    url: basePath + '/common/uploadFile',
		                    waitMsg: '正在上传中，请稍候...',
		                    success: function(fp, o) {
		                        console.log(fp,o)
		                        fileWindow.close();
		                        
		                        if(!filePanel.multiUpload){
		                        	var reItem = [];
	                        		Ext.each(filePanel.items.items,function(item,i){
	                        			if(i!=filePanel.items.length-1){
	                        				reItem.push(item);
	                        			}
	                        		});
	                        		Ext.each(reItem,function(item){
	                        			filePanel.remove(item);
	                        		});
	                        	}
		                        
		                        var fileInfos = o.result.model;
		                        Ext.each(fileInfos,function(fileInfo){
		                        	filePanel.addFile(fileInfo);
		                        });
		                        
		                    }
		                });
		            }
				}
			}]
		});
		var fileWindow = Ext.create('Ext.window.Window',{
			title : '文件上传',
			width : 400,
			modal : true,
			layout : 'fit',
			items : [formPanel]
		}).show();
	},
	addFile : function(fileInfo){
		var filePanel = this;
		var html = '<div style="width:120px;height:120px;background:#aaa;margin:5px 5px;"><img style="width:100%;height:100%;" src="'+fileInfo.thumbPath+'" onclick="App.lightBoxImg(\''+fileInfo.path+'\')"></div>';
    	filePanel.insert(filePanel.items.length-1,{
        	html : html,
        	items : [{
        		xtype : 'hiddenfield',
        		name : filePanel.name,
        		value : fileInfo.id
        	}]
        });
	}
});

/**
 * 选择本地文件组件
 */
Ext.define('App.Common.SelectLocalFile',{
	extend : 'Ext.window.Window',
	alias : 'widget.selectLocalFile',
	constructor : function(config){
		var me = this;
		me.rootPath = config.inputField.rootPath;
		me.fileTree = Ext.create('Ext.tree.Panel',{
			store : {
				xtype : 'treestore',
				fields : [{
					name : 'text',
					mapping : 'name'
				},{
					name : 'leaf',
					mapping : 'file'
				},{
					name : 'id',
					mapping : 'path'
				}],
				proxy : {
					url : basePath + '/common/queryFileListByPath',
					timeout : 60*1000,
					type : 'ajax',
					reader : {
						type : 'json',
						rootProperty : 'model',
						messageProperty : 'message'
					}
				},
				root: {
			        expanded: true,
			        text : '根目录',
			        id : me.rootPath||'C:/',
			        path : me.rootPath||'C:/',
			        leaf : false
			    },
//			    parentIdProperty : 'path',
				nodeParam : 'path',
				autoLoad : true,
				listeners : {
					load : function(store , records , successful , operation , node , eOpts){
						if(!successful){
							App.alert(operation.error);
						}
					}
				}
			}
		});
		Ext.applyIf(config,{
			modal : true,
			height : 500,
			width : 400,
			layout : 'fit',
			items : [me.fileTree],
			buttons : [{
				text : '确认',
				iconCls : 'fa fa-save',
				handler : function(){
					var select = me.fileTree.getSelection();
					if(select.length>0){
						var path = select[0].get('path');
						if(config.inputField){
							config.inputField.setValue(path);
							me.close();
						}else{
							throw new error('没有配置文件路径的inputField');
						}
					}else{
						App.alert('请选择路径！');
					}
				}
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define('App.Common.SelectLocalFileField',{
	extend : 'Ext.form.field.Text',
	alias : 'widget.selectLocalFileField',
	constructor : function(config){
		var me = this;
		me.windTitle = config.windTitle;
		Ext.applyIf(config,{
        	triggers : {
        		foo : {
        			cls : 'x-form-search-trigger',
        			handler : function(){
        				Ext.create('App.Common.SelectLocalFile',{
        					title : me.windTitle||'选择文件路径',
        					inputField : me
        				}).show();
        			}
        		}
        	}
		});
		this.callParent(arguments);
	}
});
