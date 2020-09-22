<%@ page import="com.netcity.module.entity.UserEntity"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>我的信息</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
	.layui-table{
		color:#333;
	}
</style>
</head>
<body style="min-height: 100%; padding-left: 5px; margin: 0px;">
	<div class="layui-table-box">
		<div class="layui-table-body layui-table-main">
			<table cellspacing="0" cellpadding="0" border="0" class="layui-table">
				<tbody>
					<tr>
						<td align="right" style="width:80px;">
							<div class="layui-table-cell">工号</div>
						</td>
						<td align="left"><div class="layui-table-cell">${user.usercode}</div></td>
					</tr>
					<tr>
						<td align="right" style="width:80px;">
							<div class="layui-table-cell">姓名</div>
						</td>
						<td align="left"><div class="layui-table-cell">${user.name }</div></td>
					</tr>
					<tr>
						<td align="right" style="width:80px;">
							<div class="layui-table-cell">性别</div>
						</td>
						<td align="left"><div class="layui-table-cell">${user.sex==1?"男":"女" }</div></td>
					</tr>
					<tr>
						<td align="right" style="width:80px;">
							<div class="layui-table-cell">部门</div>
						</td>
						<td align="left"><div class="layui-table-cell">${user.department }</div></td>
					</tr>
					<tr>
						<td align="right" style="width:80px;">
							<div class="layui-table-cell">科室/车间</div>
						</td>
						<td align="left"><div class="layui-table-cell">${user.workShop }</div></td>
					</tr>
					<tr>
						<td align="right" style="width:80px;">
							<div class="layui-table-cell">班组</div>
						</td>
						<td align="left"><div class="layui-table-cell">${user.dept }</div></td>
					</tr>
					<tr>
						<td align="right" style="width:80px;">
							<div class="layui-table-cell">备注</div>
						</td>
						<td align="left"><div class="layui-table-cell">${user.remark }</div></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script>
		layui.config(
			{
				base : '../js/',
				version : new Date().getTime()
			});
		layui.use([ 'element', 'form', 'commons' ], function() {
			var element = layui.element;
			var $ = layui.jquery;
			var form = layui.form;
		});
	</script>
</body>
</html>