<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>课程信息</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../plugins/zTree/css/zTreeStyle/zTreeStyle.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
	#form1 .layui-form-label{
		width:80px;
	}
	#form1 .layui-input-inline{
		width:180px;
	}
</style>
</head>
<body style="min-height:100%;padding:0px;margin:0px;">
	<script id="toolbar" type="text/html">
		<form class="layui-form" id="form1" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
			<div class="layui-form-item">
				<label class="layui-form-label">部门</label>
				<div class="layui-input-inline">
					<input type="text" name="department" placeholder="请输入部门" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">科室车间</label>
				<div class="layui-input-inline">
					<input type="text" name="workShop" placeholder="请输入科室车间" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">课程名称</label>
				<div class="layui-input-inline">
					<input type="text" name="courseName" placeholder="请输入课程名称" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">类型</label>
				<div class="layui-input-inline">
					<select name="type" class="layui-input">
						<option value>请选择</option>
						<option value="单选题">单选题</option>
						<option value="多选题">多选题</option>
						<option value="判断题">判断题</option>
						<option value="填空题">填空题</option>
						<option value="简答题">简答题</option>
						<option value="理论考核项目">理论考核项目</option>
						<option value="技能考核项目">技能考核项目</option>
					</select>
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
					<shiro:hasPermission name="question:query">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
					</shiro:hasPermission>
				</div>
			</div>
			<shiro:hasPermission name="question:create">
				<a class="layui-btn layui-btn-xs" lay-event="create">新增</a>
			</shiro:hasPermission>
			<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
			<shiro:hasPermission name="question:update">
				<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="question:delete">
				<a class="layui-btn layui-btn-xs" lay-event="del">禁用</a>
				<a class="layui-btn layui-btn-xs" lay-event="enable">启用</a>
			</shiro:hasPermission>
			<!--<shiro:hasPermission name="question:grant">
				<a class="layui-btn" lay-event="enable">启用</a>
				<a class="layui-btn" lay-event="disable">禁用</a>
			</shiro:hasPermission>-->
			<shiro:hasPermission name="question:exportData">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="question:importData">
				<a class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
				<a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
			</shiro:hasPermission>
		</form>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
		<shiro:hasPermission name="question:update">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="question:update">
			<a class="layui-btn layui-btn-xs" lay-event="del">删除</a>
		</shiro:hasPermission>
	</script>
	<script type="text/html" id="state">
		{{d.state==1?'必答':'非必答'}}
	</script>
	
	<div id="dlg" style="padding-top: 10px; padding-right: 30px;display:none;">
		<form class="layui-form" lay-filter="frm1">
			<input type="text" style="display: none;" name="id" />
			<div class="layui-form-item">
				<label class="layui-form-label">课程</label>
				<div class="layui-input-block">
					<select name="courseId" id="courseId" class="layui-input" required lay-verify="required">
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">类型</label>
				<div class="layui-input-block">
					<select name="type" class="layui-input" required lay-verify="required">
						<option value>请选择</option>
						<option value="单选题">单选题</option>
						<option value="多选题">多选题</option>
						<option value="判断题">判断题</option>
						<option value="填空题">填空题</option>
						<option value="简答题">简答题</option>
						<option value="理论考核项目">理论考核项目</option>
						<option value="技能考核项目">技能考核项目</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">问题</label>
				<div class="layui-input-block">
					<input type="text" name="content" required lay-verify="required" placeholder="请输入内容" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">选项</label>
				<div class="layui-input-block">
					<input type="text" name="selections" placeholder="请输入选项内容" autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">答案</label>
				<div class="layui-input-block">
					<input type="text" name="answer" placeholder="请输入问题答案" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" style="margin-bottom:0px;">
				<center>
					<a class="layui-btn" lay-submit lay-filter="tijiao">提交</a>
					<a class="layui-btn layui-btn-primary btn-cancel">取消</a>
				</center>
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
	<script src="../js/question/index.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>