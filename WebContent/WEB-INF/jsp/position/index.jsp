<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>岗位管理</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../plugins/zTree/css/zTreeStyle/zTreeStyle.css" media="all">
<style>
	.layui-table, .layui-table-view {
		margin: 0 0 !important;
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
	<form class="layui-form" id="queryform" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
		<div class="layui-form-item">
			<label class="layui-form-label">部门</label>
			<div class="layui-input-inline">
				<input type="text" name="department" placeholder="请输入部门名称" autocomplete="off" class="layui-input" />
			</div>
			<label class="layui-form-label">科室/车间</label>
			<div class="layui-input-inline">
				<input type="text" name="workShop" placeholder="请输入科室/车间名称" autocomplete="off" class="layui-input" />
			</div>
			<label class="layui-form-label">班组</label>
			<div class="layui-input-inline">
				<input type="text" name="workGroup" placeholder="请输入科室/车间名称" autocomplete="off" class="layui-input" />
			</div>
			<label class="layui-form-label">&nbsp;岗位性质</label>
			<div class="layui-input-inline">
				<select name="type" lay-filter="type">
					<option value="">请选择岗位性质</option>
					<option value="0">普通岗</option>
					<option value="1">关键岗</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">岗位</label>
			<div class="layui-input-inline">
				<input type="text" name="name" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
			</div>
			<label class="layui-form-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态</label>
				<div class="layui-input-inline">
					<select name="state" class="layui-input">
						<option value="">请选择</option>
						<option value="1" selected="selected">启用</option>
						<option value="0">禁用</option>
					</select>
				</div>
			<div class="layui-input-inline">
			<shiro:hasPermission name="position:query">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
				<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
			</shiro:hasPermission>
			</div>
		</div>
	</form>
	<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
	<shiro:hasPermission name="position:create">
		<a class="layui-btn layui-btn-xs" lay-event="create">新增</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="position:update">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="position:grant">
	<a class="layui-btn layui-btn-xs" lay-event="enable">启用</a>
	<a class="layui-btn layui-btn-xs" lay-event="disable">禁用</a>
	</shiro:hasPermission>
	<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
	<a class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
	<a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
	</script>
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
		<shiro:hasPermission name="position:update">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<div id="dlg" style="padding-top: 10px; padding-right: 30px;display:none;">
		<form class="layui-form" lay-filter="frm1">
			<div class="layui-form-item">
				<label class="layui-form-label">部门</label>
				<div class="layui-input-block">
					<input type="text" style="display: none;" name="id" />
					<select class="layui-input" id="departmentId" name="departmentId" lay-filter="departmentId" required lay-verify="required" lay-search>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">科室/车间</label>
				<div class="layui-input-block">
					<select class="layui-input" id="deptId" name="deptId" lay-filter="deptId" required lay-verify="required" lay-search>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">班组</label>
				<div class="layui-input-block">
					<select class="layui-input" id="workGroupId" name="workGroupId" lay-filter="workGroupId" lay-search>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">岗位名称</label>
				<div class="layui-input-block">
					<input type="text" name="name" required lay-verify="required" placeholder="请输入岗位名称" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">岗位性质</label>
				<div class="layui-input-block">
					<select name="type" lay-filter="type" required lay-verify="required">
						<option value>请选择岗位性质</option>
						<option value="0">普通岗</option>
						<option value="1">关键岗</option>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">岗位类别</label>
				<div class="layui-input-block">
					<select class="layui-input" id="postCategory" name="postCategory" lay-filter="postCategory" required lay-verify="required" lay-search>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">复审周期</label>
				<div class="layui-input-block">
					<input type="text" name="examPeriod" required lay-verify="number" placeholder="请输入复审周期" autocomplete="off" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea name="remark" placeholder="请输入内容" class="layui-textarea"></textarea>
				</div>
			</div>
			<br/>
			<div class="layui-form-item" style="margin-bottom:0px;">
				<center>
					<a class="layui-btn layui-btn-xs" lay-submit lay-filter="tijiao">提交</a>
					<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel">取消</a>
				</center>
			</div>
		</form>
	</div>
	<script>
        var basePath = '../';
    </script>
    <script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/position/index.js?t=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>
