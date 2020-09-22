<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>已测试</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
</style>
</head>
<body style="min-height: 100%; padding: 0px; margin: 0px;">
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test">
	</table>
	<script type="text/html" id="bar">
		<a class="layui-btn layui-btn-xs" lay-event="detail">查看详细</a>
	</script>
	<script>
		var basePath = '../';
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/exam/index2.js?t=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>