<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>详情</title>
<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all">
</head>
<body style="min-height: 100%; padding: 10px; margin: 0px;background: url(images/logn.jpg) no-repeat fixed right bottom;">
	<iframe class="layui-hide" id="download"></iframe>
	<center>
		<h2>${detail.title }</h2>
		<c:if test="${fn:length(detail.files)>0}"><h4>附件:
		<c:forEach items="${detail.files }" var="x">
		<button onclick="downloadFile(${x.id})" class="layui-btn layui-btn-xs layui-btn-primary layui-bg-orange">${x.fileName }</button>
		</c:forEach>
		</h4>
		</c:if>
	</center>
	<div>
		${fn:replace(detail.content,"..//","")}
	</div>
	<script>
		var basePath = '';
	</script>
	<script src="js/json2.js"></script>
	<script src="plugins/layui/layui.js"></script>
	<script src="plugins/jquery/jquery-1.9.1.js"></script>
	<script src="plugins/easyui/jquery.easyui.min.js"></script>
	<script>
	function downloadFile(id){
		$("#download").attr("src",'news/downloadFile.do?id='+id);
	}
	layui.config(
			{
				base : basePath + 'js/',
				version : new Date().getTime()
			});
		layui.use(['element'],function(){
			var element = layui.element;
		});
	</script>
</body>
</html>