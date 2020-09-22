<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告管理</title>
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
	<script type="text/html" id="toolbar">
	<shiro:hasPermission name="news:create">
		<a class="layui-btn layui-btn-xs" lay-event="create">新增</a>
	</shiro:hasPermission>
	</form>
	</script>
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
		<shiro:hasPermission name="news:delete">
			<a class="layui-btn layui-btn-xs" lay-event="delete">删除</a>
		</shiro:hasPermission>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<iframe class="layui-hide" id="download"></iframe>
	<div id="dlg" style="padding-top: 10px; padding-right: 30px;display:none;">
		<form id="frm1" class="layui-form" lay-filter="frm1">
			<div class="layui-form-item">
				<label class="layui-form-label">标题</label>
				<div class="layui-input-block">
					<input type="text" style="display: none;" name="id" />
					<input type="text" class="layui-input" autocomplete="false" required="required" lay-verify="required" name="title" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">内容</label>
				<div class="layui-input-block">
					<script id="editor" name="content" type="text/plain" style="width:100%;height:200px;margin-bottom:10px;"></script>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">附件</label>
				<div class="layui-input-block">
					<button type="button" class="layui-btn layui-btn-xs" id="test1">
					  <i class="layui-icon">&#xe67c;</i>上传附件
					</button>
					<div id="file">
					</div>
				</div>
			</div>
			<div class="layui-form-item layui-footer" style="margin-bottom: 10px; padding-left: 110px;">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="tijiao">提交</a>
				<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel">取消</a>
			</div>
		</form>
	</div>
	<script>
        var basePath = '../';
    </script>
    <script type="text/javascript" charset="utf-8" src="../plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../plugins/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="../plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/news/index.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
</body>
</html>