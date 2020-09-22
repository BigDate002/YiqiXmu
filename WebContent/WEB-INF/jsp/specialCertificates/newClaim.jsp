<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新申领</title>
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
	/*#dlg .layui-form-label{*/
	/*	width:80px;*/
	/*}*/
	/*#dlg .layui-input-block{*/
	/*	margin-left:70px;*/
	/*	margin-right:10px;*/
	/*	min-height: 30px;*/
	/*}*/
	/*#dlg .layui-input-inline{*/
	/*	width:120px;*/
	/*	min-height: 30px;*/
	/*}*/
	/*#dlg .label-height{*/
	/*	padding-left: 30px;*/
	/*}*/
	.uploader-list {
		margin-left: -15px;
	}
	.uploader-list .info {
		position: relative;
		margin-top: -25px;
		background-color: black;
		color: white;
		filter: alpha(Opacity=80);
		-moz-opacity: 0.5;
		opacity: 0.5;
		width: 100px;
		height: 25px;
		text-align: center;
		display: none;
	}

	.uploader-list .handle {
		position: relative;
		background-color: black;
		color: white;
		filter: alpha(Opacity=80);
		-moz-opacity: 0.5;
		opacity: 0.5;
		width: 100px;
		text-align: right;
		height: 18px;
		margin-bottom: -18px;
		display: none;
	}
	.uploader-list .handle i {
		margin-right: 5px;
	}
	.uploader-list .handle i:hover {
		cursor: pointer;
	}
	.uploader-list .file-iteme {
		margin: 12px 0 0 15px;
		padding: 1px;
		float: left;
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
					<input type="text" name="username" placeholder="请输入姓名" autocomplete="off" class="layui-input" />
				</div>
				<label class="layui-form-label">证件名称</label>
				<div class="layui-input-inline">
					<input type="text" name="certificateName" placeholder="请输入证件名称" autocomplete="off" class="layui-input" />
				</div>

				<label class="layui-form-label">状态</label>
				<div class="layui-input-inline">
					<select name="status" class="layui-input">
						<option value>请选择</option>
						<option value="0">待复审</option>
						<option value="1">复审中</option>
						<option value="2">正常</option>
						<option value="3">过期</option>
					</select>
				</div>

			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" style="">提报时间</label>
				<div class="layui-input-inline">
					<input type="text" name="beginDate" id="beginDate" readonly="readonly" placeholder="请输入开始日期" autocomplete="off" class="layui-input test-item">
				</div>
				<label class="layui-form-label" style="width:20px">至</label>
				<div class="layui-input-inline">
					<input type="text" name="endDate" id="endDate" readonly="readonly" placeholder="请输入结束日期" autocomplete="off" class="layui-input test-item">
				</div>

				<input type="hidden" name="type" value="0">
				<div class="layui-input-inline">
					<shiro:hasPermission name="staff:query">
						<a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
						<a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
					</shiro:hasPermission>
				</div>
			</div>

			<shiro:hasPermission name="staff:create">
				<a class="layui-btn layui-btn-xs" lay-event="create">证件新增</a>
			</shiro:hasPermission>
			<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
			<shiro:hasPermission name="staff:exportData">
			<a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
			</shiro:hasPermission>

			<a class="layui-btn layui-btn-xs" lay-submit lay-filter="downloadPicturesInBulk">批量下载图片</a>

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

	<div id="dlg" style="padding-top: 5px;display:none;">
		<form class="layui-form" lay-filter="frm1">

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">部门</label>
					<div class="layui-input-inline">
						<select id="department" name="departmentId" lay-filter="department" lay-verify="required">
						</select>
					</div>
					<label class="layui-form-label">科室/车间</label>
					<div class="layui-input-inline">
						<select id="workShop" name="workShopId" lay-filter="workShop" lay-verify="required">
						</select>
					</div>
				</div>
            </div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">班组</label>
					<div class="layui-input-inline">
						<select id="deptId" name="deptId" lay-filter="deptId">
						</select>
					</div>
					<label class="layui-form-label">证件名称</label>
					<div class="layui-input-inline">
						<input type="text" id="certificateName" name="certificateName" lay-verify="required|title" autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">办理机构</label>
				<div class="layui-input-inline">
					<select id="handlingAgency" lay-verify="required" name="handlingAgency" >
						<option value>请选择</option>
						<option value="0" >公司办理</option>
						<option value="1" >个人办理</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label label-height">身份证正/反面</label>
				<div class="layui-input-block">
					<button type="button" class="layui-btn layui-btn-xs" id="idCard">
						<i class="layui-icon">&#xe67c;</i>上传照片
					</button>
					<div class="layui-upload-list uploader-list" style="overflow: auto;" id="uploader-idCardList"></div>
				</div>
				<input type="hidden" id="idCardHidden" name="idCardHidden">
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">学历证明</label>
				<div class="layui-input-block">
					<button type="button" class="layui-btn layui-btn-xs" id="educationCertificate">
						<i class="layui-icon">&#xe67c;</i>上传照片
					</button>
					<div class="layui-upload-list uploader-list" style="overflow: auto;" id="uploader-educationCertificateList"></div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">其他材料</label>
				<div class="layui-input-block">
					<button type="button" class="layui-btn layui-btn-xs" id="other">
						<i class="layui-icon">&#xe67c;</i>上传照片
					</button>
					<div class="layui-upload-list uploader-list" style="overflow: auto;" id="uploader-otherList"></div>
				</div>
			</div>
			<input type="hidden" name="type" value="0">
			<br>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn layui-btn-sm" lay-submit lay-filter="tijiao">提交</button>
					<button class="layui-btn layui-btn-sm layui-btn-primary btn-cancel">取消</button>
				</div>
			</div>



		</form>
	</div>
	<div id="dlgDetail" style="padding-top: 5px;display:none;">
		<form class="layui-form" lay-filter="frm1Detail">

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">部门</label>
					<div class="layui-input-inline">

						<input type="text" name="department" disabled class="layui-input">

					</div>
					<label class="layui-form-label">科室/车间</label>
					<div class="layui-input-inline">
						<input type="text" name="workShop" disabled class="layui-input">
					</div>
				</div>
            </div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">班组</label>
					<div class="layui-input-inline">
						<input type="text" name="dept" disabled class="layui-input">
					</div>
					<label class="layui-form-label">证件名称</label>
					<div class="layui-input-inline">
						<input type="text" name="certificateName" disabled class="layui-input">
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">办理机构</label>
				<div class="layui-input-inline">
					<input type="text" name="handlingAgency" disabled class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label label-height">身份证正/反面</label>
				<div class="layui-input-block">

					<div class="layui-upload-list">
						<div id="idCardImgDetail"></div>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">学历证明</label>
				<div class="layui-input-block">

					<div class="layui-upload-list">
						<p id="educationCertificateImgDetail"></p>
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">其他材料</label>
				<div class="layui-input-block">

					<div class="layui-upload-list">
						<p id="otherImgDetail"></p>
					</div>
				</div>
			</div>
		</form>
	</div>
	<form id="xiazai" method="post" style="display:none;"></form>
	<form id="downloadPictures" method="post" style="display:none;"></form>
	<iframe class="layui-hide" id="download"></iframe>
	<script>
		var basePath = '../';
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/specialCertificates/newClaim.js?t=<%=System.currentTimeMillis()%>"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>
