<%@page import="com.netcity.module.entity.ConfigEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>试卷配置</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style type="text/css">
.layui-form-item {
	margin: 5px 0px;
}
</style>
</head>
<body style="padding-top: 50px;">
	<form class="layui-form">
		<!-- <div class="layui-form-item">
			<label class="layui-form-label" style="width: 300px; color: red; text-align: left;"><h2>所有设置项均为整数</h2></label>
		</div> -->
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">单选题数量</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="num1" name="num1" required lay-verify="required|number" value="${sessionScope.config.num1}" />
			</div>
			<label class="layui-form-label" style="width: 120px">单选题分数</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="score1" name="score1" required lay-verify="required|number" value="${sessionScope.config.score1}" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">多选题数量</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="num2" name="num2" required lay-verify="required|number" value="${sessionScope.config.num2}" />
			</div>
			<label class="layui-form-label" style="width: 120px">多选题分数</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="score2" name="score2" required lay-verify="required|number" value="${sessionScope.config.score2}" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">判断题数量</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="num3" name="num3" required lay-verify="required|number" value="${sessionScope.config.num3}" />
			</div>
			<label class="layui-form-label" style="width: 120px">判断题分数</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="score3" name="score3" required lay-verify="required|number" value="${sessionScope.config.score3}" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">填空题数量</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="num4" name="num4" required lay-verify="required|number" value="${sessionScope.config.num4}" />
			</div>
			<label class="layui-form-label" style="width: 120px">填空题分数</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="score4" name="score4" required lay-verify="required|number" value="${sessionScope.config.score4}" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">简答题数量</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="num5" name="num5" required lay-verify="required|number" value="${sessionScope.config.num5}" />
			</div>
			<label class="layui-form-label" style="width: 120px">简答题分数</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="score5" name="score5" required lay-verify="required|number" value="${sessionScope.config.score5}" />
			</div>
		</div>
		<%-- <div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">理论考核题分数</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="score6" name="score6" required lay-verify="required|number" value="${sessionScope.config.score6}" />
			</div>
		</div> --%>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px">初审百分比%</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="firstRate" name="firstRate" required lay-verify="required|number" value="${sessionScope.config.firstRate}" />
			</div>
			<label class="layui-form-label" style="width: 120px">复审百分比%</label>
			<div class="layui-input-inline" style="width: 300px">
				<input class="layui-input" id="secondRate" name="secondRate" required lay-verify="required|number" value="${sessionScope.config.secondRate}" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 120px"></label>
			<div class="layui-input-inline" style="width: 300px">
				<button class="layui-btn layui-btn-xs" lay-submit lay-filter="formSubmit">立即提交</button>
			</div>
		</div>
	</form>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script>
		layui.config({
			base:'../js/'
			})
		layui.use([ 'element', 'form', 'commons' ], function() {
			var form = layui.form, element = layui.element, $ = layui.jquery, commons = layui.commons();
			form.on('submit(formSubmit)', function(data) {
				var data = JSON.stringify(data.field);
				$.ajax(
					{
						type : "post",
						url : 'update.do',
						dateType : "json",
						contentType : 'application/json',
						data : data,
						success : function(result) {
							if(result.flag){
								commons.showInfo(result.message) 
							}else{
								layer.msg(result.message);
							}
						}
					});
				return false;
			});
		});
	</script>
</body>
</html>