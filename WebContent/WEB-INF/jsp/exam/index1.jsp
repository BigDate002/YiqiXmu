<%@page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>待测试</title>
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
		{{#if(d.exam.beginDate<=today&&today<=d.exam.endDate){}}
			<a class="layui-btn layui-btn-xs" lay-event="detail">开始测试</a>
		{{#} }}
	</script>
	<script>
		var today = '<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>';
		var basePath = '../';
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/exam/index1.js?t=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>