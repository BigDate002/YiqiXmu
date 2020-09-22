<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
.cc{
	border-top:0px;
	border-left:0px;
	border-right:0px;
	border-bottom:1px solid #cccccc;
	width:60px;
	display:inline;
}
.aaa{
	padding:5px 15px;
}
.abc{
	margin:5px 15px;
}
.xx{
	padding-top:5px;
	padding-left:10px;
}
.layui-form-radio{
	padding:0px;margin:0px;
}
</style>
</head>
<body style="min-height: 100%; padding: 10px; margin: 0px;">
	<form class="layui-form" id="form" method="post">
	<c:forEach var="x" items="${papers}">
		<c:set var="total" value="${total+x.score1 }"></c:set>
		<c:set var="totalScore" value="${totalScore+x.score }"></c:set>
	</c:forEach>
	<div style="background-color: #F6F6E6;width: 300px;">满分:${totalScore } 考试时常:${exam.examtime }分钟
	 剩余时间<label id="time"></label></div>
	<div class="aaa">
		<h4>一、填空题</h4>
		<div class="abc">
			<c:forEach var="x" items="${papers}">
	          <c:if test="${x.question.type =='填空题' }">
	          <h5 class="tiankong">${index }.${x.question.content }
	            <input class="layui-input cc" type="text" name="${x.id }"/>(${ x.score}分)</h5>
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
	          	<c:forEach var="s" items="${x.question.selections.split('\\\\|')}">	
          			<h5 class="xx"><input type="radio" value="${s.split('\\.')[0]}" title="${s }" name=${x.id } /></h5>
          		</c:forEach>
	          	<c:set var="index" value="${index+1 }"></c:set>
	          </c:if>
		    </c:forEach>
		</div>
		<h4>三、多选题</h4>
		<div class="abc">
			<c:set var="index" value="1"></c:set>
			<c:forEach var="x" items="${papers}">
	            <c:if test="${x.question.type =='多选题' }">
	            <h5>${index }.${x.question.content }(${ x.score}分)</h5>
	          	<c:forEach var="s" items="${x.question.selections.split('\\\\|')}">	
          			<h5 class="xx"><input type="checkbox" value="${s.split('\\.')[0]}" name=${x.id } title="${s }" lay-skin="primary"/></h5>
          		</c:forEach>
	          	<c:set var="index" value="${index+1 }"></c:set>
	          </c:if>
		    </c:forEach>
		</div>
		<h4>四、判断题</h4>
		<div class="abc">
			<c:set var="index" value="1"></c:set>
			<c:forEach var="x" items="${papers}">
	          <c:if test="${x.question.type =='判断题' }">
	          <h5>${index }.${x.question.content }(${ x.score}分)</h5>
	          	<c:forEach var="s" items="${x.question.selections.split('\\\\|')}">	
          			<h5 class="xx"><input type="radio" value="${s }" name=${x.id } title="${s }" /></h5>
          		</c:forEach>
	          	<c:set var="index" value="${index+1 }"></c:set>
	          </c:if>
		    </c:forEach>
		</div>   
		<h4>五、简答题</h4>
		<div class="abc">
			<c:set var="index" value="1"></c:set>
			<c:forEach var="x" items="${papers}">
	          <c:if test="${x.question.type =='简答题' }">
	          <h5>${index }.${x.question.content }(${ x.score}分)</h5>
	          	<h5 class="xx">
	          		<textarea name="${x.id }" class="layui-textarea"></textarea>
	          	</h5>
	          	<c:set var="index" value="${index+1 }"></c:set>
	          </c:if>
		    </c:forEach>
		</div>
		<c:if test="${practice!=null}"> 
		<h4>理论考核项目</h4>
		<div class="abc">
			<c:set var="index" value="1"></c:set>
			<c:forEach var="x" items="${papers}">
	          <c:if test="${x.question.type =='理论考核项目' }">
	          <h5>${index }.${x.question.content }</h5>
	          	<h5 class="xx">
	          		<textarea name="${x.id }" class="layui-textarea"></textarea>
	          	</h5>
	          	<c:set var="index" value="${index+1 }"></c:set>
	          </c:if>
		    </c:forEach>
		</div>
		</c:if>    
	    <a id="submit" href="javascript:void(0);" lay-submit lay-filter="formSubmit" class="layui-btn layui-btn-xs">我要交卷</a>
	</div>
	</form>
	<script>
		var basePath = '../';
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
	<script>
	layui.config(
			{
				base : basePath + 'js/',
				version : new Date().getTime()
			});
		layui.use(['element','form','commons'],function(){
			var element = layui.element;
			var $ = layui.jquery;
			var form = layui.form;
			var commons = layui.commons();
			var key = 'time-test-${exam.id}';
			var time = localStorage.getItem(key);
			if(time==null){
				time = parseInt('${exam.examtime}')*60;
			}
			function setText(){
				var min = parseInt(time/60);
				var second = time%60;
				$('#time').html(min+'分'+second+'秒');
				localStorage.setItem(key,time);
				if(time>0){
					time--;
				}
			}
			setText();
			if(time<=0){
				layer.msg('考试时间到')
				$('input').attr('disabled',true);
				$('textarea').attr('disabled',true);
				return;
			}
			window.setInterval(setText,1000);
			window.setTimeout(function(){
				$('input').attr('disabled',true);
				$('textarea').attr('disabled',true);
				layer.msg('考试时间到,自动提交');
				$('#submit').click();
			},time*1000);
			form.on('submit(formSubmit)',function(obj){
				for(var key in obj.field){
					if($('input:checkbox[name='+key+']:checked').length>0){
						var arr = new Array();
						$('input:checkbox[name='+key+']:checked').each(function(i){
							arr[i] = $(this).val();
						});
						 obj.field[key] = arr.join("");
					}
				}
				$('.tiankong').each(function(index){
					var arr = new Array();
					var name = '';
					$(this).children('input').each(function(i){
						arr[i] = $(this).val();
						name = $(this).attr('name');
					});
					obj.field[name] = arr.join(",");
				});
				obj.field.code = '${exam.code}';
				$.ajax({
					  type:'post', 
					  url:'savePaper.do', 
					  dateType:"json", 
					  contentType : 'application/json', 
					  data:JSON.stringify(obj.field), 
					  success:function(result){
						if(result.flag){
							commons.showInfo(result.message);
							top.window.active.tabChangeAndRefresh('${param.tab}');
							top.window.active.tabDelete('test1');
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