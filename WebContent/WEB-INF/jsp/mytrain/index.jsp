<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>培训列表</title>
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
					url : '..//mytrain/queryPage.do',
					where : {state : ${state }},
					title : '培训列表',
					limit : 10,
					limits : [ 10, 15, 20, 30, 50, 100 ],
					page : {
						layout : [ 'limit', 'prev', 'page', 'next','skip', 'count' ],
						groups : 5
					}
					,defaultToolbar : []
					,totalRow : false
					,cols : [ [
					  { type : 'checkbox', fixed : 'left' }
					, { type : 'numbers', title : '序号', width : 70, align : 'center' }
					, { field : 'position', title : '岗位', width:100, align : 'center'}
					, { field : 'courseName', title : '课程', width : 100, align:'center'}
					, {field : 'beginDate', title : '开始日期', width : 100 }
					, {field : 'endDate', title: '结束日期', width:100}
					, {field : 'studyState', title: '学习状态', width:100, toolbar:"#bar"}
					] ],
					done : function(res) {
						layer.close(index1);
						$('#query').val(name);
					}

				});
		});
	</script>
	<script type="text/html" id="bar">
		var studyState=studyState;
		if(studyState==0){
			<button type="button" class="layui-btn" style="background-color: #f00">未打卡</button>
		}else{
			<button type="button" class="layui-btn" style="background-color: #00B83F">已打卡</button>
		}
	</script>

<%--	<c:if test="${studyState==0}">--%>
<%--		<button type="button" class="layui-btn" style="background-color: #f00">未打卡</button>--%>
<%--	</c:if>--%>
<%--	<c:if test="${studyState>0}">--%>
<%--		<button type="button" class="layui-btn" style="background-color: #f00">已打卡</button>--%>
<%--	</c:if>--%>


	<!--打卡按钮-->
<%--	<script type="text/html" id="studyState">--%>
<%--		if(studyState==0){--%>
<%--			btnName= ‘未打卡’--%>
<%--			<a class="layui-btn layui-btn-xs" lay-event="detail">{{btnName}}</a>--%>
<%--		}else{--%>
<%--			<a class="layui-btn layui-btn-xs" lay-event="detail">已打卡</a>--%>
<%--		}--%>
<%--	</script>--%>
</body>
</html>