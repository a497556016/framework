<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/extjs/ext-bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/extjs/build/classic/locale/locale-zh_CN-debug.js"></script>
<style type="text/css">
.x-form-code {
 width: 73px;
 height: 20px;
 vertical-align: middle;
 cursor: pointer;
 float: left;
 margin-left: 7px;
}
</style>
<script type="text/javascript">
	var errorTimes = 0;
	var login = function(form){
		var params = form.getForm().getValues();
		if(errorTimes>=3){
			params.needCode = '1';
		}else{
			params.needCode = '0';
		}
		
		var loadMask = new Ext.LoadMask({
		   msg : '系统加载中...',
		   target : form
		});
		loadMask.show();
		Ext.Ajax.request({
			url : '<%=request.getContextPath()%>/user/login',
			params : params,
			success : function(response,opts){
				var data = Ext.util.JSON.decode(response.responseText);
				if(data.success){
					window.location.href = '${ctx}/web/index'
				}else{
					loadMask.hide();
					errorTimes++;
					if(errorTimes==3){
						form.add({
							id : 'check-code',
							xtype : 'textfield',
							fieldLabel : '验证码',
							labelWidth : 80,
							width : 220,
							margin : '5 10 5 10',
							name : 'checkCode',
						    listeners : {
						    	afterrender : function(){
						    		<%-- this.codeEl   = this.bodyEl.createChild({ tag: 'img', title:'点击刷新', src:'<%=request.getContextPath()%>/common/checkCode.do', style : 'height:30px;margin-top:5px;margin-left:0px;float:left;'});   
						    	    this.inputEl.setWidth(this.width-this.labelWidth-(100+10)); //这里减掉验证码的宽度
						    	    this.codeEl.on('click',function(){
						    	    	
						    	    }, this);   
						    	    this.codeEl.addCls('x-form-code');  --%>
						    	}
						    }
						});
						form.add({
							id : 'check-code-img',
							xtype : 'panel',
							height : 30,
							width : 77,
							margin : '5 5 5 5',
							layout : 'fit',
							html : '<img id="check_code" title="点击刷新" src="<%=request.getContextPath()%>/common/checkCode.do" style="height:30px;pointer:cursor;">',
							listeners : {
								afterrender : function(){
									this.el.on('click',function(){
										refreshCheckCode();
									},this);
								}
							}
						});
					}
					if(errorTimes>=3){
						refreshCheckCode();
					}
					Ext.Msg.alert('提示','登录失败！'+data.message);
				}
			},
			failure : function(){
				loadMask.hide();
			}
		});
	}
	Ext.onReady(function(){
		Ext.create('Ext.window.Window',{
			title : '登录',
			closable : false,
			width : 350,
			//height : 220,
			layout : 'fit',
			items : [{
				xtype : 'form',
				layout : {
					type : 'table',
					columns : 2
				},
				defaults : {
					enableKeyEvents : true
				},
				items : [{
					xtype : 'textfield',
					fieldLabel : '用户名',
					labelWidth : 80,
					width : 315,
					margin : '5 10 5 10',
					name : 'personCode',
					colspan : 2,
					listeners: {
		                specialkey: function(field, e){
		                    if (e.getKey() == e.ENTER) {
		                        var form = field.up('form');
		                        login(form);
		                    }
		                }
		            }
				},{
					xtype : 'textfield',
					inputType : 'password',
					fieldLabel : '密码',
					labelWidth : 80,
					width : 315,
					margin : '5 10 5 10',
					name : 'password',
					colspan : 2,
					listeners: {
		                specialkey: function(field, e){
		                    if (e.getKey() == e.ENTER) {
		                        var form = field.up('form');
		                        login(form);
		                    }
		                }
		            }
				}],
				buttonAlign : 'center',
				buttons : [{
					text : '登录',
					handler : function(btn){
						var form = this.up('form');
						login(form);
					}
				},{
					text : '重置',
					handler : function(){
						var form = this.up('form');
						form.reset();
					}
				}]
			}]
		}).show();
	});
	
	function refreshCheckCode(){
		var checkCode = document.getElementById('check_code');
		console.log(checkCode)
		checkCode.src = '<%=request.getContextPath()%>/common/checkCode?random='+new Date().getTime();
	}
</script>
</head>
<body>
	
</body>
</html>