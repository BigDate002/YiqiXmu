<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发布培训</title>
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

	<script type="text/html" id="toolbar1">
	<form method="POST action="exportData.do" class="layui-form" id="queryform" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
		<div class="layui-form-item">
			<label class="layui-form-label">
				<a class="layui-btn layui-btn-xs" lay-event="adduser">添加</a>
				<a class="layui-btn layui-btn-xs" lay-event="closethis">取消</a>
			</label>
			<label class="layui-form-label" style="float:right">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="queryuser">查询</a>
			</label>
			<div class="layui-input-inline" style="float:right">
				<input type="text" id="query" name="query" placeholder="请输入人员工号/姓名" autocomplete="off" class="layui-input" />
			</div>
		</div>
	</form>
	</script>
	<script type="text/html" id="toolbar">
	<form method="POST action="exportData.do" class="layui-form" id="queryform" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
		<div class="layui-form-item">
			<label class="layui-form-label">课程</label>
			<div class="layui-input-inline">
				<input type="text" name="courseName" placeholder="请输入课程名称" autocomplete="off" class="layui-input" />
			</div>
			<label class="layui-form-label">岗位</label>
			<div class="layui-input-inline">
				<input type="text" name="position" placeholder="请输入岗位名称" autocomplete="off" class="layui-input" />
			</div>
			<label class="layui-form-label">状态</label>
			<div class="layui-input-inline">
				<select name="state" lay-filter="state">
					<option value>请选择</option>
					<option value="1">正常</option>
					<option value="2">已结束</option>
					<option value="0">已取消</option>
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

			<div class="layui-input-inline">
			<shiro:hasPermission name="train:query">
				<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
				<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
			</shiro:hasPermission>
			</div>
		</div>
	<shiro:hasPermission name="train:create">
		<a class="layui-btn layui-btn-xs" lay-event="create">发布通用培训</a>
		<a class="layui-btn layui-btn-xs" lay-event="create2">发布资质培训</a>
		<shiro:hasPermission name="train:cancel">
		<a class="layui-btn layui-btn-xs" lay-event="cancel">取消培训</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="train:stop">
		<a class="layui-btn layui-btn-xs" lay-event="stop">结束培训</a>
		</shiro:hasPermission>
	</shiro:hasPermission>
	<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>

	<shiro:hasPermission name="train:hideOropen">
		<a class="layui-btn layui-btn-xs" lay-event="hide">隐藏</a>
		<a class="layui-btn layui-btn-xs" lay-event="open">开启</a>
	</shiro:hasPermission>

	</form>
	</script>
	<script type="text/html" id="barDemo">
		{{#if(d.state==1){}}
		<shiro:hasPermission name="train:cancel">
		<a class="layui-btn layui-btn-xs" lay-event="cancel">取消培训</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="train:stop">
		<a class="layui-btn layui-btn-xs" lay-event="stop">结束培训</a>
		</shiro:hasPermission>
		{{#} }}
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<div id="dlg" style="padding-top: 10px; padding-right: 30px;display:none;">
		<form id="fbpx" class="layui-form" lay-filter="frm1">

			<div class="layui-form-item">
				<label class="layui-form-label">培训讲师</label>
				<div class="layui-input-block">
					<div style="display: none;">
					<select id="teacher" ></select>
					</div>
					<input name="teacher" type="text" class="layui-input" required lay-verify="required" lay-search>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">培训课程</label>
				<div class="layui-input-block">
					<select id="courseId" name="courseId" lay-filter="courseId" required lay-verify="required">
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">部门</label>
				<div class="layui-input-block">
					<input type="text" style="display: none;" name="id" />
					<select class="layui-input" id="department" name="department" lay-filter="department" lay-search>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">科室/车间</label>
				<div class="layui-input-block">
					<select class="layui-input" id="dept" name="dept" lay-filter="dept" lay-search>
					</select>
				</div>
			</div>


			<div class="layui-form-item" style="padding-bottom:5px">
				<label class="layui-form-label">人员</label>
				<div class="layui-input-block">
					<a target="userlist1" class="layui-btn layui-btn-xs chooseUser">选择人员</a>
					<div class="userlist" id="userlist1"></div>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">培训日期</label>
				<div class="layui-input-block">
					<input type="text" required lay-verify="required" readonly="readonly" placeholder="请选择日期范围" autocomplete="off" class="layui-input test-item">
					<input type="hidden" name="beginDate" id="beginDate3" >
					<input type="hidden" name="endDate" id="endDate3" >
				</div>
			</div>

<%--			<div class="layui-form-item">--%>
<%--				<label class="layui-form-label">结束日期</label>--%>
<%--				<div class="layui-input-block">--%>
<%--					<input type="text" name="endDate" id="endDate" required lay-verify="required" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">--%>
<%--				</div>--%>
<%--			</div>--%>

			<div class="layui-form-item" style="margin-bottom:0px;margin-top:5px;position: absolute;bottom:10px;">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<a class="layui-btn layui-btn-xs" lay-submit lay-filter="tijiao">提交</a>
					<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel">取消</a>
				</div>
			</div>
		</form>
	</div>

	<div id="dlg3" style="padding-top: 10px; padding-right: 30px;display:none;">
		<form id="fbpx2" class="layui-form" lay-filter="frm2">
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
			<div class="layui-form-item" id="pos">
				<label class="layui-form-label">岗位</label>
				<div class="layui-input-block">
					<select id="postionId" lay-verify="required" lay-search required name="postionId" lay-filter="postionId">
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">课程</label>
				<div class="layui-input-block" id="courseList">
				</div>
			</div>

			<div class="layui-form-item" style="padding-bottom:5px">
				<label class="layui-form-label">人员</label>
				<div class="layui-input-block">
					<a target="userlist2" class="layui-btn layui-btn-xs chooseUser">选择人员</a>
					<div class="userlist" id="userlist2"></div>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">培训日期</label>
				<div class="layui-input-block">
					<input type="text" required lay-verify="required" readonly="readonly" placeholder="请选择日期范围" autocomplete="off" class="layui-input test-item">
					<input type="hidden" name="beginDate" id="beginDate" >
					<input type="hidden" name="endDate" id="endDate" >
				</div>
			</div>

<%--			<div class="layui-form-item">--%>
<%--				<label class="layui-form-label">结束日期</label>--%>
<%--				<div class="layui-input-block">--%>
<%--					<input type="text" name="endDate" id="endDate" required lay-verify="required" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">--%>
<%--				</div>--%>
<%--			</div>--%>

			<div class="layui-form-item">
				<label class="layui-form-label"></label>
				<div class="layui-input-block">
					<a class="layui-btn layui-btn-xs" lay-submit lay-filter="submitBtn">提交</a>
					<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel">取消</a>
				</div>
			</div>
		</form>
	</div>
	<div id="dlg2" style="padding-top: 0px; padding-right: 0px;display:none;">
		<table class="layui-hide" id="user" style="margin: 0px;" lay-filter="user"></table>
	</div>
	<div id="dlg1" style="padding-top: 10px; padding-right: 30px;display:none;">
		<form class="layui-form" id="frm2" lay-filter="frm2">
			<input type="text" style="display:none;" id="id" name="id"/>
			<div class="layui-form-item">
				<label class="layui-form-label">开始日期</label>
				<div class="layui-input-block">
					<input type="text" name="beginDate" id="beginDate1" required lay-verify="required" readonly="readonly" placeholder="请输入开始日期" autocomplete="off" class="layui-input test-item">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">结束日期</label>
				<div class="layui-input-block">
					<input type="text" name="endDate" id="endDate1" required lay-verify="required" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">考试时长(分)</label>
				<div class="layui-input-block">
					<input type="text" name="examtime" required lay-verify="number" placeholder="请输入考试时长" autocomplete="off" class="layui-input">
				</div>
			</div>

			<div class="layui-form-item" style="margin-bottom:0px;margin-top:5px;">
				<center>
					<a class="layui-btn layui-btn-xs" lay-submit lay-filter="submit">提交</a>
					<a class="layui-btn layui-btn-xs layui-btn-primary btn-cancel">取消</a>
				</center>
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
	<script src="../js/train/index.js?times=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>
