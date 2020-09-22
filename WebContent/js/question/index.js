layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload', 'rest' ], function() {
	var _query = {};
	var f = true;
	var laypage = layui.laypage // 分页
	, layer = layui.layer // 弹层
	, table = layui.table // 表格
	, element = layui.element // 元素操作
	, slider = layui.slider // 滑块
	, form = layui.form, commons = layui.commons() // 右下角提示
	, $ = layui.jquery, combo = layui.combo(), rest = layui.rest(), upload = layui.upload;
	var index1 = layer.load(1,
		{
			shade : [ 0.5, '#ccc' ]
		})
	table.render(
		{
			id : 'userTable',
			elem : '#demo',
			height : 'full-0',
			method : 'post',
			url : 'queryPage.do',
			where : {state:1},
			title : '试题列表',
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
					field : 'id',
					title : '序号',
					type:'numbers',
					align : 'center',
					width : 70
				},
				{
					field : 'department',
					title : '部门',
					align : 'center',
					width : 100
				},
				{
					field : 'workShop',
					title : '科室/车间',
					align : 'center',
					width : 100
				},
				{
					field : 'courseName',
					title : '课程名称',
					align : 'center',
					width : 100
				},
				{
					field : 'type',
					title : '类型',
					width : 105,
					align : 'center'
				},
				/*{
					field : 'title',
					title : '标题',
					width : 130,
					align : 'center'
				},*/
				{
					field : 'content',
					title : '题目',
					width : 300
				},
				{
					field : 'selections',
					title : '选项',
					width : 150
				},
				{
					field : 'answer',
					title : '答案',
					width : 200
				},
				/*{
					field : 'state',
					title : '状态',
					templet : function(d){return ['禁用','启用'][d.state]}
				}*/,
				{
					fixed : 'right',
					title : '操作',
					width : 150,
					toolbar : '#barDemo'
				} ] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform', _query);
				if(f){
					// 指定允许上传的文件类型
					upload.render(
					{
						elem : $('[lay-event=importExcel]'),
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
					f = false;
				}
			}
		});
	// 表刷新方法
	window.reloadTable = function(param) {
		index1 = layer.load(1,
				{
					shade : [ 0.5, '#ccc' ]
				});
		f = true;
		$.extend(true, _query, param);
		table.reload("userTable",
			{
				where : _query
			});
	};
	loadPos();
	function reset() {
		$('[lay-filter=frm1]')[0].reset();
		$('#xx').html('');
	}

	form.on('submit(tijiao)', function(data) {
		var arr = [];
		$("input:checkbox[name='positionId']:checked").each(function(item) {
			arr.push($(this).val());
		});
		data.field.positionId = arr.toString();
		return rest.formSubmit(data);
	});
	$('.btn-cancel').click(function(e) {
		layer.close(rest.config.idx);
	});
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data;
		switch (obj.event) {
		case 'create':
			reset();
			rest.config.idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '添加试题',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
			break;
		case 'reset':
			$('#form1')[0].reset();
			break;
		case 'downloadfile':
			$('#download').attr('src', 'downloadTemplate.do');
			break;
		case 'detail':
			if(data.length!=1){
				layer.msg('请选择一条数据')
				return;
			}
			data = data[0];
			rest.config.idx = layer.open(
					{
						title : '查看试题信息',
						skin : 'layui-layer-molv',
						type : 1,
						area : [ '660px', '450px' ],
						closeBtn : 1,
						shade : 0.5,
						content : '<form class="layui-form">' 
								+'<div class="layui-form-item"><label class="layui-form-label">部门</label>' + '<div class="layui-input-block" style="line-height:22px">'
								+ data.department + '</div></div>'
								+'<div class="layui-form-item"><label class="layui-form-label">科室/车间</label>' + '<div class="layui-input-block" style="line-height:22px">'
								+ data.workShop + '</div></div>'
								+ '<div class="layui-form-item"><label class="layui-form-label">课程</label>' + '<div class="layui-input-block" style="line-height:22px">'
								+ data.courseName + '</div></div>' + '<div class="layui-form-item"><label class="layui-form-label">问题</label>' + '<div class="layui-input-block" style="line-height:22px">'
								+ data.content + '</div></div>' + '<div class="layui-form-item"><label class="layui-form-label">选项</label>' + '<div class="layui-input-block" style="line-height:22px">'
								+ data.selections + '</div></div>' + '<div class="layui-form-item"><label class="layui-form-label">答案</label>' + '<div class="layui-input-block" style="line-height:22px">'
								+ data.answer + '</div></div>' + '</form>'
					});
			break;
		case 'edit':
			if(data.length!=1){
				layer.msg('请选择一条数据')
				return;
			}
			data = data[0];
			form.val('frm1', data);
			rest.config.idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '编辑试题',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
			break;
		case 'del':
			if(data.length==0){
				layer.msg('请选择数据')
				return;
			}
			rest.deleteMethod(data.map(x=>x.id).join(','));
			break;
		case 'enable':
			if(data.length==0){
				layer.msg('请选择数据')
				return;
			}
			rest.deleteMethod1(data.map(x=>x.id).join(','));
			break;
		}
	});

	function loadPos() {
		combo.set(
			{
				elem : 'courseId',
				param : {},
				url : basePath + 'course/queryList.do'
			}).render();
	}

	table.on('tool(test)', function(obj) {
		if (obj === false)
			return;
		var data = obj.data
		, layEvent = obj.event;
		if (layEvent === 'detail') {
			rest.config.idx = layer.open(
				{
					title : '查看试题信息',
					skin : 'layui-layer-molv',
					type : 1,
					area : [ '660px', '450px' ],
					closeBtn : 1,
					shade : 0.5,
					content : '<form class="layui-form">' + '<div class="layui-form-item"><label class="layui-form-label">课程</label>' + '<div class="layui-input-block" style="line-height:22px">'
							+ data.courseName + '</div></div>' + '<div class="layui-form-item"><label class="layui-form-label">标题</label>' + '<div class="layui-input-block" style="line-height:22px">'
							+ data.title + '</div></div>' + '<div class="layui-form-item"><label class="layui-form-label">问题</label>' + '<div class="layui-input-block" style="line-height:22px">'
							+ data.content + '</div></div>' + '<div class="layui-form-item"><label class="layui-form-label">选项</label>' + '<div class="layui-input-block" style="line-height:22px">'
							+ data.selections + '</div></div>' + '<div class="layui-form-item"><label class="layui-form-label">答案</label>' + '<div class="layui-input-block" style="line-height:22px">'
							+ data.answer + '</div></div>' + '</form>'
				});
		} else if (layEvent === 'del') {
			rest.deleteMethod(data.id)
		} else if (layEvent === 'edit') {
			form.val('frm1', data);
			rest.config.idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '编辑试题',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
		}
	});
	form.on('submit(query)', function(data) {
		_query = data.field;
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