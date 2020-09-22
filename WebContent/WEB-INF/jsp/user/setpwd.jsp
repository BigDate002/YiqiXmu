<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
.layui-form-item {
	margin-top: 10px;
}
</style>
</head>
<body>
	<form lay-filter="form" class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">原密码</label>
			<div class="layui-input-inline">
				<input type="password" name="oldpwd" required lay-verify="required" placeholder="请输入原密码" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">新密码</label>
			<div class="layui-input-inline">
				<input type="password" name="newpass" required lay-verify="required" placeholder="请输入新密码" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">确认</label>
			<div class="layui-input-inline">
				<input type="password" name="newpass2" required lay-verify="required" placeholder="请输入新密码确认" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"></label>
			<button class="layui-btn layui-btn-xs" lay-submit lay-filter="submit">提交</button>
		</div>
	</form>
</body>
<script src="../plugins/layui/layui.js"></script>
<script>
	layui.config(
		{
			base : '../js/'
		});
	
	layui.use([ 'element', 'form', 'layer', 'commons' ], function() {
		var checkPass = function(s) {
			if (s.length < 8) {
				return 0;
			}
			var ls = 0;
			if (s.match(/([a-z])+/)) {
				ls++;
			}
			if (s.match(/([0-9])+/)) {
				ls++;
			}
			if (s.match(/([A-Z])+/)) {
				ls++;
			}
			if (s.match(/[^a-zA-Z0-9]+/)) {
				ls++;
			}
			return ls;
		}
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		var commons = layui.commons();
		form.on('submit(submit)', function(obj) {
			var data = obj.field;
			if (data.newpass != data.newpass2) {
				layer.msg('新密码两次输入不一致');
				return false;
			}
			if(checkPass(data.newpass)<3){
				layer.msg("密码必须是字母大写，小写字母，数字，特殊字符中任意三种组合且长度不小于8位，请重新设置！");
		    	return false;
			}
			layer.load();
			$.ajax(
				{
					type : "post",
					url : 'updatePassword.do',
					dateType : "json",
					contentType : 'application/json',
					data : JSON.stringify(data),
					success : function(result) {
						layer.closeAll();
						if (result.flag) {
							if(window.parent==self){
								window.location = '../index.html';
							}
							commons.showInfo(result.message);
							return;
						}
						layer.msg(result.message);
					}
				});
			return false;
		});
	})
</script>
</html>