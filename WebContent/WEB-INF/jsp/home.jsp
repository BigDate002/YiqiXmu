<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>首頁</title>
<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">
<style>
.xuanzhong,.xuanzhong:hover{
	background-color:#009688;
	color:white;
}
.scro{
	width:auto;
}
.layui-tab-content {
	padding: 0px 0px;
}

body {
	line-height: 24px;
	font: 14px Helvetica Neue, Helvetica, PingFang SC, Tahoma, Arial, sans-serif;
}

.header-demo {
	height: 60px;
	line-height: 60px;
	border-bottom: none;
}

.sdemo {
<shiro:hasPermission name="32">
	padding-left: 359px;
</shiro:hasPermission>
<shiro:lacksPermission name="32">
	padding-left: 179px;
</shiro:lacksPermission>
}

.footer {
	text-align: center;
	font-weight: 300;
	color: black;
}

.layui-nav .layui-nav-item {
	width: 180px;
}

.layui-tab-title li {
	width: 105px;
}

.layui-icon {
	width: 20px;
}

.abc {
	margin-top: 24px;
	padding-left: 60px;
}
.layuibgblue{
	background-color:transparent;
	color:transparent!importmant;
}
.layui-form-switch{
	margin-top:0px;
}
</style>
</head>

<body>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header  header header-demo" style="background-color: #dfdfdf; padding-left: 5px;">
			<a class="logo">
			<img width="80" src="images/logo.png" style="padding-bottom: 10px;" />
			</a>
			<span style="font-size: 25px; font-weight:bolder;color: #009688; font-family: STXinwei;">人员资质管理平台
			</span>
			<ul class="layui-nav layui-layout-right" lay-filter="header-right">
				<form class="layui-form">
					<shiro:hasPermission name="32">
					<input type="checkbox" name="switch" lay-skin="switch" lay-text="关闭组织结构|开启组织结构" checked>
					</shiro:hasPermission>
					<a href="javascript:;" class="layui-btn layui-btn-xs layui-btn-primary">
						<shiro:authenticated>
						<shiro:principal property="name" />
						(<shiro:principal property="usercode" />)
						<%=new SimpleDateFormat("yyyy年MM月dd日 EEEE").format(new Date()) %>
						</shiro:authenticated>
					</a>
					<a href="javascript:;" class="layui-btn layui-btn-xs" lay-submit lay-filter="logout">退出系统</a>
				</form>
			</ul>
		</div>
		<div class="layui-side layui-bg-cyan" style="width: 180px;">
			<div class="layui-side-scroll">
				<div id="sidebar" class="layui-nav layui-nav-tree" lay-filter="testbar"></div>
			</div>
		</div>
		<shiro:hasPermission name="32">
		<div class="layui-side" style="left: 180px; width: 180px;" id="zuzhi">
			<div class="layui-side-scroll" style="background-color: #eee">
				<div class="layui-nav layui-nav-tree" class="width:150px">
					<div class="layui-nav-item layui-nav-itemed layui-bg-cyan" style="position:fixed;">
						<a href="javascript:;">组织结构</a>
					</div>
				</div>
				<div id="dept" lay-filter="test1" style="margin-top:45px;"></div>
			</div>
		</div>
		</shiro:hasPermission>
		<div class="layui-tab layui-tab-card layui-tab-brief" style="margin: 0px;" lay-allowClose="true" id="tabs" lay-filter="tab">
			<ul class="layui-tab-title sdemo">
				<li class="layui-this"><i class="layui-icon">&#xe68e;</i>&nbsp;首页</li>
			</ul>
			<div class="layui-tab-content sdemo">
				<div class="layui-tab-item layui-show" id="indx" style="height: calc(100vh - 140px);">
					<div style="padding: 0px; background-color: #F2F2F2; height: 100%">
						<div class="layui-row">
							<div class="layui-col-md12">
								<div class="layui-card">
									<div class="layui-card-header" style="background-color:white;">
										<shiro:hasPermission name="news:create">
											<a href="javascript:;" id="newBtn" class="layui-btn layui-btn-xs" >公告管理</a>
										</shiro:hasPermission>
										<shiro:lacksPermission name="news:create">公告</shiro:lacksPermission>
									</div>
									<div class="layui-card-body" style="background-image: url(images/logn.jpg);background-repeat:no-repeat; background-position: right bottom; height:calc(100vh-140px);">
										<ol style="width:100%; padding-left: 0px;  height:calc(100vh - 210px); margin-top: 5px; overflow: hidden;" id="news">
											<c:forEach var="x" items="${news}" varStatus="userStatus">
												<li class="scro">
												<c:if test="${!x.isRead }">
													<span class="layui-badge-dot"></span>
												</c:if>
												<c:if test="${x.isRead }">
													<span class="layui-badge-dot layuibgblue"></span>
												</c:if>
												<label style="width:20px;margin-right:1px;text-align:center;display:inline-block;">
												${userStatus.count }.</label>
												<label newId="${x.id }" style="cursor:pointer;">${x.title }</label>
												</li>
											</c:forEach>
										</ol>
										<h1 style="height:30px;"></h1>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-footer footer" style="background-color: #dfdfdf;">
			版权所有:长城汽车股份有限公司 <!--Copyright&copy;<%= new Date().getYear()+1900%>,技术支持:网城软件-->
			<a href="http://beian.miit.gov.cn" target="blank">
				冀ICP备05008632号-38
			</a>,
			<a href="http://beian.miit.gov.cn" target="blank">
				冀ICP备05008632号-40
			</a>
		</div>
	</div>
	<script src="plugins/layui/layui.js?t=<%=System.currentTimeMillis() %>"></script>
	<script>
		var selectNode = null;
		layui.config({base : 'js/',version : new Date().getTime()}).use(
				['layer', 'tree', 'jquery', 'element', 'navbar','form','carousel'],
				function() {
					var $ = layui.jquery; 
					var element = layui.element;
					var navbar = layui.navbar();
					var form = layui.form;
					var layer = layui.layer;
					var carousel = layui.carousel;
					var bindMethod = function(el){
						el.bind('mouseover',function(e){
							clearInterval(timer);
						});
						el.bind('mouseout',function(e){
							if($('.scro').length*24>height-70){
								timer = window.setInterval(scrool,inter);
							}
						});
						el.click(function(e){
							var id = $(this).attr('newId');
							if(id===undefined)return;
							$(this).siblings('span').addClass('layuibgblue');
							window.open('detail_'+id+'.html','_blank');
						});
					}
					navbar.set({
						elem : $('#sidebar'),
						url : 'menulist.do',
						done : function(){
							navbar.on('click(testbar)',function(data){
								var dataid = data.elem;
								var param = data.field;
								active.tabAdd(param.href,param.id,param.title);
						        active.tabChange(param.id);
							});
						}
					});
					var speed = 1000;
					var inter = 1000;
					bindMethod($('.scro>label'));
					var scrool = function(){
						$('.scro:first').animate({'margin-top':'-20px'},speed,function(){
							var obj = $('.scro:first');
							obj.remove();
							var clon = obj.clone();
							clon.css('margin-top','0px');
							bindMethod(clon.find('label'));
							$('#news').append(clon);
							
						});
					}
					$('#newBtn').click(function(){
						active.tabAdd('news/index.html','news','公告管理');
						active.tabChange('news');
					});
					form.on('submit(logout)',function(data){
						layer.open({
							type:1,
							area:['300px','180px'],
							content:'<h3 class="abc">是否退出登录?</h3>',
							title:'提示',
							btn:['退出','取消'],
							yes:function(){
								window.location.href="logout.do";
							}
						})
						return false;
					});
					navbar.render();
					$.ajax({
						url : 'department/query.do',
						async : false,
						type : 'post',
						datatype : "application/json",
						success : function(result) {
							layui.tree({
								elem : '#dept',
								hideIcon : true,
								nodes : result.data,
								click: function(node){
									selectNode = node;
									debugger;
									var id= $('#tabs').find('li.layui-this').attr('lay-id');
									if(id===undefined)return;
									var frm = $('[data-frameid='+id+']');
									var reload =  frm[0].contentWindow.reloadTable;
									if(reload!=undefined)
									reload({departmentId:node.id});
								} 
							});
							$(".layui-tree li a").click(function() {
							    $(".layui-tree li a").removeClass("xuanzhong");
							    $(this).addClass("xuanzhong");
							});
						}
					});
					form.on('switch', function(data){
						  if(!data.elem.checked){
							  $('#zuzhi').hide();
							  $('.sdemo').css('padding-left','179px');
						  }else{
							  $('#zuzhi').show();
							  $('.sdemo').css('padding-left','359px');
						  }
						});  
					var height = $(window).height()-140;
					$('.layui-tab-close:first').hide();
					var timer;
					if($('.scro').length*24>=height-70){
						timer = window.setInterval(scrool,inter);
					}
					//触发事件
				    window.active = {
				        tabAdd: function (url,id,name) {
				        	var isData = false;
				        	$.each($('#tabs').find(".layui-tab-title li"),function () {
				        		var layid = $(this).attr("lay-id");
				                if(layid && layid == id){
				                    isData = true;
				                }
				            });
				        	if(isData)return;
				            element.tabAdd('tab',{
				                title:name,
				                content: '<iframe data-frameid="'+id+'" scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:'+height+'px;"></iframe>',
				                id:id
				            });
				        },
				        tabChange:function (id) {
				            element.tabChange('tab',id);
				        },
				        tabChangeAndRefresh:function (id) {
				            element.tabChange('tab',id);
				            var frm = $('[data-frameid='+id+']');
							frm[0].contentWindow.reloadTable();
				        },
				        tabDelete:function (id) {
				            element.tabDelete('tab',id);
				        },
				        tabRefresh:function(id) {
							var frm = $('[data-frameid='+id+']');
							$(frm).attr('src', $(frm).attr('src'))
						},
						getCurrentID:function(){
							return $('.layui-this[lay-id]').attr('lay-id');
						}
				    };
				    /* $(".layui-nav-child").find('a').on('click',function () {
				        var dataid = $(this);
				        if($(".layui-tab-title li[lay-id]").length<= 0&&dataid.attr("data-url")!='#'){
				            active.tabAdd(dataid.attr("data-url"),dataid.attr("data-id"),dataid.attr("data-title"));
				        }else{
				        	active.tabAdd(dataid.attr("data-url"),dataid.attr("data-id"),dataid.attr("data-title"));
				        }
				        active.tabChange(dataid.attr("data-id"));
				    }); */
				}
			);
	</script>
</body>

</html>