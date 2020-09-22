/**
 * 演示程序当前的 “注册/登录” 等操作，是基于 “本地存储” 完成的
 * 当您要参考这个演示程序进行相关 app 的开发时，
 * 请注意将相关方法调整成 “基于服务端Service” 的实现。
 **/
var C = CryptoJS;
(function($, owner) {
	/**
	 * 用户登录
	 **/
	owner.login = function(loginInfo, callback) {
		callback = callback || $.noop;
		loginInfo = loginInfo || {};
		loginInfo.username = loginInfo.account || '';
		loginInfo.password = loginInfo.password || '';
		loginInfo.password = C.enc.Base64.stringify(C.enc.Utf8.parse(loginInfo.password));
		if(loginInfo.username == ''){
			return callback('请输入账号!')
		}
		if(loginInfo.password == ''){
			return callback('请输入密码!')
		}
		mui.ajax('login.do',{
			data:loginInfo,
			dataType:'json',
			type:'post',
			timeout:10000,
			headers:{'Content-Type':'application/json'},	              
			success:function(data){
				if(data.flag){
					owner.setUserinfo(data.data);
					owner.createState(data);
					return callback();
				}else{
					return callback(data.message);
				}
			},
			error:function(xhr,type,errorThrown){
				console.log(type);
			}
		});
	};

	owner.getUserinfo = function(){
		var userinfo = localStorage.getItem('userinfo') || "{}";
		return JSON.parse(userinfo);
	};
	
	owner.setUserinfo = function(userinfo){
		localStorage.setItem('userinfo',JSON.stringify(userinfo));
	};
	
	owner.createState = function(data) {
		var state = owner.getState();
		state.account = 'lisi';
		state.token = data.token;
		owner.setState(state);
	};

	
	/**
	 * 获取当前状态
	 **/
	owner.getState = function() {
		var stateText = localStorage.getItem('$state') || "{}";
		return JSON.parse(stateText);
	};

	/**
	 * 设置当前状态
	 **/
	owner.setState = function(state) {
		state = state || {};
		localStorage.setItem('$state', JSON.stringify(state));
		//var settings = owner.getSettings();
		//settings.gestures = '';
		//owner.setSettings(settings);
	};

	/**
	 * 获取应用本地配置
	 **/
	owner.setSettings = function(settings) {
		settings = settings || {};
		localStorage.setItem('$settings', JSON.stringify(settings));
	}

	/**
	 * 设置应用本地配置
	 **/
	owner.getSettings = function() {
			var settingsText = localStorage.getItem('$settings') || "{}";
			return JSON.parse(settingsText);
		}

}(mui, window.app = {}));

function getQueryVariable(variable){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
    }
    return(false);
}