<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<% 
	request.setAttribute("aa","?timestamp="+System.currentTimeMillis());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>人员管理</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../plugins/zTree/css/zTreeStyle/zTreeStyle.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
</style>
</head>
<body style="min-height:100%;padding:0px;margin:0px;">
	<script type="text/html" id="toolbar">
		<div class="layui-input-inline">
			<select class="layui-input"  id="roles" name="roles" lay-search>
				<option value>请选择角色</option>
			</select>
		</div>
		<shiro:hasPermission name="user:updateRole">
			<a class="layui-btn layui-btn-xs" lay-event="updateRole">关联角色</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="user:addDept">
			<a class="layui-btn layui-btn-xs" lay-event="addDept">关联部门</a>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="user:resetPassword">
		<a class="layui-btn layui-btn-xs" lay-event="resetPassword">重置密码</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="user:importData">
		{{#  if('<shiro:principal property="usercode" />'=='GW00147407'){}}
			<a id="importExcel" class="layui-btn layui-btn-xs" lay-event="importExcel">工学转正导入</a>
			<a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
		{{#}}}
		</shiro:hasPermission>
		<shiro:hasPermission name="user:query">
		<a class="layui-btn layui-btn-xs" lay-event="query" style="float:right;">查询</a>
		</shiro:hasPermission>
		<input class="layui-input" id="query" placeholder="请输入工号/姓名" style="width:200px;display:inline;float:right;" />
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
	</script>
	<script type="text/html" id="state">
		{{d.userState==0?'离职':'在职'}}
	</script>
	<div id="dlg4" style="display:none;padding:10px">
		<ul id="ztree0" class="ztree" style="width:460px;height:270px; overflow:auto;"></ul>
		<div class="layui-form-item" style="position: absolute; bottom:5px; padding-left:160px;">
		      <button class="layui-btn" lay-submit lay-filter="formSubmit3">提交</button>
		      <button type="button" class="layui-btn layui-btn-primary btn-cancel" >取消</button>
		 </div>
	</div>
	<iframe class="layui-hide" id="download"></iframe>
	<script>
		var basePath = '../';
	</script>
	<script src="../js/json2.js${aa }"></script>
	<script src="../plugins/layui/layui.js${aa }"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js${aa }"></script>
	<script src="../plugins/zTree/js/jquery.ztree.all.js${aa }"></script>
	<script src="../js/user/index.js${aa }"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js${aa }"></script>
</body>
</html>