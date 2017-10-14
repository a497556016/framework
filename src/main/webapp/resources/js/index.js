

Ext.define("App.Main.TabPanel",{
	extend : "Ext.tab.Panel",
	constructor : function(config){
		Ext.applyIf(config,{
			items : [{
				title : '<i class="fa fa-home fa-lg"></i>首页',
				layout : 'fit',
				items : [{
        			xtype : 'uxiframe',
        			src : basePath + '/main'
        		}]
			}]
		});
		this.callParent(arguments);
	}
});

Ext.define("App.Main.MenuPanel",{
	extend : "Ext.list.Tree",
	constructor : function(config){
		var me = this;
		me.tabPanel = config.tabPanel;
		Ext.applyIf(config,{
            ui: 'navigation',
            expanderFirst: false,
            expanderOnly: false,
            scrollable : true,
            listeners: {
            	itemclick: function(sender,info){
            		var node = info.node;
                	if(!node.data.href){
                		return;
                	}
                	
                	var title = '<i class="'+node.data.iconCls+'"></i>&nbsp;'+node.data.text,
                	curTab;
                	Ext.each(me.tabPanel.items.items,function(item,index){
                		if(item.title==title){
                			curTab = item;
                			return;
                		}
                	});
                	if(!curTab){
                		curTab = me.tabPanel.add({
                    		title : title,
    						layout : 'fit',
    						closable : true,
    						items : [{
    		        			xtype : 'uxiframe',
    		        			src : node.data.href
    		        		}]
                    	});
                		
                	}
                	
                	console.log(curTab)
                	me.tabPanel.setActiveTab(curTab);
                }
            },
			store: {
		        root: {
		            expanded: true,
		            children: menuData
		        }
		    }
		});
		this.callParent(arguments);
	}
});

Ext.define("App.Main.Viewport",{
	extend : "Ext.container.Viewport",
	constructor : function(config){
		var me = this;
		me.tabPanel = Ext.create("App.Main.TabPanel",{
			id : 'main-tab-panel',
			region : 'center'
		});
		me.menuPanel = Ext.create("App.Main.MenuPanel",{
			tabPanel : me.tabPanel
		});
		Ext.applyIf(config,{
			layout: 'border',
		    items: [{
		        region: 'north',
		        //title : '欢迎XX登录XX系统',
		        html : '<div style="height:60px;background:blue;position: relative;">'+
		        '<div class="logo"></div>'+
		        /**顶部菜单栏*/
		        /*'<a class="cls1"><div class="first-menu"><div class="icon"><i class="fa fa-edit fa-lg"></i></div><div class="text">系统管理</div></div></a>'+
		        '<div class="split"></div>'+
		        '<a class="cls1"><div class="first-menu"><div class="icon"><i class="fa fa-edit fa-lg"></i></div><div class="text">系统配置</div></div></a>'+
		        '<div class="split"></div>'+
		        '<a class="cls1"><div class="first-menu"><div class="icon"><i class="fa fa-edit fa-lg"></i></div><div class="text">系统管理</div></div></a>'+
		        '<div class="split"></div>'+
		        '<a class="cls1"><div class="first-menu"><div class="icon"><i class="fa fa-edit fa-lg"></i></div><div class="text">系统管理</div></div></a>'+
		        '<div class="split"></div>'+
		        '<a class="cls1"><div class="first-menu"><div class="icon"><i class="fa fa-edit fa-lg"></i></div><div class="text">系统管理</div></div></a>'+
		        '<div class="split"></div>'+*/
		        
		        '<div style="position:absolute;bottom:0px;right:0px;">'+
		        '<div style="float:left;margin-top:5px;margin-right:5px;color:#fff;">欢迎XX登录系统</div>'+
		        '<div id="theme-change" style="float:left"></div>'+
		        '<div id="logout" style="float:left"></div>'+
		        '</div>'+
		        
		        '</div>',
		        listeners : {
		        	afterrender : function(){
		        		console.log(Ext.query('.first-menu'))
		        		Ext.each(Ext.query('.first-menu'),function(cmp){
		        			console.log(Ext.fly(cmp))
		        			
		        			/*Ext.fly(cmp).on('click',function(){
		        				console.log('click',this)
		        			})*/
		        			cmp.onclick = function(){
		        				console.log('click',this)
		        				Ext.create('Ext.menu.Menu',{
			        				renderTo : Ext.getBody(),
			        				items : [{
			        					text : '用户管理',
			        					iconCls : 'fa fa-plus',
			        					handler : function(){
			        						addTabPanel(this.text,'http://www.baidu.com')
			        					}
			        				},{
			        			        xtype: 'menuseparator'
			        			    },{
			        					text : '栏目管理',
			        					iconCls : 'fa fa-plus',
			        					menu : {
			        						items : [{
				        						text : '不知道',
					        					iconCls : 'fa fa-plus'
				        					}]
			        					}
			        					
			        				}]
			        			}).showAt(this.offsetLeft,this.offsetTop+60);
		        			}
		        		});
		        		
		        		Ext.create('Ext.button.Button',{
		        			renderTo : 'theme-change',
		        			text : '切换主题',
		        			menu : {
		        				items: [
				                    // these will render as dropdown menu items when the arrow is clicked:
				                    {
				                    	text: '主题 -流行', 
				                    	dataIndex : 'triton',
				                    	handler: changeTheme
				                    },
				                    {
				                    	text: '主题 -简约', 
				                    	dataIndex : 'crisp',
				                    	handler: changeTheme
				                    },
				                    {
				                    	text: '主题 -非主流', 
				                    	dataIndex : 'neptune',
				                    	handler: changeTheme
				                    },
				                    {
				                    	text: '主题 -传统', 
				                    	dataIndex : 'classic',
				                    	handler: changeTheme
				                    }
				                ]
		        			}
		        		});
		        		
		        		Ext.create('Ext.button.Button',{
		        			renderTo : 'logout',
		        			text : '安全退出',
		        			handler : function(){
		        				window.location.href = basePath + '/user/logout';
		        				/*App.request('/user/logout',{},function(re){
		        					if(re.success){
		        						window.location.href = sysPath+'/user/login';
		        					}
		        				})*/
		        			}
		        		})
		        	}
		        }/*,
		        buttons : ['->',{
		        	text : '主题切换',
		        	menu: {
		                items: [
		                    // these will render as dropdown menu items when the arrow is clicked:
		                    {
		                    	text: '主题 -流行', 
		                    	dataIndex : 'triton',
		                    	handler: changeTheme
		                    },
		                    {
		                    	text: '主题 -简约', 
		                    	dataIndex : 'crisp',
		                    	handler: changeTheme
		                    },
		                    {
		                    	text: '主题 -非主流', 
		                    	dataIndex : 'neptune',
		                    	handler: changeTheme
		                    },
		                    {
		                    	text: '主题 -传统', 
		                    	dataIndex : 'classic',
		                    	handler: changeTheme
		                    }
		                ]
		            }
		        }]*/
		    }, {
		        region: 'west',
		        collapsible: true,
		        scrollable : true,
		        title: '<i class="fa fa-bars fa-lg"></i>&nbsp;菜单栏',
		        width: 230,
		        defaults : {
		        	bodyStyle : 'padding:0px;'
		        },
		        //style : 'border-width:0px;',
		        items : [me.menuPanel],
		        split : true
		        // could use a TreePanel or AccordionLayout for navigational items
		    }, me.tabPanel]
		});
		this.callParent(arguments);
	}
});

function changeTheme(btn){
	var dataIndex = btn.dataIndex;

	Ext.util.Cookies.set("curTheme",dataIndex);
	
	window.location.reload();
	
	 
}

function addTabPanel(title,href){
	var tabPanel = Ext.getCmp('main-tab-panel');
	var curTab;
	Ext.each(tabPanel.items.items,function(item,index){
		if(item.title==title){
			curTab = item;
			return;
		}
	});
	if(!curTab){
		curTab = tabPanel.add({
			title : title,
			layout : 'fit',
			closable : true,
			items : [{
				xtype : 'uxiframe',
				src : href
			}]
		});
	}
	tabPanel.setActiveTab(curTab);
}

function closeCurTab(){
	var tabPanel = Ext.getCmp('main-tab-panel');
	var curTab = tabPanel.getActiveTab();
	tabPanel.remove(curTab);
}

var viewport,menuData = [{
    text: '首页',
    leaf: true,
    iconCls: 'fa fa-home fa-lg',
    baseCls : 'menu',
    href : '##'
}, {
    text: 'Demo',
    expanded: false,
    iconCls: 'fa fa-folder fa-lg',
    children: [{
        text: '表格',
        leaf: true,
        iconCls: 'fa fa-book fa-lg',
    	href : '../grid.jsp'
    }, {
        text: 'nono',
        leaf: true,
        iconCls: 'fa fa-graduation-cap fa-lg',
    	href : 'http://www.baidu.com'
    }]
}];
Ext.onReady(function(){
	var menuJsonData = Ext.JSON.decode(loginUserMenu);
	Ext.each(menuJsonData,function(menu){
		var o = {
			text: menu.text,
            leaf: false,
            iconCls: menu.iconCls,
            children : []
		};
		if(menu.child&&menu.child.length>0){
			Ext.each(menu.child,function(child){
				o.children.push({
					text: child.text,
			        leaf: true,
			        iconCls: child.iconCls,
			    	href : basePath + child.url
				});
			});
		}
		menuData.push(o);
	});
	
	viewport = Ext.create("App.Main.Viewport",{
		
	});
	
});