<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>角色列表</title>
<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<link rel="stylesheet" href="../plugins/zTree/css/zTreeStyle/zTreeStyle.css" media="all">
<style>
	.layui-table, .layui-table-view {
	    margin: 0 0!important;
	}
	.layui-table-cell{
		min-height:24px;
	}
</style>
</head>
<body style="min-height:100%;padding:0px;margin:0px;">
	<script type="text/html" id="toolbar">
		<shiro:hasPermission name="role:create">
			<a class="layui-btn layui-btn-xs" lay-event="add">添加</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="role:addColumn">
			<a class="layui-btn layui-btn-xs" lay-event="addColumn">关联栏目</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="role:addRight">
		<a class="layui-btn layui-btn-xs" lay-event="addRight">关联权限</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="role:query">
		<a class="layui-btn layui-btn-xs" lay-event="query" style="float:right;">查询</a>
		</shiro:hasPermission>
		<input class="layui-input" id="query" placeholder="请输入名称" style="width:200px;display:inline;float:right;" />
	</script>
	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
		<shiro:hasPermission name="role:update">
  			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		</shiro:hasPermission>
	</script>
	<div id="dlg1" style="display:none;padding:10px">
		<form action="#" method="post" class="layui-form"  lay-filter="formDemo">
			<div class="layui-form-item">
    			<label class="layui-form-label">名称
    			<input type="text" style="display: none;" name="id" /></label>
				<div class="layui-input-block">
					<input type="text" name="name" required  lay-verify="required" placeholder="请输入角色名称" autocomplete="off" class="layui-input" />
				</div>
			</div>
			<div class="layui-form-item">
    			<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<input type="text" name="remark"  placeholder="描述信息" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn layui-btn-xs" lay-submit lay-filter="formSubmit">提交</button>
			      <button type="button" class="layui-btn layui-btn-xs layui-btn-primary btn-cancel" >取消</button>
			    </div>
			  </div>
		</form>
	</div>
	<div id="dlg2" style="display:none;padding:10px;">
		<ul id="ztree" class="ztree" style="width:460px;overflow:auto;"></ul>
		<div class="layui-form-item">
			    <div class="layui-input-block" style="position: absolute; min-height:28px; bottom:0px; padding-left:60px;">
			      <button class="layui-btn" lay-submit lay-filter="formSubmit1">提交</button>
			      <button type="button" class="layui-btn layui-btn-primary btn-cancel" >取消</button>
			    </div>
		 </div>
	</div>
	<div id="dlg3" style="display:none;padding:10px">
		<ul id="ztree1" class="ztree" style="width:460px;overflow:auto;"></ul>
		<div class="layui-form-item">
			    <div class="layui-input-block" style="position: absolute; min-height:28px; bottom:0px; padding-left:60px;">
			      <button class="layui-btn" lay-submit lay-filter="formSubmit2">提交</button>
			      <button type="button" class="layui-btn layui-btn-primary btn-cancel" >取消</button>
			    </div>
		 </div>
	</div>
	<script type="text/html" id="kaiguan">
		<input type="checkbox" value="{{d.id}}" name="zzz" lay-skin="switch" lay-text="启用|禁用"
		{{d.state==1?'checked':''}} />
	</script>
	<script src="../js/json2.js"></script>
	<script src="../plugins/layui/layui.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script src="../plugins/zTree/js/jquery.ztree.all.js"></script>
	<script>
	
	var idx = 0;
		 layui.config({
			 base:'../js/'
			 ,version: '<%=System.currentTimeMillis()%>' //为了更新 js 缓存，可忽略
		 });
		layui.use([  'laypage', 'layer', 'table', 'element', 'slider' ,'commons','form'],
			function() {
				var laypage = layui.laypage //分页
				, layer = layui.layer //弹层
				, table = layui.table //表格
				, element = layui.element //元素操作
				, slider = layui.slider //滑块
				, form = layui.form
				, commons = layui.commons() //右下角提示
				, $ = layui.jquery
				var wh = ($(window).height()-5) + 'px';
				$("#ztree").height($(window).height()-95);
				$("#ztree1").height($(window).height()-95);
				var index1 = layer.load(1, {
					shade : [ 0.5, '#ccc' ]
				});
				//执行一个 table 实例
				table.render({
					id : 'userTable',
					elem : '#demo',
					height : 'full-0',
					method : 'post',
					url : 'queryPage.do',
					title : '角色列表',
					limit : 10,
					limits : [ 10, 15, 20, 30, 50, 100 ],
					page : {
						layout : [ 'limit', 'prev', 'page', 'next','skip', 'count' ],
						groups : 5
					},
					toolbar : '#toolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
					,defaultToolbar : []
					,totalRow : false //开启合计行
					,cols : [ [ //表头
					{ type : 'checkbox', fixed : 'left' }, 
					{
						type: 'numbers', 
						title: '序号',
						align : 'center',
						width : 60
					}
					, { field : 'name', title : '角色名称', width : 150, align : 'center'}
					, { field : 'remark', title : '备注', width : 180, align : 'center'}
					, { field : 'state', title: '状态', width:100, templet: '#kaiguan', align : 'center'}
					, {
						title : '操作',
						fixed : 'right',
						width : 165,
						align : 'center',
						toolbar : '#barDemo'
					} ] ],
					done : function(res) {
						layer.close(index1);
						$('#query').val(name);
					}
				});
		//表刷新方法
        var reloadTable = function (param) {
			if(param===undefined){
				param = {};
				name = param.name = $('#query').val();
			}
            table.reload("userTable", {
                where: param
            });
        }; 
        
        form.on('switch()', function(data){
        	var id = data.elem.value;
        	var state  = data.elem.checked?1:0;
        	$.ajax({
				type : "post",
				url : 'grant.do',
				dateType : "json",
				contentType : 'application/json',
				data : JSON.stringify({id:id,state:state}),
				success : function(result) {
					if (result.flag) {
						commons.showInfo(result.message);
						return;
					}
		        	data.elem.checked = !data.elem.checked;
		        	form.render('checkbox');
		        	layer.msg(result.message);
				}
			})
       	}); 
        
		//监听提交
		form.on('submit(formSubmit)', function(data) {
				var row = JSON.stringify(data.field);
				var url = "create.do";
				if (data.field.id) {
					url = "update.do"
				}
				$.ajax({
					type : "post",
					url : url,
					dateType : "json",
					contentType : 'application/json',
					data : row,
					success : function(result) {
						if (result.flag) {
							layer.close(idx);
							reloadTable({});
							commons.showInfo(result.message);
							return;
						}
						layer.msg(result.message);
					}
				});
				return false;
			});
			$('.btn-cancel').click(function(e) {
				layer.close(idx);
			});
			//监听头工具栏事件
			table.on('toolbar(test)',
				function(obj) {
					var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; //获取选中的数据
					switch (obj.event) {
					case 'add':
						$('form')[0].reset();
						form.render('formDemo');
						idx = layer.open({
							type : 1,
							content : $('#dlg1'),
							title : '添加角色',
							area : [ '500px', '330px' ]
							,cancel:function(){
								$('#dlg1').hide();
							}
						});
						break;
					case 'addColumn':
						if (data.length === 0) {
							layer.msg('请选择一行');
						} else if (data.length > 1) {
							layer.msg('只能同时编辑一个');
						} else {
							idx = layer.open({
								type : 1,
								content : $('#dlg2'),
								title : '关联栏目',
								offset:['0px'],
								area : [ '500px',wh ]
								,cancel:function(){
									$('#dlg2').hide();
								}
							});
							loadColumnsTree(data[0].id);
						}
						break;
					case 'addRight':
						if (data.length === 0) {
							layer.msg('请选择一行');
						} else if (data.length > 1) {
							layer.msg('只能同时编辑一个');
						} else {
							idx = layer.open({
								type : 1,
								content : $('#dlg3'),
								title : '关联权限',
								offset:['1px'],
								area : [ '500px',wh]
								,cancel:function(){
									$('#dlg3').hide();
								}
							});
							loadColumnsTree1(data[0].id);
						}
						break;
					case 'query':
						reloadTable();
				}
			});
			//监听行工具事件
			table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
				if (obj === false)
					return;
				var data = obj.data //获得当前行数据
				, layEvent = obj.event; //获得 lay-event 对应的值
				if (layEvent === 'detail') {
					layer.open({
						title:'查看角色',
						skin : 'layui-layer-molv',
						type:1,
						area:['300px','220px'],
						closeBtn:1,
						shade:0.5,
						content:'<form class="layui-form">'
									+'<div class="layui-form-item"><label class="layui-form-label">名称</label>'
									+'<div class="layui-input-block" style="line-height:38px">'+data.name+'</div></div>'
									+'<div class="layui-form-item"><label class="layui-form-label">描述</label>'
									+'<div class="layui-input-block" style="line-height:38px">'+data.remark+'</div></div>'
									+'</form>'
					});
				} else if (layEvent === 'edit') {
					form.val('formDemo',data);
					idx = layer.open({
						type: 1,
				    	content: $('#dlg1'),
				    	title:'编辑角色',
				    	area:['500px','330px']
						,cancel:function(){
							$('#dlg1').hide();
						}
				    });
				}
			});
			var setting = {
			        view: {
			            dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
			            showLine: true,//是否显示节点之间的连线
			            fontCss:{'color':'black','font-weight':'bold'},//字体样式函数
			            selectedMulti: true //设置是否允许同时选中多个节点
			        },
			        check:{
			            chkboxType: { "Y": "ps", "N": "ps" },
			            chkStyle: "checkbox",
			            enable: true
			        },
			        data: {
			        	key:{
			        		url : 'undefined'
			        	},
			        	simpleData: {//简单数据模式
			                enable:true,
			                idKey: "id",
			                pIdKey: "parentId",
			                rootPId: null
			            }	
			        }
			    };
			var setting2 = {
			        view: {
			            dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
			            showLine: true,//是否显示节点之间的连线
			            fontCss:{'color':'black','font-weight':'bold'},//字体样式函数
			            selectedMulti: true //设置是否允许同时选中多个节点
			        },
			        check:{
			            chkboxType: { "Y": "s", "N": "s" },
			            chkStyle: "checkbox",
			            enable: true
			        },
			        data: {
			        	key:{
			        		url : 'undefined'
			        	},
			        	simpleData: {//简单数据模式
			                enable:true,
			                idKey: "id",
			                pIdKey: "parentId",
			                rootPId: null
			            }	
			        }
			    };
			var zTreeObj0,zTreeObj,zTreeObj1;
			var loadDeptsTree = function(id){
				$.ajax({
					type : "post",
					url : '../department/queryDept.do?id='+id,
					dateType : "json",
					contentType : 'application/json',
					success : function(result) {
						zTreeObj0 = $.fn.zTree.init($("#ztree0"), setting2, result.data);
					}
				});
			}
			var loadColumnsTree = function(id){
				$.ajax({
					type : "post",
					url : '../column/query.do',
					dateType : "json",
					data: '{"roleId":'+id+'}',
					contentType : 'application/json',
					success : function(result) {
						zTreeObj = $.fn.zTree.init($("#ztree"), setting, result.data);
					}
				});
			}
			var loadColumnsTree1 = function(id){
				$.ajax({
					type : "post",
					url : '../right/query.do',
					dateType : "json",
					data: '{"roleId":'+id+'}',
					contentType : 'application/json',
					success : function(result) {
						zTreeObj1 = $.fn.zTree.init($("#ztree1"), setting, result.data);
					}
				});
			}
			form.on('submit(formSubmit1)', function(data) {
				var id = table.checkStatus('userTable').data[0].id;
				var ids = zTreeObj.getCheckedNodes().map(function(obj){return obj.id;}).toString();
				$.ajax({
					type : "post",
					url : 'addColumn.do?id='+id+'&ids='+ids,
					dateType : "json",
					contentType : 'application/json',
					success : function(result) {
						commons.showInfo(result.message);
						layer.close(idx);
					}
				});
				return false;
			});
			form.on('submit(formSubmit2)', function(data) {
				var id = table.checkStatus('userTable').data[0].id;
				var ids = zTreeObj1.getCheckedNodes()
							.filter(function(obj){return obj.columnId!=undefined})
							.map(function(obj){return obj.id;}).toString();
				$.ajax({
					type : "post",
					url : 'addRight.do?id='+id+'&ids='+ids,
					dateType : "json",
					contentType : 'application/json',
					success : function(result) {
						commons.showInfo(result.message);
						layer.close(idx);
					}
				});
				return false;
			});
			form.on('submit(formSubmit3)', function(data) {
				var id = table.checkStatus('userTable').data[0].id;
				var ids = zTreeObj0.getCheckedNodes()
							.map(function(obj){return obj.id;}).toString();
				$.ajax({
					type : "post",
					url : 'addDept.do?id='+id+'&ids='+ids,
					dateType : "json",
					contentType : 'application/json',
					success : function(result) {
						commons.showInfo(result.message);
						layer.close(idx);
					}
				});
				return false;
			});
		});
	</script>
</body>
</html>