<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="css/login/layui.css">
<link rel="stylesheet" href="css/login/login.css">
<script type="text/javascript" src="lib/js/layui/layui.all.js" charset="utf-8"></script>
<script type="text/javascript" src="lib/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="lib/js/jquery-3.1.1.min.js"></script>
<script src="lib/crypto/lib-typedarrays.js"></script>
<script src="lib/crypto/x64-core.js"></script>
<script src="lib/crypto/core.js" type="text/javascript" charset="utf-8"></script>
<script src="lib/crypto/enc-base64.js" type="text/javascript" charset="utf-8"></script>
<style>
.kit-login-form{
  		width: 240px\9;
  		overflow: hidden;
  		position: absolute\9;
  		left: 50%\9;
  		top: 50%\9;
  		transform: translate(100%,-50%)\9\0;
  	}
  	.kit-login-slogan{
  		-webkit-text-stroke: 1px white;
  		text-shadow: 0px 0px 0px 3px #fff;
  	}
  	.kit-login-slogan{
  		position: absolute\9;
  		left: 50%\9;
  		top: 50%\9;
  		transform: translate(-100%,-100%)\9\0;
  	}
  	_:-ms-lang(x),
  	.kit-login-slogan,.kit-login-form{
  		transform: translate(0px,0px);
  	}
  	_:-ms-lang(x),
  	.kit-login-slogan,.kit-login-form{
  		transform: translate(0px,0px)9;
  		position: relative;
  		left: 0;
  		top: 0;
  	}
	.kit-login-col span#forgot{
		cursor: pointer;
	}
</style>
</head>

<body>
	<div class="kit-login" onselectstart="return false;">
		<div class="kit-login-bg" style="background-image: url(images/login.png);"></div>
		<div class="kit-login-wapper">
			<h2 class="kit-login-slogan" style="text-align: center;font-size: 48px;letter-spacing: 2px;line-height: 48px;margin-bottom: 9px;font-weight: 900;color: #009688;-webkit-text-stroke: 0px white;font-family: STSong;">
				欢迎使用资质管理系统
			</h2>
			<div class="kit-login-form">
				<h4 class="kit-login-title">登录</h4>
				<form class="layui-form"  style="height: 130px;" action="login.do" method="POST">
					<div class="kit-login-row">
						<div class="kit-login-col">
							<i class="layui-icon">&#xe612;</i>
							<span class="kit-login-input">
								<input type="text" name="username" id="username"
								lay-verify="required" placeholder="请输入账号" />
							</span>
						</div>
						<div class="kit-login-col"></div>
					</div>
					<div class="kit-login-row">
						<div class="kit-login-col">
							<i class="layui-icon">&#xe64c;</i> <span class="kit-login-input">
								<input type="password" name="password" id="userpassword"
								lay-verify="required" placeholder="请输入密码" />
							</span>
						</div>
						<div class="kit-login-col"></div>
					</div>
					<div class="kit-login-row">
						<button class="layui-btn kit-login-btn" lay-submit
							lay-filter="formDemo" VALUE="登录">登录</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<script>
	var C = CryptoJS;
	layui.use('form', function(){
			//判断是否存在父级页面，如果存在刷新父级页面
			if (top != self) {
			    top.window.location.reload();
			};		
		  var form = layui.form;
		  //监听提交1
		  form.on('submit(formDemo)', function(data){
			  	document.getElementById('userpassword').value = C.enc.Base64.stringify(C.enc.Utf8.parse(data.field.password));
			    return true;
		  });
		  var msg = '<%=request.getAttribute("msg") == null ? "" : request.getAttribute("msg")%>';
		  var username = '<%=request.getAttribute("username") == null ? "" : request.getAttribute("username")%>';
		if (msg != "") {
			layer.msg(msg)
		}
		if (username != "") {
			$('#username').val(username);
		}
	});
</script>
</html>