<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
</head>
<body>
	<div class="layui-tab" id="tabs" lay-filter="tab">
		<ul class="layui-tab-title">
			<li class="layui-this">新办资质模板</li>
			<li>复审资质模板</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">
				<img src="../images/u1229.jpg" />
			</div>
			<div class="layui-tab-item">
				<img src="../images/u1227.jpg" />
			</div>
		</div>
	</div>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
	<script>
		layui.use([ 'element','form' ], function() {
			var element = layui.element;
			var form = layui.form;
			form.render('checkbox');
			var height = $(window).height()-100;
			$('#bishi').height(height);
		});
	</script>
</body>
</html>