

Ext.define('App.System.UserManage.AddUserWind',{
	extend : 'Ext.window.Window',
	constructor : function(config){
		var me = this;
		me.userInfo = config.userInfo;
		me.formPanel = Ext.create('Ext.form.Panel',{
			layout : {
				type : 'table',
				columns : 1
			},
			defaults : {
				margin : '5 10 5 10',
				width : 500
			},
			items : [{
				xtype : 'hidden',
				name : 'id'
			},{
				xtype : 'textfield',
				fieldLabel : '姓名',
				labelWidth : 100,
				name : 'lastName',
				maxLength : 16,
				allowBlank : false
			},{
				xtype : 'textfield',
				fieldLabel : '账号',
				labelWidth : 100,
				name : 'personCode',
				maxLength : 16,
				allowBlank : false
			},{
				xtype : 'textfield',
				fieldLabel : '密码',
				labelWidth : 100,
				name : 'password',
				inputType : 'password',
				allowBlank : me.userInfo?true:false
			},{
				xtype : 'textfield',
				fieldLabel : '确认密码',
				labelWidth : 100,
				name : 'confirmPassword',
				inputType : 'password',
				allowBlank : me.userInfo?true:false,
				validator : function(value){
					return value == me.formPanel.getValues().password?true : '两次输入密码不一致';
				}
			},{
				xtype : 'textfield',
				fieldLabel : '邮箱',
				labelWidth : 100,
				name : 'email',
				vtype : 'email'
			},{
				xtype : 'numberfield',
				fieldLabel : '手机',
				labelWidth : 100,
				name : 'mobilePhone',
				maxLength : 11,
				vtype: 'mobilePhone',
				allowBlank : false
			},{
				xtype : 'textfield',
				fieldLabel : '身份证号码',
				labelWidth : 100,
				name : 'cardId',
				maxLength : 18,
				vtype: 'cardId'
			},{
				xtype : 'comboTrigger',
				fieldLabel : '角色',
				labelWidth : 100,
				name : 'roleCodes',
				queryMode : 'remote',
				url : '/role/queryRoleList',
				valueField : 'roleCode',
				displayField : 'roleName',
				singleSelect : false,
				queryItems : [{
					fieldLabel : '角色名称',
					name : 'roleName'
				}],
				listColumns : [{
	        		text : '角色编码',
	        		dataIndex : 'roleCode'
	        	},{
	        		text : '角色名称',
	        		dataIndex : 'roleName'
	        	},{
	        		text : '角色描述',
	        		dataIndex : 'remark',
	        		flex : 1
	        	}]
			},{
				xtype : 'appfileUpload',
				fieldLabel : '上传照片:',
				id : 'user-photo',
				name : 'userPhoto.id',
				multiUpload : false,
				data : me.userInfo?[me.userInfo.userPhoto]:[]
			}]
		});
		
		Ext.applyIf(config,{
			//height : 450,
			width : 520,
			layout : 'fit',
			modal : true,
			items : [me.formPanel],
			buttons : [{
				text : '保存',
				iconCls : 'Accept',
				handler : function(){
					var valid = me.formPanel.isValid();
					if(valid){
						var edit = me.userInfo?true:false;
						console.log(me.formPanel.getValues())
						
						App.request('/user/'+(edit?'update':'add')+'User.do',me.formPanel.getValues(),function(result){
							if(result.success){
								App.alert((edit?'更新':'添加')+'用户成功！',function(){
									config.grid.getStore().reload();
									me.close();
								});
							}else{
								App.alert(result.message);
							}
						})
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
					if(me.userInfo)
					me.formPanel.getForm().setValues(me.userInfo);
				}
			}
		});
		this.callParent(arguments);
	}
});


Ext.define("App.System.UserManage.QueryPanel",{
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

Ext.define("App.System.UserManage.ListPanel",{
	extend : 'Ext.grid.Panel',
	constructor : function(config){
		var me = this;
		me.store = Ext.create('Ext.data.Store',{
			fields : ['text'],
			proxy : {
				url : basePath + '/user/queryUserList',
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
								App.toast((record.get('locked')?'锁定':'解锁')+'成功！');
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
        		text : '账号',
        		dataIndex : 'personCode',
        		width : 150
        	},{
        		text : '姓名',
        		dataIndex : 'lastName',
        		width : 150
        	},{
        		text : '邮箱',
        		dataIndex : 'email'
        	},{
        		text : '手机',
        		dataIndex : 'mobilePhone'
        	},{
        		text : '部门',
        		dataIndex : 'deptName'
        	},{
        		text : '角色',
        		dataIndex : 'roleNames',
        		flex : 1
        	},{
        		xtype : 'checkcolumn',
        		text : '锁定',
        		dataIndex : 'locked'
        	},{
        		xtype:'actioncolumn',
        		text : '编辑',
        		align : 'center',
        		items: [{
                    iconCls: 'Applicationedit',
                    tooltip: '编辑用户信息',
                    handler: function(grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        App.request('/user/getUser',{
                        	id : rec.get('id')
                        },function(re){
                        	if(re.success){
                        		Ext.create('App.System.UserManage.AddUserWind',{
            						title : '编辑用户',
            						grid : me,
            						userInfo : re.model
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
				width : 80,
				handler : function(){
					Ext.create('App.System.UserManage.AddUserWind',{
						title : '新增用户',
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
							var users = [];
							Ext.each(records,function(record,index){
								users.push(record.getData());
							})
							App.request('/user/deleteUsers',{
								userInfosJson : Ext.encode(users)
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

Ext.define("App.System.UserManage.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.queryPanel = Ext.create('App.System.UserManage.QueryPanel',{
			region: 'north'
		});
		me.gridPanel = Ext.create('App.System.UserManage.ListPanel',{
			region: 'center'
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [me.queryPanel,me.gridPanel]
		});
		this.callParent(arguments);
	}
});

