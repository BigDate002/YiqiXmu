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
				<label class="layui-form-label">岗位</label>
				<div class="layui-input-inline">
					<input type="text" name="positionName" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">课程名称</label>
				<div class="layui-input-inline">
					<input type="text" name="name" placeholder="请输入课程名称" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">类型</label>
				<div class="layui-input-inline">
					<select name="type" class="layui-input">
						<option value>请选择</option>
						<option value="0">通用培训</option>
						<option value="1">资质培训</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-inline">
					<select name="state" class="layui-input">
						<option value>请选择</option>
						<option value="1" selected="selected">启用</option>
						<option value="0">禁用</option>
					</select>
				</div>
				<div class="layui-input-inline" style="width:150px">
					<shiro:hasPermission name="course:query">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
					</shiro:hasPermission>
				</div>
			</div>
			<!--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>-->
			<shiro:hasPermission name="course:create">
				<a class="layui-btn layui-btn-xs" lay-event="create">新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="course:update">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="course:update">
			<a class="layui-btn layui-btn-xs" lay-event="enable">启用</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="course:update">
			<a class="layui-btn layui-btn-xs" lay-event="del">禁用</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="course:grant">
				<a class="layui-btn layui-btn-xs" lay-event="enable">启用</a>
				<a class="layui-btn layui-btn-xs" lay-event="disable">禁用</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="course:exportData">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="course:importData">
				<a class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
				<a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
			</shiro:hasPermission>
		</form>
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
		<shiro:hasPermission name="course:update">
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="course:update">
			{{#if(d.state==1){}}
			<a class="layui-btn layui-btn-xs" lay-event="del">删除</a>
			{{#}}}
		</shiro:hasPermission>
		<shiro:hasPermission name="course:update">
			{{#if(d.state==0){}}
			<a class="layui-btn layui-btn-xs" lay-event="enable">恢复</a>
			{{#}}}
		</shiro:hasPermission>
	</script>
	<script type="text/html" id="state">
		{{d.state==1?'启用':'禁用'}}
	</script>
	<script type="text/html" id="state">
		{{d.state==1?'启用':'禁用'}}
	</script>
	<script id="toolbarson" type="text/html">
		<form class="layui-form" id="form3" lay-filter="queryform3" onkeydown="if(event.keyCode==13){return false}">
			<div class="layui-form-item">

			<input type="hidden" id="courseId" name="courseId" />

			<shiro:hasPermission name="course:importData">
				<div class="layui-input-inline">
					<input type="text" name="name" placeholder="请输入岗位" autocomplete="off" class="layui-input" />
				</div>
				<div class="layui-input-inline" style="width:10px;">
				</div>
                <a class="layui-btn layui-btn-xs" lay-event="selectGW">岗位搜索</a>
				<a class="layui-btn layui-btn-xs" lay-event="deleteGW" style="margin-left:10px;">岗位删除</a>
			</shiro:hasPermission>
		</form>
	</script>
	<table class="layui-hide" id="demo1" style="margin: 0px;" lay-filter="test1"></table>
	<div id="dlg1" style="display: none;">
		<form class="layui-form" id="form2" style="padding:5px;" lay-filter="querydept">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width:35px">岗位</label>
				<div class="layui-input-inline">
					<input type="text" name="name" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
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
				<label class="layui-form-label">类型</label>
				<div class="layui-input-inline">
					<select name="type" class="layui-input" required lay-verify="required" lay-filter="type">
						<option value>请选择类型</option>
						<option value="0">通用培训</option>
						<option value="1">资质培训</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item zzkc">
				<label class="layui-form-label">部门</label>
				<div class="layui-input-inline">
					<input type="text" style="display: none;" name="id" />
					<select class="layui-input" id="departmentId" name="departmentId" lay-filter="departmentId" lay-search>
					</select>
				</div>
			</div>
			<div class="layui-form-item zzkc">
				<label class="layui-form-label">科室/车间</label>
				<div class="layui-input-inline">
					<select class="layui-input" id="workShopId" name="workShopId" lay-filter="workShopId" lay-search>
					</select>
				</div>
			</div>
			<div class="layui-form-item zzkc" style="padding-top: 5px;">
				<label class="layui-form-label">岗位<input type="text" name="id" style="display:none;"/></label>
				<div class="layui-input-inline" style="width: 420px; max-height:180px; overflow-y: scroll; padding-top:3px; min-height: 1px;" id="xxx"></div>
				<label class="layui-form-label" style="width: 30px;"> <a class="layui-btn layui-btn-xs" id="checkdept">选择</a>
				</label>
			</div>

			<div class="layui-form-item" style="padding: 5px 0px">
				<label class="layui-form-label">课程名称</label>
				<div class="layui-input-inline">
					<input type="text" style="width: 400px;" name="name" required lay-verify="required" placeholder="请输入课程名称" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item" style="height: 29px;">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-inline">
					<input type="text" name="remark" style="width: 400px;" placeholder="请输入内容" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item" style="position: fixed; bottom: 20px;">
				<label class="layui-form-label"></label>
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
	<script src="../js/course/index.js?t=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
	<script >
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
	</script>
</body>
</html>
