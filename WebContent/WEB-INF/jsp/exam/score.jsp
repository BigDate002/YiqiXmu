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
<title>详情</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
.aaa {
	padding: 5px 15px;
}

.cc {
	padding: 0px 20px;
}

.laytable-cell-1-0-2 {
	width: 420px;
}

.laytable-cell-1-0-3 {
	width: 150px;
}
</style>
</head>
<body style="min-height: 100%; padding: 0px; margin: 0px;">
	<c:forEach var="x" items="${papers}">
		<c:if test="${x.question.type!='技能考核项目' }">
			<c:set var="total" value="${total+x.score1 }"></c:set>
		</c:if>
		<c:set var="totalScore" value="${totalScore+x.score }"></c:set>
	</c:forEach>
	<h3 style="color: orange;">
		填空题、单选题、多选题、判断题自动评分,简答题需要手动评分。满分${totalScore},总分<span id="total">${total}</span>
	</h3>
	<form class="layui-form" lay-filter="demo">
		<input type="text" name="id" style="display:none;" value="${exam.id }" />
		<input type="text" name="id1" style="display:none;" value="${examuser.id }" />
		<div class="layui-tab" id="tabs" lay-filter="tab">
			<ul class="layui-tab-title">
				<li lay-id="1" class="layui-this">理论考核</li>
				<c:if test="${practice!=null}">
				<li lay-id="2">实践考核</li>
				</c:if>
			</ul>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
					<div id="bishi" class="aaa" style="overflow-x:hidden;overflow-y:scroll;height:350px;">
						<h4>一、填空题</h4>
						<div class="aaa">
							<c:forEach var="x" items="${papers}">
								<c:if test="${x.question.type =='填空题' }">
									<h5>${index }.&nbsp;${x.question.content }(${ x.score}分)</h5>
									<div class="cc">正确答案:${x.question.answer },答案:${x.answer },得分:${x.score1 }</div>
									<c:set var="index" value="${index+1 }"></c:set>
								</c:if>
							</c:forEach>
						</div>
						<h4>二、单选题</h4>
						<div class="aaa">
							<c:set var="index" value="1"></c:set>
							<c:forEach var="x" items="${papers}">
								<c:if test="${x.question.type =='单选题' }">
									<h5>${index }.&nbsp;${x.question.content }(${ x.score}分)</h5>
									<h5 class="cc">选项${x.question.selections.replaceAll('\\|','&nbsp;&nbsp;') }</h5>
									<h5 class="cc">正确答案:${x.question.answer },答案:${x.answer },得分:${x.score1 }</h5>
									<c:set var="index" value="${index+1 }"></c:set>
								</c:if>
							</c:forEach>
						</div>
						<h4>三、多选题</h4>
						<c:set var="index" value="1"></c:set>
						<c:forEach var="x" items="${papers}">
							<c:if test="${x.question.type =='多选题' }">
								<h5>${index }.&nbsp;${x.question.content }(${ x.score}分)</h5>
								<h5 class="cc">选项${x.question.selections.replaceAll('\\|','&nbsp;&nbsp;') }</h5>
								<h5 class="cc">正确答案:${x.question.answer },答案:${x.answer },得分:${x.score1 }</h5>
								<c:set var="index" value="${index+1 }"></c:set>
							</c:if>
						</c:forEach>
						<h4>四、判断题</h4>
						<c:set var="index" value="1"></c:set>
						<c:forEach var="x" items="${papers}">
							<c:if test="${x.question.type =='判断题' }">
								<h5>${index }.&nbsp;${x.question.content }(${ x.score}分)</h5>
								<h5 class="cc">正确答案:${x.question.answer },答案:${x.answer },得分:${x.score1 }</h5>
								<c:set var="index" value="${index+1 }"></c:set>
							</c:if>
						</c:forEach>
						<h4>五、简答题</h4>
						<c:set var="index" value="1"></c:set>
						<c:forEach var="x" items="${papers}">
							<c:if test="${x.question.type =='简答题' }">
								<h5>${index }.&nbsp;${x.question.content }(${ x.score}分)</h5>
								<h5 class="cc">正确答案:${x.question.answer }</h5>
								<h5 class="cc">答案:${x.answer }</h5>
								<h5 class="cc">
									评分:
									<div class="layui-input-inline">
										<input name="${x.id }" type="text" min="0" max="${x.score }" required lay-verify="number" class="layui-input jdt" style="width: 100px" />
									</div>
								</h5>
								<c:set var="index" value="${index+1 }"></c:set>
							</c:if>
						</c:forEach>
					</div>
				</div>
				<c:if test="${practice!=null}">
				<div class="layui-tab-item">
					<c:set var="index" value="1"></c:set>
						<c:forEach var="x" items="${papers}">
							<c:if test="${x.question.type =='理论考核项目' }">
								<h5>${index }.&nbsp;${x.question.content }</h5>
								<%-- <h5 class="cc">正确答案:${x.question.answer }</h5> --%>
								<h5 class="cc">答案:${x.answer }</h5>
								<h5 class="cc">
									是否合格:
									<div class="layui-input-inline">
										<input type="radio" name="${x.id }" value="1" title="合格">
										<input type="radio" name="${x.id }" value="0" title="不合格">
									</div>
								</h5>
								<c:set var="index" value="${index+1 }"></c:set>
							</c:if>
						</c:forEach>
					<div class="layui-form layui-border-box layui-table-view" lay-filter="LAY-table-1" lay-id="userTable" style="width: 573px; height: auto;">
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
												</div>
											</th>
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
													<input type="radio" name="${x.id }" value="1" title="合格">
													<input type="radio" name="${x.id }" value="0" title="不合格">
												</div>
											</td>
										</tr>
										</c:if>
										</c:forEach>
										<tr>
											<td align="center"><div class="layui-table-cell laytable-cell-1-0-2">技能矩阵</div></td>
											<td align="center"><div class="layui-table-cell laytable-cell-1-0-3">
													<img id="skill" src="../images/skill-0.jpg" />
													<input name="score" id="score" type="text" style="display: none;" />
												</div></td>
										</tr>
										<tr style="display:none;">
											<td align="center"><div class="layui-table-cell laytable-cell-1-0-2">考核结果是否通过</div></td>
											<td align="center"><input name="result" id="result" type="text" style="display: none;" />
												<div class="layui-table-cell laytable-cell-1-0-3" id="res"></div></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				</c:if>
			</div>
		</div>
		<a href="javascript:;" class="layui-btn layui-btn-xs" style="margin-left:60px;" lay-submit lay-filter="test">提交</a>
	</form>
	<script>
		var basePath = '../';
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script>
		layui.config({
			base:'../js/'
		})
		layui.use([ 'element', 'layer','jquery', 'form', 'commons'], function() {
			var $ = layui.jquery; 
			var element = layui.element;
			var form = layui.form;
			var layer = layui.layer;
			var commons = layui.commons();
			var height = $(window).height()-145;
			$('#bishi').height(height);
			form.on('submit(test)', function(obj) {
				var data = obj.field;
				var i = $('input:radio:checked').length;
				var n = $('input:radio').length/2;
				var m = $('input:radio:checked[value=1]').length;
				if(i!=n){
					layer.msg('考核项目尚未填写是否合格');
					element.tabChange('tab',2);
					return;
				}
				if(m<n&&data.score>2){
					layer.msg('以上项目都合格才能选择3/4及以上,请重新选择');
					return;
				}
				if(data.score==0){
					layer.msg('未选择技能矩阵图');
					element.tabChange('tab',2);
					return;
				}
				layer.load();
				$.ajax({
					  type:'post', 
					  url:'saveScore.do', 
					  dateType:"json", 
					  contentType : 'application/json', 
					  data:JSON.stringify(data), 
					  success:function(result){
						if(result.flag){
							commons.showInfo(result.message);
							top.window.active.tabChangeAndRefresh('${param.tab}');
							top.window.active.tabDelete('mask');
						}else{
							layer.msg(result.message);
						}
					  }
				  });
				return false;
			});
			
			$('#skill').click(function(e){
				var num = $('input:radio:checked[value=1]').length;
				var total = $('input:radio').length/2;
				var x = 28;
				var y = 25;
				var index = 0;
				if(e.offsetX>28&&e.offsetY<=25){
					index = 1;
				}else if(e.offsetX>28&&e.offsetY>25){
					index = 2;
				}else if(e.offsetX<=28&&e.offsetY>25){
					index = 3;
				}else if(e.offsetX<=28&&e.offsetY<=25){
					index = 4;
				}
				if(index>=3&&num<total){
					layer.msg('以上项目都合格才能选择3/4及以上');
					return;
				}
				$('#score').val(index);
				$('#result').val(index>2?1:0);
				$('#res').text(index>2?'通过':'不通过');
				var src = '../images/skill-' + index + '.jpg';
				$('#skill').attr('src', src);
			});
			
			$('.jdt').on('keydown', function(e) {
				if ((e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode >= 96 && e.keyCode <= 105)) {
					return true;
				} else {
					return false;
				}
			});
			$('.jdt').on('keyup', function(e) {
				if (e.keyCode != 13)
					return;
				$(this).val($(this).val()*1 < $(this).attr('max') ? $(this).val() : $(this).attr('max'));
				var total = parseInt('${total}');
				$('.jdt').each(function(i) {
					if ($(this).val() != '') {
						total += parseInt($(this).val());
					}
				});
				$('#total').text(total);
			});
			$('.jdt').on('blur', function(e) {
				$(this).val($(this).val()*1 < $(this).attr('max')*1 ? $(this).val() : $(this).attr('max'));
				var total = parseInt('${total}');
				$('.jdt').each(function(i) {
					if ($(this).val() != '') {
						total += parseInt($(this).val());
					}
				});
				$('#total').text(total);
			});
		});
	</script>
</body>
</html>