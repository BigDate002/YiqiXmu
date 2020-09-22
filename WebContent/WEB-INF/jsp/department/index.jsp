 <%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="com.netcity.module.entity.UserEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>组织列表</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
	#toolbar .layui-btn{
		vertical-align: top;
	} 
</style>
</head>
<body style="min-height:100%;padding:0px;margin:0px;">
	<script type="text/html"  id="toolbar">
			<shiro:hasPermission name="department:create">
			<a class="layui-btn layui-btn-xs" lay-event="add">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="department:importData">
			<a class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
			<a class="layui-btn layui-btn-xs" style="margin-left:10px;" lay-event="downloadfile" >导入模板下载</a>
			</shiro:hasPermission>
			<a class="layui-btn layui-btn-xs" lay-event="query" style="float:right;">查询</a>
			<input class="layui-input" id="query" placeholder="请输入组织名称" style="width:200px;display:inline;float:right;" />
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
		{{#if(d.code.length>4){}}
		<shiro:hasPermission name="department:update">
  		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="department:delete">
			{{#if(d.state==0){}}	
  			<a class="layui-btn layui-btn-xs"  lay-event="del">禁用</a>
			{{#}}}
			{{#if(d.state==1){}}	
  			<a class="layui-btn layui-btn-xs" lay-event="enable">启用</a>
			{{#}}}
		</shiro:hasPermission>
		{{#}}}

		<%
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if("admin".equals(user.getUsercode())){%>
			<a class="layui-btn layui-btn-xs" lay-event="deleteReal">删除</a>
		<%}%>
	</script>
	
	<div id="dlg1" style="display:none;padding:10px">
		<form action="#" method="post" class="layui-form"  lay-filter="formDemo">
			<div class="layui-form-item">
    			<label class="layui-form-label">上级组织<input type="text" style="display: none;" name="id" /></label>
				<div class="layui-input-block">
					<input type="text" style="display:none;" name="parentId" id="parentId">
					<input type="text" style="display:none;" name="level" id="level">
					<input readonly="readonly" class="layui-input" id="parentName" name="parentName" required lay-verify="required" lay-search>
				</div>
			</div>
			<div class="layui-form-item">
    			<label class="layui-form-label">组织名称</label>
				<div class="layui-input-block">
					<input type="text" name="name" required  lay-verify="required" placeholder="请输入组织名称" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
    			<label class="layui-form-label">排序</label>
				<div class="layui-input-block">
					<input type="text" name="showOrder" required  lay-verify="number" value="0" placeholder="请输入排序" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn layui-btn-xs" lay-submit lay-filter="formSubmit">立即提交</button>
			      <button type="button" class="layui-btn layui-btn-xs layui-btn-primary btn-cancel" >取消</button>
			    </div>
			  </div>
		</form>
	</div>
	<iframe class="layui-hide" id="download"></iframe>
	<form id="xiazai" method="post" style="display: none;"></form>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/easyui/jquery.easyui.min.js"></script>
	<script>
		 layui.config({
			base:'../js/'
		 	,version: '<%=System.currentTimeMillis()%>' //为了更新 js 缓存，可忽略
		 });
		 function extra_data(input,data){
				var item=[];
				$.each(data,function(k,v){
					item.push('<input type="hidden" name="'+k+'" value="'+v+'">');
				})
				$(input).after(item.join(''));
			}
		layui.use([ 'laypage', 'layer', 'table', 'element' ,'form', 'combo','commons','rest', 'upload'],
			function() {
				var laypage = layui.laypage
				, layer = layui.layer
				, table = layui.table
				, element = layui.element
				, $ = layui.jquery
				, combo = layui.combo()
				, commons = layui.commons()
				, rest = layui.rest()
				, upload = layui.upload
				, form = layui.form;
				var index1 = layer.load(1, {
					shade : [ 0.5, '#ccc' ]
					//0.1透明度的白色背景
				});
				var f = true;
				table.render({
					id : 'userTable',
					elem : '#demo',
					height : 'full-0',
					method : 'post',
					url : 'queryPage.do',
					title : '组织列表',
					limit : 10,
					limits : [ 10, 15, 20, 30, 50, 100 ],
					page : {
						layout : [ 'limit', 'prev', 'page', 'next','skip', 'count' ],
						groups : 5
					},
					toolbar : '#toolbar'
					,defaultToolbar : []
					,totalRow : false
					,cols : [ [
					{type : 'checkbox', fixed : 'left'},
					{
						type: 'numbers', 
						title: '序号',
						align : 'center',
						width : 60
					}, 
					{field : 'level', title : '级别', width : 60 , align : 'center'},
					{field : 'parentName', title : '上级组织', width : 120, align : 'center'},
					{field : 'code', title : '编码', width : 150, align : 'center'},
					{field : 'name', title : '名称', width : 200, align : 'center'},
					{field : 'showOrder', title : '排序', width : 80, align : 'center'}, 
					{field : 'state', title : '状态', width : 70,templet:function(d){return d.state==0?'正常':'禁用'}, align : 'center'}, 
					{title : '操作', fixed : 'right', width : 220, toolbar : '#barDemo', align:'center'}
					] ],
					done : function(res,curr,count) {
						layer.closeAll();
						$('#query').val(name);
						if(f){
							upload.render(
								{
									elem : $('[lay-event=importExcel]'),
									url : 'importData.do',
									accept : 'file',
									field : 'filename',
									acceptMime : '.xlsx',
									exts : 'xlsx',
									multiple : true,
									before : function(input){
										var id = top.selectNode==null?0:top.selectNode.id;
										var data = {"id":id};
										//layer.load(); //上传loading
										this.data=data;
									},
									done : function(res) {
										if (res.flag) {
											commons.showInfo(res.message);
											reloadTable();
											return;
										}
										layer.msg(res.message);
									}
								});
								f = false;
							}
					}
				});
				//监听提交
				form.on('submit(formSubmit)', function(data){
					return rest.formSubmit(data);
				});
				$('.btn-cancel').click(function(e){
				  layer.close(rest.config.idx);
				});
			var name = '';
			//表刷新方法
	        window.reloadTable = function (param) {
	        	layer.load(1,
						{
							shade : [ 0.5, '#ccc' ]
						});
				f = true;
				var where = {};
				where.name = name = $('#query').val();
				if(typeof(param)=='object'){
					$.extend(true, where, param);
				}
	            table.reload("userTable", {
	                where: where
	            });
	        }; 
		//监听头工具栏事件
		table.on(
			'toolbar(test)',
			function(obj) {
				var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; //获取选中的数据
				switch (obj.event) {
				case 'downloadfile':
				$('#download').attr('src', 'downloadTemplate.do');
					break;
				case 'query':
					reloadTable();
					break;
				case 'add':
					if(top.selectNode&&top.selectNode!=null){
						
					}else{
						layer.msg('请选择上级组织结构');
						return ;
					}
					if(top.selectNode.level=='四级'){
						return ;
					}
					$("#code").attr('readonly',false);
					$('form')[0].reset();
					$('#parentId').val(top.selectNode.id);
					$('#parentName').val(top.selectNode.name);
					var arr = ['一级','二级','三级','四级'];
					$('#level').val(arr[arr.indexOf(top.selectNode.level)+1]);
					rest.config.idx = layer.open({
						type: 1,
				    	content: $('#dlg1'),
				    	title:'添加组织结构',
				    	area:['500px','330px']
						,cancel:function(){
							$('#dlg1').hide();
						}
				    });
					break;
				case 'update':
					if (data.length === 0) {
						layer.msg('请选择一行');
					} else if (data.length > 1) {
						layer.msg('只能同时编辑一个');
					} else {
						form.val(data);
						idx = layer.open({
							type: 1,
					    	content: $('#dlg1'),
					    	title:'编辑组织结构',
					    	area:['500px','330px']
							,cancel:function(){
								$('#dlg1').hide();
							}
					    });
					}
					break;
				case 'delete':
					var checkStatus = table.checkStatus('userTable'); //idTest 即为基础参数 id 对应的值
					if(checkStatus.data.length==0){
						layer.msg('请选择数据');
						return;
					}
					var ids = checkStatus.data.map(function(item){
						return item.id
					}).toString();
					rest.deleteMethod(ids);
					break;
				}
			});
		//监听行工具事件
		table.on('tool(test)', function(obj) {
			if (obj === false)
				return;
			var data = obj.data
			, layEvent = obj.event;
			if (layEvent === 'detail') {
				layer.open({
					title:'查看组织结构',
					skin : 'layui-layer-molv',
					type:1,
					area:['400px','320px'],
					closeBtn:1,
					shade:0.5,
					content: '<form class="layui-form">'
							+'<div class="layui-form-item"><label class="layui-form-label">上级</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+(data.parentName===null?'':data.parentName)+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">等级</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.level+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">编码</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.code+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">名称</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.name+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">排序</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.showOrder+'</div></div>'
							+'</form>'
				});
			} else if (layEvent === 'del') {
				console.log(rest)
				rest.config.deleteUrl='delete1.do';
				rest.delFun('确定禁用选中数据？',{ids:data.id});
			} else if (layEvent === 'enable') {
				rest.config.deleteUrl='delete.do';
				rest.delFun('确定启用选中数据？',{ids:data.id});
			} else if (layEvent === 'edit') {
				form.val('formDemo',data);
				$("#code").attr('readonly',true);
				rest.config.idx = layer.open({
					type: 1,
			    	content: $('#dlg1'),
			    	title:'编辑组织结构',
			    	area:['500px','330px']
			    });
			} else if (layEvent === 'deleteReal') {
				rest.config.deleteUrl='deleteReal.do';
				rest.delFun('确定删除选中数据？',{ids:data.id});
			}
		});
	});
	</script>
</body>
</html>