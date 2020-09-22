<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>人员信息</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../plugins/zTree/css/zTreeStyle/zTreeStyle.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
	#form1 .layui-form-label{
		width:60px;
	}
	#form1 .layui-input-inline{
		width:150px;
	}
	.c1{
		width:120px;
		text-align: left;
	}
	#dlg .layui-form-label{
		width:60px;
	}
	#dlg .layui-input-block{
		margin-left:70px;
		margin-right:10px;
		min-height: 30px;
	}
	#dlg .layui-input-inline{
		width:120px;
		min-height: 30px;
	}
</style>
</head>
<body style="min-height:100%;padding:0px;margin:0px;">
	<script id="toolbar" type="text/html">
		<form class="layui-form" id="form1" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
			<div class="layui-form-item">
				<label class="layui-form-label">工号</label>
				<div class="layui-input-inline">
					<input type="text" name="usercode" placeholder="请输入工号" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-inline">
					<input type="text" name="query" placeholder="请输入姓名" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">部门</label>
				<div class="layui-input-inline">
					<input type="text" name="department" placeholder="请输入部门" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">科室车间</label>
				<div class="layui-input-inline">
					<input type="text" name="workShop" placeholder="请输入科室车间" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">班组</label>
				<div class="layui-input-inline">
					<input type="text" name="dept" placeholder="请输入班组" autocomplete="off" class="layui-input" />
				</div>
				<!--<label class="layui-form-label">岗位</label>
				<div class="layui-input-inline">
					<input type="text" name="positionName" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
				</div>-->
				<label class="layui-form-label">状态</label>
				<div class="layui-input-inline">
					<select name="userState" class="layui-input">
						<option value>请选择</option>
						<option value="0">离职</option>
						<option value="1" selected="selected">在职</option>
					</select>
				</div>
				<label class="layui-form-label"></label>
				<div class="layui-input-inline">
					<shiro:hasPermission name="staff:query">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
					</shiro:hasPermission>
				</div>
			</div>
			<shiro:hasPermission name="staff:create">
				<a class="layui-btn layui-btn-xs" lay-event="create">新增</a>
			</shiro:hasPermission>
			<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
			<shiro:hasPermission name="staff:update">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:del">
				<a class="layui-btn layui-btn-xs" lay-event="del">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:grant">
				<a class="layui-btn layui-btn-xs" lay-event="enable">启用</a>
				<a class="layui-btn layui-btn-xs" lay-event="disable">禁用</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:exportData">
			<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="staff:importData">
			<a id="importExcel" class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
			<a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
			</shiro:hasPermission>
		</form>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
		<shiro:hasPermission name="staff:update">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
	</script>
	<script type="text/html" id="state">
		{{d.userState==0?'离职':'在职'}}
	</script>

	<div id="dlg" style="padding-top: 5px; padding-right: 20px;display:none;">
		<form class="layui-form" lay-filter="frm1">
			<div class="layui-form-item">
				<label class="layui-form-label">工号</label>
				<div class="layui-input-inline" style="width:133px;">
					<input type="text" style="display: none;" name="id" />
					<input type="text" name="usercode" required lay-verify="required" placeholder="请输入工号" autocomplete="off" class="layui-input">
				</div>
				<label class="layui-form-label" style="width:30px">姓名</label>
				<div class="layui-input-inline" style="width:133px;">
					<input type="text" name="name" required lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
				</div>
				<label class="layui-form-label" style="width:30px">性别</label>
				<div class="layui-input-inline" style="width:134px;">
					<select name="sex" required lay-verify="required" class="layui-input">
						<option value>请选择</option>
						<option value="0">女</option>
						<option value="1">男</option>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">部门</label>
				<div class="layui-input-block">
					<select id="department" name="departmentId" lay-filter="department" required lay-verify="required">
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">科室/车间</label>
				<div class="layui-input-block">
					<select id="workShop" name="workShopId" lay-filter="workShop" required lay-verify="required">
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">班组</label>
				<div class="layui-input-block">
					<select id="deptId" name="deptId" lay-filter="deptId">
					</select>
				</div>
			</div>

			<!-- <div class="layui-form-item">
				<label class="layui-form-label">储备岗位</label>
				<div class="layui-input-block" id="xx">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">现任岗位</label>
				<div class="layui-input-block" id="xxx">
				</div>
			</div>
			 -->

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea style="margin:5px 0px;" name="remark" placeholder="请输入内容" class="layui-textarea"></textarea>
				</div>
			</div>

			<div class="layui-form-item" style="margin-bottom:0px;position: absolute;bottom: 5px;">
				<label class="layui-form-label"></label>
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="tijiao">提交</a>
				<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel">取消</a>
			</div>
		</form>
	</div>
	<form id="xiazai" method="post" style="display:none;"></form>
	<iframe class="layui-hide" id="download"></iframe>
	<script>
		var basePath = '../';
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/staff/index.js?t=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>
