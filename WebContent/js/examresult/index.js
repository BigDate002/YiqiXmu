layui.config(
	{
		base : '../js/',
		version : new Date().getMilliseconds() // 为了更新 js 缓存，可忽略
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'form', 'laydate', 'combo', 'upload', ], function() {
	var f = true;
	var _query = {};
	var laypage = layui.laypage // 分页
	, layer = layui.layer // 弹层
	, table = layui.table // 表格
	, element = layui.element // 元素操作
	, slider = layui.slider // 滑块
	, form = layui.form
	, commons = layui.commons() // 右下角提示
	, $ = layui.jquery
	, combo = layui.combo()
	, laydate = layui.laydate
	, upload = layui.upload;
	var td = new Date();
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
			url : '..//examresult/queryPageI.do',
			title : '考核列表',
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
					type : 'numbers',
					title : '序号',
					width : 60,
					align : 'center'
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
					field : 'workGroup',
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
					field : 'username',
					title : '姓名',
					width : 120,
					align : 'center'
				},
				{
					field : 'year',
					title : '考核时间',
					width : 90,
					align : 'center',
					templet : function(d) {
						return d.year + '年' + d.month + '月'
					}
				},
				{
					field : 'result',
					title : '考核结果',
					width : 80,
					align : 'center'
				},
				{
					title : '操作',
					fixed : 'right',
					width : 120,
					toolbar : '#barDemo',
					align : 'center'
				} ] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform', _query);
				lay('.test-item').each(function() {
					laydate.render(
						{
							elem : this,
							format : 'yyyy年MM月',
							type : 'month',
							trigger : 'click'
						});
				});
				if (f) {
					upload.render(
						{
							elem : $('[lay-event=importExcel]'),
							url : 'importData.do',
							accept : 'file',
							field : 'filename',
							acceptMime : '.xlsx',
							exts : 'xlsx',
							multiple : true,
							before : function(obj) {
								layer.load();
							},
							done : function(res) {
								layer.closeAll();
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
	// 监听行工具事件
	table.on('tool(test)', function(obj) {
		if (obj === false)
			return;
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'edit') {
			form.val('frm1', data);
			$('#year').val(data.year+'年'+data.month+'月');
			$('#year').attr('disabled',true);
			$('#usercode').attr('disabled',true);
			form.render('select');
			idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '编辑',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
		}
	});

	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
		switch (obj.event) {
		case 'reset':
			document.getElementById('queryform').reset();
			break;
		case 'downloadfile':
			$('#download').attr('src', 'downloadTemplate.do');
			break;
		case 'create':
			//$('#year').removeAttr('disabled');
			form.val('frm1', {id:'',year:td.getFullYear()+'年'+(td.getMonth()==0?12:td.getMonth())+'月',usercode:'',result:''});
			$('#year').removeAttr('disabled');
			$('#usercode').removeAttr('disabled');
			form.render('select');
			idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '新增',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
			break;
		}
	});

	form.on('submit(query)', function(data) {
		_query = data.field;
		reloadTable(data.field);
		return false;
	});
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
		loadUserList(_query.departmentId);
	};
	
	var list = combo.config.data;
	var loadUserList = function(deptId){
		var param = {};
		if(deptId){
			param.departmentId=deptId;
		}
		combo.set({
			elem : 'usercode',
			valueField : 'usercode',
			url : '../train/queryUserbypostion.do',
			param : param
		}).render();
		list = combo.config.data;
	}
	
	loadUserList();
	
	// 监听提交
	form.on('submit(tijiao)', function(data) {
		if(data.field.year&&data.field.year!=''){
			var ss = data.field.year.split('年')
			data.field.year = ss[0];
			data.field.month = ss[1].split('月')[0];
		}
		var row = JSON.stringify(data.field);
		var url = "create.do";
		if (data.field.id) {
			url = "update.do"
		}
		layer.load();
		$.ajax(
			{
				type : "post",
				url : url,
				dateType : "json",
				contentType : 'application/json',
				data : row,
				success : function(result) {
					layer.closeAll();
					if (result.flag) {
						layer.close(idx);
						reloadTable({});
						commons.showInfo(result.message);
						return;
					}
					layer.msg(result.message);
				},error : function(){
					layer.msg('网络错误');
				}
			});
		return false;
	});
	
	form.on('submit(exportData)', function(data) {
		$.extend(true, data.field, _query);
		submitForm(data.field);
		return false;
	});
	
	$('.btn-cancel').click(function(e) {
		layer.close(idx);
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