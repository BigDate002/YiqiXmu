<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>资质管理</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../plugins/zTree/css/zTreeStyle/zTreeStyle.css" media="all">
<style>
	.layui-table, .layui-table-view {
		margin: 0 0 !important;
	}
	u{
		color:#8E2323
	}
	hr{
		border: 0;
    	border-bottom: 2px solid #999999;
	}
	#queryform .layui-form-label {
		width: auto;
	}
	#queryform .layui-input-inline {
		width: 130px;
	}
	#queryform .layui-btn {
	vertical-align: top;
}

</style>
</head>
<body style="min-height: 100%; margin: 0px;">
	<form id="xiazai" method="post" style="display:none;"></form>
	<iframe class="layui-hide" id="download"></iframe>
	<script type="text/html" id="toolbar">
	<form method="POST action="exportData.do" class="layui-form" id="queryform" lay-filter="queryform">

	<div class="layui-form-item" style="float:right;">
		<div class="layui-input-inline">
			<select name="type" >
				<option value="">请选择资质状态</option>
				<option value="1">待复审资质</option>
				<option value="0">有效资质</option>
			</select>
		</div>
		<div class="layui-input-inline" style="width:auto">
			<shiro:hasPermission name="qualify:query">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
				<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
			</shiro:hasPermission>
		</div>
	</div>

		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看资质证书</a>
		<shiro:hasPermission name="qualify:exportData">
			<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
		</shiro:hasPermission>
	</form>
	</script>
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<div id="detail" style="min-width:480px;display:none;" >
	</div>
	<script>
        var basePath = '../';
    </script>
    <script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/qualify/mine.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>
