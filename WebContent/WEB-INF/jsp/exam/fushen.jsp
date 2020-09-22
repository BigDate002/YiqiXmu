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
	width: 70px;
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
		<form class="layui-form" id="form1" lay-filter="queryform">
			<div class="layui-form-item">
				<label class="layui-form-label">部门</label>
				<div class="layui-input-inline">
					<input type="text" name="department" placeholder="请输入部门" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">科室车间</label>
				<div class="layui-input-inline">
					<input type="text" name="workShop" placeholder="请输入科室车间" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">岗位</label>
				<div class="layui-input-inline">
					<input type="text" name="post" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">状态</label>
				<div class="layui-input-inline">
					<select name="state" class="layui-input">
						<option value>请选择</option>
						<option value="0">正常</option>
						<option value="1">待复审</option>
						<option value="2">过期</option>
						<option value="3">复审中</option>
						<option value="4">复审未通过</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">生效日期从</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" name="beginDate1" readonly="readonly" placeholder="请输入开始日期" autocomplete="off" class="layui-input test-item">
				</div>
				<label class="layui-form-label">到</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" name="beginDate2" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">
				</div>
				<label class="layui-form-label">失效日期从</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" name="endDate1" readonly="readonly" placeholder="请输入开始日期" autocomplete="off" class="layui-input test-item">
				</div>
				<label class="layui-form-label">到</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" name="endDate2" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">
				</div>
				<div class="layui-input-inline" style="width:150px">
					<shiro:hasPermission name="exam:query">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
					</shiro:hasPermission>
				</div>
			</div>
		</form>
		<shiro:hasPermission name="fushen:startFushen">	
		    <a class="layui-btn layui-btn-xs" lay-event="start">发布复审</a>
		</shiro:hasPermission>
	</script>
	<script id="barDemo" type="text/html">
		<shiro:hasPermission name="exam:score">	
		    {{#if(d.state==1&&d.status==1){ }}
		    	<a class="layui-btn layui-btn-xs" lay-event="edit">评分</a>
		    {{#} }}
		</shiro:hasPermission>
		<shiro:hasPermission name="exam:finish">	
		    {{#if(d.status==1){ }}	
		    	<a class="layui-btn layui-btn-xs" lay-event="finish">结束复审</a>
		    {{#} }}
		</shiro:hasPermission>
		<shiro:hasPermission name="exam:cancle">	
		    {{#if(d.status<2){ }}
		    <a class="layui-btn layui-btn-xs" lay-event="cancel">取消复审</a>
		    {{#} }}
		</shiro:hasPermission>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
	</script>
	<script type="text/html" id="state">
		{{d.state==1?'启用':'禁用'}}
	</script>

	<div id="dlg" class="aaaa" style="height: 100%;padding:0px 20px 0px 0px;">
		<form id="fushen" class="layui-form" lay-filter="frm1" style="height: 100%;">
			<input type="text" name="id" style="display:none;"/>
			<div class="layui-form-item" style="padding-top: 5px;">
				<div class="layui-form-item">
					<label class="layui-form-label">开始日期</label>
					<div class="layui-input-block">
						<input type="text" name="beginDate" id="beginDate" required lay-verify="required" readonly="readonly" placeholder="请输入开始日期" autocomplete="off" class="layui-input test-item">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">结束日期</label>
					<div class="layui-input-block">
						<input type="text" name="endDate" id="endDate" required lay-verify="required" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">考试时长(分)</label>
					<div class="layui-input-block">
						<input type="text" name="examtime" required lay-verify="number" placeholder="请输入考试时长" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>

			<div class="layui-form-item layui-footer" style="margin-bottom: 10px; padding-left: 110px; position: absolute; bottom: 0px;">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="tijiao">提交</a>
				<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel" style="margin-left:20px;">取消</a>
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
	<script src="../js/exam/fushen.js?tms=<%=System.currentTimeMillis() %>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>