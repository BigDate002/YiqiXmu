<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>考核结果</title>
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
	.edui-editor-breadcrumb{
	display:none;
	}	
</style>
</head>
<body style="min-height: 100%; margin: 0px;">
	<script id="toolbar" type="text/html">
		<form class="layui-form" id="queryform" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
			<div class="layui-form-item">
				<label class="layui-form-label">所在部门</label>
				<div class="layui-input-inline">
					<input type="text" name="department" placeholder="请输入部门" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">科室/车间</label>
				<div class="layui-input-inline">
					<input type="text" name="workShop" placeholder="请输入科室车间" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">班组</label>
				<div class="layui-input-inline">
					<input type="text" name="workGroup" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
				</div>				
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">考核日期</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" name="beginDate" readonly="readonly" placeholder="请输入开始日期" autocomplete="off" class="layui-input test-item">
				</div>
				<label class="layui-form-label">—&nbsp;&nbsp;&nbsp;</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" name="endDate" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">
				</div>
				<label class="layui-form-label">工号</label>
				<div class="layui-input-inline">
					<input type="text" name="usercode" placeholder="请输入人员工号" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-inline">
					<input type="text" name="username" placeholder="请输入人员姓名" autocomplete="off" class="layui-input" />
				</div>
				<div class="layui-input-inline" style="width:150px">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
				</div>
			</div>
			<shiro:hasPermission name="examresult:create">
				<a class="layui-btn layui-btn-xs" lay-event="create">新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="examresult:exportData">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="examresult:importData">
				<a class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
				<a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
			</shiro:hasPermission>
		</form>
	</script>
	<script type="text/html" id="barDemo">
		<shiro:hasPermission name="examResult:edit">
			 {{#if("<shiro:principal property="role" />"!="班组管理员"||(d.year==new Date().getFullYear()&&d.month>new Date().getMonth())){ }}
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			 {{#} }}
		</shiro:hasPermission>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<iframe class="layui-hide" id="download"></iframe>
	<div id="dlg" style="padding-top: 10px; padding-right: 30px;display:none;">
		<form id="frm1" class="layui-form" lay-filter="frm1">
			<div class="layui-form-item">
				<label class="layui-form-label">考核日期</label>
				<div class="layui-input-block">
					<input type="text" id="year" name="year" readonly="readonly" required lay-verify="required" placeholder="请输入考核日期" autocomplete="off" class="layui-input test-item">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">考核人员</label>
				<div class="layui-input-block">
					<input type="text" style="display: none;" name="id" />
					<select class="layui-input" id="usercode" required lay-verify="required" name="usercode" lay-filter="departmentId" lay-search>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">考核结果</label>
				<div class="layui-input-block">
				<input name="result" class="layui-input" required lay-verify="required" >
			</div>
			</div>
			<div class="layui-form-item layui-footer" style="margin-bottom: 10px; padding-left: 110px;">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="tijiao">提交</a>
				<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel">取消</a>
			</div>
		</form>
	</div>
	<iframe class="layui-hide" id="download"></iframe>
	<form id="xiazai" method="post" style="display: none;"></form>
	<script>
        var basePath = '../';
    </script>
    <script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/examresult/index.js?version=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>