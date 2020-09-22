layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload' ], function() {
	var idx = 0;
	var laypage = layui.laypage // 分页
	, layer = layui.layer // 弹层
	, table = layui.table // 表格
	, element = layui.element // 元素操作
	, slider = layui.slider // 滑块
	, form = layui.form, commons = layui.commons() // 右下角提示
	, $ = layui.jquery, combo = layui.combo(), upload = layui.upload;
	var wh = ($(window).height()-10)+'px';
	$("#ztree0").height($(window).height()-100);
	var f = true;
	var index1 = layer.load(1,
		{
			shade : [ 0.5, '#ccc' ]
		});
	table.render(
		{
			id : 'userTable',
			elem : '#demo',
			height : 'full-0',
			method : 'post',
			url : 'queryPage.do',
			title : '角色列表',
			limit : 10,
			limits : [ 10, 15, 20, 30, 50, 100 ],
			page :
				{
					layout : [ 'limit', 'prev', 'page', 'next', 'skip', 'count' ],
					groups : 5
				},
			toolbar : '#toolbar',
			defaultToolbar : [],
			totalRow : false,
			cols : [ [
				{
					type : 'checkbox',
					fixed : 'left'
				},
				{
					type: 'numbers', 
					title: '序号',
					align : 'center',
					width : 60
				},
				{
					field : 'department',
					title : '部门',
					width : 120,
					align : 'center'
				},
				{
					field : 'workShop',
					title : '科室/车间',
					width : 120,
					align : 'center'
				},
				{
					field : 'dept',
					title : '班组',
					width : 120,
					align : 'center'
				},
				{
					field : 'usercode',
					title : '工号',
					width : 120,
					align : 'center'
				},
				{
					field : 'name',
					title : ' 姓名',
					width : 120
					, align : 'center'
				},
				{
					field : 'role',
					title : '角色',
					width : 150
					, align : 'center'
				},
				{
					field : 'remark',
					title : '备注',
					width : 180
					, align : 'center'
				},
				{
					field : 'state',
					title : '状态',
					width : 110,
					templet : '#state'
				} ] ],
			done : function(res) {
				layer.close(index1);
				$('#query').val(name);
				if(f){
					upload.render(
					{
						elem : $('#importExcel'),
						url : 'importYC2GW.do',
						accept : 'file',
						field : 'filename',
						acceptMime : '.xlsx',
						exts : 'xlsx',
						multiple : true,
						before: function(obj){
						    layer.load();
						},
						done : function(res) {
							layer.closeAll();
							if (res.flag) {
								commons.showInfo(res.message);
								reloadTable();
								return;
							}
							layer.msg(res.message)
						}
					});
					f =false;
				}
				try {
					combo.set(
						{
							elem : 'roles',
							url : '../role/queryall.do'
						}).render();
				} catch (e) {
					layer.msg(e.message)
				}
			}
		});
	// 表刷新方法
	window.reloadTable = function(obj) {
		f = true;
		index1 = layer.load(1,
				{
					shade : [ 0.5, '#ccc' ]
				});
		var param = {};
		name = param.query = $('#query').val();
		if(obj!=undefined){
			$.extend(true,param,obj);
		}
		table.reload("userTable",
			{
				where : param
			});
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
	// 监听提交
	form.on('submit(formSubmit)', function(data) {
		var row = JSON.stringify(data.field);
		var url = "create.do";
		if (data.field.id) {
			url = "update.do"
		}
		$.ajax(
			{
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
	
	form.on('submit(formSubmit3)', function(data) {
		var id = table.checkStatus('userTable').data[0].id;
		var ids = zTreeObj0.getCheckedNodes().map(function(obj){return obj.id;}).toString();
		$.ajax({
			type : "post",
			url : '../role/addDept.do?id='+id+'&ids='+ids,
			dateType : "json",
			contentType : 'application/json',
			success : function(result) {
				commons.showInfo(result.message);
				layer.close(idx);
			}
		});
		return false;
	});
	
	$('.btn-cancel').click(function(e) {
		layer.close(idx);
	});
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
		switch (obj.event) {
		case 'addDept':
			if (data.length === 0) {
				layer.msg('请选择一行');
			} else if (data.length > 1) {
				layer.msg('只能同时编辑一个');
			} else {
				idx = layer.open({
					type : 1,
					offset:['1px'],
					content : $('#dlg4'),
					title : '关联部门',
					area : [ '500px',wh ]
					,cancel:function(){
						$('#dlg4').hide();
					}
				});
				loadDeptsTree(data[0].id);
			}
			break;
		case 'query':
			reloadTable();
			break;
		case 'updateRole':
			if (data.length == 0) {
				layer.msg('没有选中数据')
				return;
			}
			var id = $('#roles').val();
			if (id === undefined || id == '') {
				layer.msg('没有选择角色')
				return;
			}
			var ids = data.map(function(x) {
				return x.id
			}).toString();
			$.ajax(
				{
					type : "post",
					url : 'updateRole.do',
					dateType : "json",
					contentType : 'application/json',
					data : JSON.stringify(
						{
							roleId : id,
							ids : ids
						}),
					success : function(result) {
						if (result.flag) {
							commons.showInfo(result.message);
							reloadTable();
							return;
						}
						layer.msg(result.message);
					}
				});
			break;
		case 'resetPassword':
			if (data.length == 0) {
				layer.msg('没有选中数据')
				return;
			}
			var ids = data.map(function(x) {
				return x.id;
			}).toString();
			$.ajax(
				{
					type : "post",
					url : 'resetPassword.do?ids=' + ids,
					dateType : "json",
					contentType : 'application/json',
					success : function(result) {
						if (result.flag) {
							commons.showInfo(result.message);
							return;
						}
						layer.msg(result.message);
					}
				});
			break;
		case 'downloadfile':
			$('#download').attr('src', 'downloadYC2GWTemplate.do');
			break;
		}
	});
});