layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload' ], function() {
	var idx = 0;
	var _query = {};
	var laypage = layui.laypage // 分页
	, layer = layui.layer // 弹层
	, table = layui.table // 表格
	, element = layui.element // 元素操作
	, slider = layui.slider // 滑块
	, form = layui.form, commons = layui.commons() // 右下角提示
	, $ = layui.jquery, combo = layui.combo(), upload = layui.upload;
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
			url : basePath + 'staff/queryPage.do',
			title : '人员列表',
			limit : 10,
			limits : [ 10, 15, 20, 30, 50, 100 ],
			where : {userState:1},
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
					field : 'usercode',
					title : '工号',
					width : 110
					,align : 'center'
				},
				{
					field : 'name',
					title : '姓名',
					width : 100
					,align : 'center'
				},
				{
					field : 'sex',
					title : '性别',
					align : 'center',
					width : 70,
					templet : function(d) {
						return [ '女', '男' ][d.sex];
					}
				},
				{
					field : 'department',
					title : '部门',
					width : 120
					,align : 'center'
				},
				{
					field : 'workShop',
					title : '科室/车间',
					width : 120
					,align : 'center'
				},
				{
					field : 'dept',
					title : '班组',
					width : 120
					,align : 'center'
				},
				{
					field : 'remark',
					title : '备注',
					width : 120
				},
				{
					field : 'state',
					title : '状态',
					width : 60,
					templet : '#state'
				}
				/*,
				{
					fixed : 'right',
					title : '操作',
					width : 120,
					toolbar : '#barDemo'
				} */] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform', _query);
				if(f){
					upload.render(
					{
						elem : $('#importExcel'),
						url : 'importData.do',
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
			}
		});
	var f = true;
	// 表刷新方法
	window.reloadTable = function(param) {
		index1 = layer.load(1,
			{
				shade : [ 0.5, '#ccc' ]
			});
		$.extend(true, _query, param);
		f = true;
		table.reload("userTable",
			{
				where : _query
			});
	};

	function reset() {
		loadKeshi(-1);
		loadBanzu(-1);
		$('[lay-filter=frm1]')[0].reset();
		$('#xx').html('');
		$('#xxx').html('');
	}

	// 监听提交
	form.on('submit(tijiao)', function(data) {
		if(data.field.deptId==''){
			data.field.deptId = data.field.workShopId;
		}
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
	$('.btn-cancel').click(function(e) {
		layer.close(idx);
	});
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
		switch (obj.event) {
		case 'create':
			reset();
			idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '添加人员',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
			break;
		case 'reset':
			$('#form1')[0].reset();
			_query =
				{
					departmentId : ''
				};
			break;
		case 'enable':
			updateUseable(data, 1);
			break;
		case 'del':
			delUser(data);
			break;
		case 'disable':
			// 判断如果存在数据 不能禁用
			debugger;
			if (data[0].id > 0) {
				layer.msg('存在人员数据不能禁用')
				return;
			}
			updateUseable(data, 0);
			break;
		case 'downloadfile':
			$('#download').attr('src', 'downloadTemplate.do');
			break;
		case 'detail':
			if(data.length!=1){
				layer.msg('请选择一条数据');
				return;
			}
			data =data[0];
			idx = layer.open(
				{
					title : '查看人员信息',
					skin : 'layui-layer-molv',
					type : 1,
					area : [ '460px', '350px' ],
					closeBtn : 1,
					shade : 0.5,
					content : '<form class="layui-form">'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">工号</label>'
								+ '<label class="layui-form-label c1">' + data.usercode + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">姓名</label>'
								+ '<label class="layui-form-label c1">' + data.name + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">性别</label>'
								+ '<label class="layui-form-label c1">' + ['女','男'][data.sex] + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">部门</label>'
								+ '<label class="layui-form-label c1">' + data.department + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">科室/车间</label>'
								+ '<label class="layui-form-label c1">' + data.workShop + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">班组</label>'
								+ '<label class="layui-form-label c1">' + data.dept + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">状态</label>'
								+ '<label class="layui-form-label c1">' + (data.userState == 1 ? '在职' : '离职') + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">备注</label>'
								+ '<label class="layui-form-label c1" style="width:320px">' + data.remark + '</label></div>'
							+ '</form>'
				});
				break;
			case 'edit':
				if(data.length!=1){
					layer.msg('请选择一条数据');
					return;
				}
				data = data[0];
				loadKeshi(data.departmentId,false);
				loadBanzu(data.workShopId,false);
				form.val('frm1', data);
				idx = layer.open(
					{
						type : 1,
						content : $('#dlg'),
						title : '编辑人员',
						area : [ '600px', '425px' ],
						cancel : function() {
							$('#dlg').hide();
						}
					});
				break;
		}
	});

	function delUser(data) {
		if (data.length == 0) {
			layer.msg('请选择数据')
			return;
		}

		layer.confirm("确定删除选中数据？",
			{
				btn : [ '确定', '取消' ]
			}, function(index) {
				layer.close(index);
				var ids = data.map(function(item) {
					return item.id;
				}).toString();
				$.ajax(
					{
						type : "post",
						url : 'del.do',
						dateType : "json",
						contentType : 'application/json',
						data : JSON.stringify(
							{
								ids : ids
							}),
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
			});
	}

	function updateUseable(data, flag) {
		if (data.length == 0) {
			layer.msg('请选择数据')
			return;
		}
		var ids = data.map(function(item) {
			return item.id;
		}).toString();
		$.ajax(
			{
				type : "post",
				url : flag==1?'grant.do':'grant1.do',
				dateType : "json",
				contentType : 'application/json',
				data : JSON.stringify(
					{
						ids : ids
					}),
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
	}

	try {
		combo.set(
			{
				valueField : 'code',
				elem : 'department',
				param :
					{
						level : '二级'
					},
				url : basePath + 'department/queryClass2.do'
			}).render();
	} catch (e) {
		layer.msg(e.message)
	}

	form.on('select(department)', function(data) {
		if (deptId == '')
			deptId = -1;
		var deptId = data.value;
		loadKeshi(deptId);
		loadBanzu(-1);
	});

	function loadKeshi(deptId,isasync) {
		combo.set(
			{
				valueField : 'code',
				elem : 'workShop',
				async : isasync===undefined?true:isasync,
				param :
					{
						parentId : deptId
					},
				url : basePath + 'department/queryClass2.do'
			}).render();
	}

	form.on('select(workShop)', function(data) {
		if (deptId == '')
			deptId = -1;
		var deptId = data.value;
		loadBanzu(deptId);
	});

	function loadBanzu(deptId,isasync) {
		combo.set(
			{
				valueField : 'code',
				elem : 'deptId',
				async : isasync===undefined?true:isasync,
				param :
					{
						parentId : deptId
					},
				url : basePath + 'department/queryClass2.do'
			}).render();
	}

	// 监听行工具事件
	table.on('tool(test)', function(obj) { // 注：tool 是工具条事件名，test 是 table
											// 原始容器的属性 lay-filter="对应的值"
		if (obj === false)
			return;
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			idx = layer.open(
				{
					title : '查看人员信息',
					skin : 'layui-layer-molv',
					type : 1,
					area : [ '460px', '350px' ],
					closeBtn : 1,
					shade : 0.5,
					content : '<form class="layui-form">'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">工号</label>'
								+ '<label class="layui-form-label c1">' + data.usercode + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">姓名</label>'
								+ '<label class="layui-form-label c1">' + data.name + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">性别</label>'
								+ '<label class="layui-form-label c1">' + ['女','男'][data.sex] + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">部门</label>'
								+ '<label class="layui-form-label c1">' + data.department + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">科室/车间</label>'
								+ '<label class="layui-form-label c1">' + data.workShop + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">班组</label>'
								+ '<label class="layui-form-label c1">' + data.dept + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">状态</label>'
								+ '<label class="layui-form-label c1">' + (data.userState == 1 ? '在职' : '离职') + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">备注</label>'
								+ '<label class="layui-form-label c1" style="width:320px">' + data.remark + '</label></div>'
							+ '</form>'
				});
		} else if (layEvent === 'del') {
			deleteMethod(data.id)
		} else if (layEvent === 'edit') {
			loadKeshi(data.departmentId);
			loadBanzu(data.workShopId);
			form.val('frm1', data);
			idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '编辑人员',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
		}
	});
	form.on('submit(query)', function(data) {
		$.extend(true, _query, data.field);
		reloadTable(data.field);
		return false;
	});

	form.on('submit(exportData)', function(data) {
		$.extend(true, data.field, _query);
		submitForm(data.field);
		return false;
	});
});

function submitForm(obj) {
	$('#xiazai').form(
		{
			url : 'exportData.do',
			onSubmit : function(param) {
				$.extend(true, param, obj);
			}
		});
	$('#xiazai').submit();
}
