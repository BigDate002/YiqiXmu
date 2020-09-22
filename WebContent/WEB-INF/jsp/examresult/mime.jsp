<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的考核</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
</style>
</head>
<body style="min-height:100%;padding:0px;margin:0px;">
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script>
		 layui.config({
			 base:'../js/'
			 ,version: '<%=System.currentTimeMillis()%>' //为了更新 js 缓存，可忽略
		 });
		layui.use([  'laypage', 'layer', 'table', 'element', 'slider' ,'commons','form'],
			function() {
				var laypage = layui.laypage //分页
				, layer = layui.layer //弹层
				, table = layui.table //表格
				, element = layui.element //元素操作
				, slider = layui.slider //滑块
				, form = layui.form
				, commons = layui.commons() //右下角提示
				, $ = layui.jquery
				
				var index1 = layer.load(1, {
					shade : [ 0.5, '#ccc' ]
				})
				table.render({
					id : 'userTable',
					elem : '#demo',
					height : 'full-0',
					method : 'post',
					url : '..//examresult/queryPage.do',
					title : '考核列表',
					limit : 10,
					limits : [ 10, 15, 20, 30, 50, 100 ],
					page : {
						layout : [ 'limit', 'prev', 'page', 'next','skip', 'count' ],
						groups : 5
					}
					,defaultToolbar : []
					,totalRow : false
					,cols : [ [
					 // { type : 'checkbox', fixed : 'left' }
					{ type : 'numbers', title : '序号', width : 70, align : 'center' }
					, { field : 'department', title : '部门', width:150, align : 'center'}
					, { field : 'workShop', title : '科室/车间', width : 150, align:'center'}
					, {field : 'workGroup', title : '班组', width : 150, align:'center'}
					, {field : 'year', title: '考核时间', width:90, align:'center', templet:function(d){return d.year+'年'+d.month+'月'}}
					, {field : 'result', title: '考核结果', width:80, align:'center'}
					] ],
					done : function(res) {
						layer.close(index1);
					}
				});
		});
	</script>
</body>
</html>