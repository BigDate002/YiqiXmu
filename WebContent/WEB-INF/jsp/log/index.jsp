<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登陆日志</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
	.layui-table-cell{
		min-height:24px;
	}
</style>
</head>
<body style="min-height:100%;padding:0px;margin:0px;">
	<script type="text/html" id="toolbar">
	<form class="layui-form" id="form" lay-filter="queryform">
		<div class="layui-form-item">
   			<label class="layui-form-label" style="width:40px">工号</label>
			<div class="layui-input-inline">
				<input class="layui-input" id="usercode" name="usercode" placeholder="请输入工号" />
			</div>
			<label class="layui-form-label" style="width:40px">从</label>
			<div class="layui-input-inline">
				<input type="text" name="beginDate" id="beginDate" required lay-verify="required" readonly="readonly" placeholder="请输入开始日期" autocomplete="off" class="layui-input test-item">
			</div>
			<label class="layui-form-label" style="width:40px">到</label>
			<div class="layui-input-inline">
				<input type="text" name="endDate" id="endDate" required lay-verify="required" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">
			</div>
			<label class="layui-form-label" style="width:40px">
			<a class="layui-btn layui-btn-xs" lay-event="query" style="float:right;">查询</a>
			</label>
		</div>
	</form>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/zTree/js/jquery.ztree.all.js"></script>
	<script>
		$.fn.serializeObject = function()
		{
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push(this.value || '');
		        } else {
		            o[this.name] = this.value || '';
		        }
		    });
		    return o;
		};
		var idx = 0;
		 layui.config({
			 base:'../js/'
			 ,version: '<%=System.currentTimeMillis()%>'
		 });
		layui.use([  'laypage', 'layer', 'table', 'element', 'slider' ,'commons','form', 'laydate'],
			function() {
				var laypage = layui.laypage //分页
				, layer = layui.layer //弹层
				, table = layui.table //表格
				, element = layui.element
				, form = layui.form
				, commons = layui.commons()
				, $ = layui.jquery
				, laydate = layui.laydate;
				var index1 = layer.load(1, {
					shade : [ 0.5, '#ccc' ]
				});
				//执行一个 table 实例
				table.render({
					id : 'userTable',
					elem : '#demo',
					height : 'full-0',
					method : 'post',
					url : 'queryPage.do',
					title : '登陆日志',
					limit : 10,
					limits : [ 10, 15, 20, 30, 50, 100 ],
					page : {
						layout : [ 'limit', 'prev', 'page', 'next','skip', 'count' ],
						groups : 5
					}
					, toolbar : '#toolbar'
					, defaultToolbar : []
					, totalRow : false //开启合计行
					, cols : [ [ //表头
					{
						type: 'numbers', 
						title: '序号',
						align : 'center',
						width : 60
					}
					, { field : 'usercode', title : '账号', width : 120, align : 'center'}
					, { field : 'ip', title : 'IP地址', width : 180, align : 'center'}
					, { field : 'currentTime', title: '操作时间', width:150, align : 'center'}
					, { field : 'type', title : '登陆方式',width: 80,templet: function(d){return d.type==1?'手机':'浏览器'}, align : 'center'}
					, { field : 'result', title : '登陆结果',width: 80,templet: function(d){return d.result==1?'成功':'失败'}, align : 'center'}
					 ] ],
					done : function(res) {
						lay('.test-item').each(function () {
					        laydate.render({
					            elem: this
					            , format: 'yyyy-MM-dd'   
					            , trigger: 'click'
					        });
					    });
						layer.close(index1);
					}
				});
			//表刷新方法
	        var reloadTable = function (param) {
				var obj = $("#form").serializeObject();
	            table.reload("userTable", {
	            	where : obj,
	            	done : function(res){
	            		form.val('queryform', obj);
	            		lay('.test-item').each(function () {
					        laydate.render({
					            elem: this
					            , format: 'yyyy-MM-dd'   
					            , trigger: 'click'
					        });
					    });
	            	}
	            });
	        };
			//监听头工具栏事件
			table.on('toolbar(test)',
				function(obj) {
					var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; //获取选中的数据
					switch (obj.event) {
					case 'query':
						reloadTable();
				}
			});
		});
	</script>
</body>
</html>