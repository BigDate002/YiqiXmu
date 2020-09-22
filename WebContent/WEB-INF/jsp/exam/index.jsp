<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	margin: 0 0 !important;
}

#form1 .layui-form-label {
	width: auto;
}

#form1 .layui-input-inline {
	width: 100px;
	vertical-align: top;
}

#form2 .layui-form-label {
	width: 60px;
}

#form2 .layui-input-inline {
	width: 100px;
}

#form2 .layui-btn, #form1 .layui-btn {
	vertical-align: top;
}
.aaaa{
	display:none;
}
</style>
</head>
<body style="min-height: 100%; padding: 0px; margin: 0px;">
	<script id="toolbar" type="text/html">
		<form class="layui-form" id="form1" method="post" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
			<div class="layui-form-item">
				<label class="layui-form-label">部门</label>
				<div class="layui-input-inline">
					<input type="text" name="department" placeholder="请输入部门" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">科室车间</label>
				<div class="layui-input-inline">
					<input type="text" name="workShop" placeholder="请输入科室车间" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;岗位</label>
				<div class="layui-input-inline">
					<input type="text" name="postionName" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">课程名称</label>
				<div class="layui-input-inline">
					<input type="text" name="name" placeholder="请输入课程名称" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">答题状态</label>
				<div class="layui-input-inline">
					<select name="state" class="layui-input">
						<option value>请选择</option>
						<option value="0">未开始</option>
						<option value="1">已提交</option>
						<option value="2">已评分</option>
					</select>
				</div>
            </div>
			<div class="layui-form-item">
				<label class="layui-form-label">工号</label>
				<div class="layui-input-inline">
					<input type="text" name="usercode" placeholder="请输入工号" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名</label>
				<div class="layui-input-inline">
					<input type="text" name="username" placeholder="请输入姓名" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">考核结果</label>
				<div class="layui-input-inline">
					<select name="result" class="layui-input">
						<option value>请选择</option>
						<option value="1">合格</option>
						<option value="2">不合格</option>
						<option value="3">过期</option>
					</select>
				</div>
				<label class="layui-form-label">人员状态</label>
				<div class="layui-input-inline">
					<select name="userState" class="layui-input">
						<option value>请选择</option>
						<option value="0">离职</option>
						<option value="1" selected="selected">在职</option>
					</select>
				</div>
				<div class="layui-input-inline" style="width:100px">
					<shiro:hasPermission name="exam:query">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
					</shiro:hasPermission>
				</div>
			</div>
		</form>
		<a class="layui-btn layui-btn-xs" lay-event="detail">查看详细</a>
		<shiro:hasPermission name="exam:score">
		    	<a class="layui-btn layui-btn-xs" lay-event="edit">评分</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="exam:cancle">
		    <a class="layui-btn layui-btn-xs" lay-event="cancel">取消测试</a>
		</shiro:hasPermission>
		<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
		<shiro:hasPermission name="exam:hideOropen">
			<a class="layui-btn layui-btn-xs" lay-event="hide">隐藏</a>
			<a class="layui-btn layui-btn-xs" lay-event="open">开启</a>
		</shiro:hasPermission>
	</script>
	<script id="barDemo" type="text/html">
		<a class="layui-btn layui-btn-xs" lay-event="detail">查看详细</a>
		<shiro:hasPermission name="exam:score">
		    {{#if(d.state==1&&d.status==1){ }}
		    	<a class="layui-btn layui-btn-xs" lay-event="edit">评分</a>
		    {{#} }}
		</shiro:hasPermission>
		<shiro:hasPermission name="exam:start">
		    {{#if(d.status==1000){ }}
		    	<a class="layui-btn layui-btn-xs" lay-event="start">开始测试</a>
		    {{#} }}
		</shiro:hasPermission>
		<shiro:hasPermission name="exam:finish">
		    {{#if(d.status==1){ }}
		    	<a class="layui-btn layui-btn-xs" lay-event="finish">结束测试</a>
		    {{#} }}
		</shiro:hasPermission>
		<shiro:hasPermission name="exam:cancle">
		    {{#if(d.status<2){ }}
		    <a class="layui-btn layui-btn-xs" lay-event="cancel">取消测试</a>
		    {{#} }}
		</shiro:hasPermission>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
	</script>
	<script type="text/html" id="state">
		{{d.state==1?'启用':'禁用'}}
	</script>

	<div id="dlg1" style="display: none;">
		<form class="layui-form" id="form2" style="padding:5px;" lay-filter="querydept">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width:35px">部门</label>
				<div class="layui-input-inline">
					<input type="hidden" name="state" value="1"/>
					<input type="text" name="department" placeholder="请输入部门" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">科室车间</label>
				<div class="layui-input-inline">
					<input type="text" name="workShop" placeholder="请输入科室车间" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label" style="width:35px">岗位</label>
				<div class="layui-input-inline">
					<input type="text" name="postionName" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
				</div>
				<div class="layui-input-inline" style="width:90px;">
					<shiro:hasPermission name="course:query">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query2">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset2">重置</a>
					</shiro:hasPermission>
				</div>
			</div>
		</form>
		<table class="layui-hide" id="dept" style="margin: 0px;" lay-filter="dept"></table>
	</div>
	<div id="dlg" class="aaaa" style="height: 100%;">
		<form class="layui-form" lay-filter="frm1" style="height: 100%;">
			<div class="layui-form-item" style="padding-top: 5px;">
				<label class="layui-form-label">岗位<input type="text" name="id" style="display:none;"/></label>
				<div class="layui-input-inline" style="width: 400px; padding-top:3px; min-height: 1px;" id="xxx"></div>
				<label class="layui-form-label" style="width: 30px;"> <a class="layui-btn layui-btn-xs" id="checkdept">选择</a>
				</label>
			</div>

			<div class="layui-form-item" style="padding: 5px 0px">
				<label class="layui-form-label">课程名称</label>
				<div class="layui-input-inline">
					<input type="text" style="width: 400px;" name="name" required lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" style="height: 29px;">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-inline">
					<textarea name="remark" style="width: 400px; height: 150px;" placeholder="请输入内容" class="layui-textarea"></textarea>
				</div>
			</div>

			<div class="layui-form-item layui-footer" style="margin-bottom: 10px; padding-left: 110px; position: absolute; bottom: 0px;">
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
	<script src="../js/exam/index.js?t=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>
