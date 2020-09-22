<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setAttribute("index", 1);
	request.setAttribute("total", 0);
	request.setAttribute("totalScore", 0);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>测试详情</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
.aaa {
	padding: 5px 15px;
}

.abc {
	margin: 5px 15px;
}
.laytable-cell-1-0-2 {
	width: 420px;
}

.laytable-cell-1-0-3 {
	width: 80px;
}
</style>
</head>
<body style="min-height: 100%; padding: 0px; margin: 0px;">
	<c:forEach var="x" items="${papers}">
		<c:if test="${x.question.type!='技能考核项目' &&x.question.type!='理论考核项目' }">
		<c:set var="total" value="${total+x.score1 }"></c:set>
		</c:if>
		<c:set var="totalScore" value="${totalScore+x.score }"></c:set>
	</c:forEach>
	<div class="layui-tab" id="tabs" lay-filter="tab">
		<ul class="layui-tab-title">
			<li class="layui-this">理论考核</li>
			<c:if test="${practice!=null}">
				<li>实践考核</li>
			</c:if>
		</ul>
		<div class="layui-tab-content">
			<div id="xyz" class="layui-tab-item layui-show">
				<div id="bishi" class="aaa" style="overflow-x:hidden;overflow-y:scroll;height:350px;">
				<div style="background-color: #13e4e5;">满分${totalScore } 分 合格分数${score } 分 总共得分${total } 分</div>
				<c:forEach var="x" items="${papers}">
					<c:set var="total" value="${total+x.score1 }"></c:set>
					<c:set var="totalScore" value="${totalScore+x.score }"></c:set>
				</c:forEach>
				<div class="aaa">
					<h4>一、填空题</h4>
					<div class="abc">
						<c:forEach var="x" items="${papers}">
							<c:if test="${x.question.type =='填空题' }">
								<h5>${index }.${x.question.content }(${ x.score}分)</h5>
								<h5>&nbsp;&nbsp;&nbsp;正确答案:${x.question.answer }</h5>
								<h5>&nbsp;&nbsp;&nbsp;答案:${x.answer }</h5>
								<h5>&nbsp;&nbsp;&nbsp;得分:${x.score1 }</h5>
								<c:set var="index" value="${index+1 }"></c:set>
							</c:if>
						</c:forEach>
					</div>
					<h4>二、单选题</h4>
					<div class="abc">
						<c:set var="index" value="1"></c:set>
						<c:forEach var="x" items="${papers}">
							<c:if test="${x.question.type =='单选题' }">
								<h5>${index }.${x.question.content }(${ x.score}分)</h5>
								<h5>&nbsp;&nbsp;&nbsp;选项${x.question.selections.replaceAll('\\|','&nbsp;&nbsp;') }</h5>
								<h5>&nbsp;&nbsp;&nbsp;正确答案:${x.question.answer }</h5>
								<h5>&nbsp;&nbsp;&nbsp;答案:${x.answer }</h5>
								<h5>&nbsp;&nbsp;&nbsp;得分:${x.score1 }</h5>
								<c:set var="index" value="${index+1 }"></c:set>
							</c:if>
						</c:forEach>
					</div>
					<h4>三、多选题</h4>
					<c:set var="index" value="1"></c:set>
					<c:forEach var="x" items="${papers}">
						<c:if test="${x.question.type =='多选题' }">
							<h5>${index }.${x.question.content }(${ x.score}分)</h5>
							<h5>&nbsp;&nbsp;&nbsp;选项${x.question.selections.replaceAll('\\|','&nbsp;&nbsp;') }</h5>
							<h5>&nbsp;&nbsp;&nbsp;正确答案:${x.question.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;答案:${x.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;得分:${x.score1 }</h5>
							<c:set var="index" value="${index+1 }"></c:set>
						</c:if>
					</c:forEach>
					<h4>四、判断题</h4>
					<c:set var="index" value="1"></c:set>
					<c:forEach var="x" items="${papers}">
						<c:if test="${x.question.type =='判断题' }">
							<h5>${index }.${x.question.content }(${ x.score}分)</h5>
							<h5>&nbsp;&nbsp;&nbsp;选项${x.question.selections.replaceAll('\\|','&nbsp;&nbsp;') }</h5>
							<h5>&nbsp;&nbsp;&nbsp;正确答案:${x.question.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;答案:${x.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;得分:${x.score1 }</h5>
							<c:set var="index" value="${index+1 }"></c:set>
						</c:if>
					</c:forEach>
					<h4>五、简答题</h4>
					<c:set var="index" value="1"></c:set>
					<c:forEach var="x" items="${papers}">
						<c:if test="${x.question.type =='简答题' }">
							<h5>${index }.${x.question.content }(${ x.score}分)</h5>
							<h5>&nbsp;&nbsp;&nbsp;参考答案:${x.question.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;答案:${x.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;得分:${x.score1 }</h5>
							<c:set var="index" value="${index+1 }"></c:set>
						</c:if>
					</c:forEach>
				</div>
				</div>
			</div>
			<div class="layui-tab-item">
				<c:set var="index" value="1"></c:set>
					<c:forEach var="x" items="${papers}">
						<c:if test="${x.question.type =='理论考核项目' }">
							<h5>${index }.${x.question.content }</h5>
							<h5>&nbsp;&nbsp;&nbsp;参考答案:${x.question.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;答案:${x.answer }</h5>
							<h5>&nbsp;&nbsp;&nbsp;是否合格:${x.score1=='1'?'是':'否' }</h5>
							<c:set var="index" value="${index+1 }"></c:set>
						</c:if>
					</c:forEach>
				<form class="layui-form">
				<div class="layui-form layui-border-box layui-table-view" lay-filter="LAY-table-1" lay-id="userTable" style="width:503px;height:auto;">
					<div class="layui-table-box">
						<div class="layui-table-header">
							<table cellspacing="0" cellpadding="0" border="0" class="layui-table">
								<thead>
									<tr>
										<th data-field="username" data-key="1-0-2" class=""><div class="layui-table-cell laytable-cell-1-0-2" align="center">
												<span>考核项目</span>
											</div></th>
										<th data-field="courseName" data-key="1-0-3" class="">
										<div class="layui-table-cell laytable-cell-1-0-3" align="center">
											<span>考核结果</span>
										</div></th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="layui-table-body layui-table-main">
							<table cellspacing="0" cellpadding="0" border="0" class="layui-table">
								<tbody>
									<c:forEach var="x" items="${papers}">
									<c:if test="${x.question.type =='技能考核项目' }">
									<tr>
										<td align="center"><div class="layui-table-cell laytable-cell-1-0-2">${x.question.content }</div></td>
										<td align="center">
											<div class="layui-table-cell laytable-cell-1-0-3">
												${x.score1 == 1 ? '合格' : '不合格' }
											</div>
										</td>
									</tr>
									</c:if>
									</c:forEach>
									<tr>
										<td align="center"><div class="layui-table-cell laytable-cell-1-0-2">技能矩阵图</div></td>
										<td align="center"><div class="layui-table-cell laytable-cell-1-0-3">
											<img src="../images/skill-${practice.score }.jpg"/>	
										</div></td>
									</tr>
									<tr>
										<td align="center"><div class="layui-table-cell laytable-cell-1-0-2">考核结果是否通过</div></td>
										<td align="center"><div class="layui-table-cell laytable-cell-1-0-3">
											${practice.result==1?'合格':'不合格' }	
										</div></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		var basePath = '../';
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
	<script>
		layui.use([ 'element','form' ], function() {
			var element = layui.element;
			var form = layui.form;
			form.render('checkbox');
			var height = $(window).height()-100;
			$('#bishi').height(height);
		});
	</script>
</body>
</html>