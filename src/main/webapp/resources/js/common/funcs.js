var App = {};

App.request = function(url,params,call,opts){
	var config = Ext.applyIf({
		loadMask : true
	},opts);
	
	var loadMask = config.loadMask?new Ext.LoadMask({
	    msg    : '请求数据中...',
	    target : Ext.getBody().component
	}):null;
	
	if(loadMask){
		loadMask.show();
	}
	Ext.Ajax.request({
		url : basePath + url,
		params : params,
		success : function(response,opts){
			if(loadMask){
				loadMask.hide();
			}
			
			var data = Ext.util.JSON.decode(response.responseText);
			call(data,true);
		},
		failure : function(response,opts){
			if(loadMask){
				loadMask.hide();
			}
			try{
				var data = Ext.util.JSON.decode(response.responseText);
				if(!data.success){
					App.alert(data.message);
				}
			}catch(e){
				call({
					success : false,
					message : '系统异常，请稍候重试。'
				},false);
			}
		}
	});
}

App.alert = function(msg,call,title){
	Ext.Msg.alert(title||'提示',msg,call);
}
App.confirm = function(msg,call,title){
	Ext.Msg.confirm(title||'确认',msg,function(yn){
		call(yn=='yes');
	});
}
App.toast = function(msg){
	Ext.toast({
		html : msg,
		timeout : 2000,
		closable : true,
	    align: 't'
	});
}


App.lightBoxImg = function(path){
	Ext.create('Ext.window.Window',{
		height : 450,
		width : 500,
		modal : true,
		layout : 'fit',
		html : '<img style="width:100%;height:100%;" src="'+path+'">'
	}).show();
}

App.gridComboColumnRender = function(value, metaData, record, rowIndex, colIndex){
	var editor = metaData.column.getEditor();
	if(editor&&editor.xtype=='appCombobox'){
		var store = editor.getStore();
		if(value instanceof Array){
			var re = '';
			for(var i in value){
				var rec = store.findRecord(editor.valueField,value[i]);
				if(rec){
					re += rec.getData()[editor.displayField];
				}
				if(i!=value.length-1){
					re += ',';
				}
			}
			return re;
		}else{
			var rec = store.findRecord(editor.valueField,value);
			if(rec){
				return rec.getData()[editor.displayField];
			}
		}
		
	}
	return value;
}

App.storeToArray = function(store,expected){
	var data = [];
	store.each(function(record){
		var d = record.getData();
		if(expected){
			for(var i in expected){
				delete d[expected[i]];
			}
		}
		data.push(d);
	});
	return data;
}

App.CamelCase = {
	/**
	 * 转换成下划线形式
	 * @param name 要转换的字符串
	 */
	camelToHyphen : function(name){
		return name.replace(/([a-z])([A-Z])/g, '$1_$2').toLowerCase();
	},
	/**
	 * 转换驼峰命名
	 * @param name 要转换的字符串
	 * @param excludeFirst 排除前缀
	 */
	hyphenToCamel : function(name,excludeFirst){
		var ss = name.split('_');
		var r = '';
		for(var i in ss){
			var c = ss[i];
			if(!excludeFirst&&i==0){
				r += (c.charAt(0).toUpperCase()+c.substring(1,c.length).toLowerCase());
			}else{
				r += (c.charAt(0).toUpperCase()+c.substring(1,c.length).toLowerCase());
			}
		}
		return r;
	}
}
