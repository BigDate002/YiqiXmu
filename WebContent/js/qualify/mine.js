layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload'], function() {
	var idx = 0;
	var _query = {};
	var laypage = layui.laypage
	, upload = layui.upload
	, layer = layui.layer
	, table = layui.table
	, element = layui.element
	, slider = layui.slider
	, form = layui.form, commons = layui.commons()
	, $ = layui.jquery, combo = layui.combo()
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
			url : basePath + 'qualify/queryMinePage.do',
			title : '列表',
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
				  { type : 'checkbox', fixed : 'left'}
				, { type : 'numbers', title : '序号', width : 60 , align: 'center' }
				, { field : 'code', title : '证书编号', width : 160 , align:'center'}
				, { field : 'type', title : '复审次数', width : 80 , align:'center'}
				, { field : 'username', title : '姓名', width : 80 , align:'center'}
				, { field : 'post', title : '岗位', width : 90, align:'center'}
				, { field : 'department', title : '部门', width : 110, align:'center'}
				, { field : 'workShop', title : '科室/车间', width : 108, align:'center'}
				, { field : 'workGroup', title : '班组', width : 110, align:'center'}
				, { field : 'effectiveDate', title : '有效期限(月)', width : 110, align:'center'}
				, { field : 'beginDate', title : '生效日期', width : 100, align:'center'}
				, { field : 'endDate', title : '失效日期',width: 100}
				//, { title : '资格证', fixed : 'right', toolbar : '#barDemo', width:70}
				] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform',_query);
			}
		});

	var html = $('#detail').html();
	var html1 = $('#detail1').html();
	var reloadTable = function(param) {
		table.reload("userTable",{where:param});
	};

	form.on('submit(query)', function(data) {
		_query = data.field;
		reloadTable(data.field);
		return false;
	});
	
	form.on('submit(exportData)', function(data) {
		$.extend(true,data.field,_query);
		submitForm(data.field);
		return false;
	});

	$('.btn-cancel').click(function(e) {
		layer.close(idx);
	});
	
	table.on('tool(test)', function(obj) {
		if (obj === false)
			return;
		var data = obj.data
		, layEvent = obj.event;
		if (layEvent === 'detail') {
			var htm = html;
			id = '#detail';
			htm= '<img width="524" src="downloadimg.do?id='+data.id+'" />';
			$(id).html(htm);
			layer.open({
				title:'查看资质信息',
				type:1,
				area:['524px','400px'],
				content: $(id),
				cancel:function(){
					$(id).hide();
				}				
			});
			//parent.showMessage('查看详情',5000);
		}
	});
	
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data;
		switch (obj.event) {
			case 'reset':
				$('#queryform')[0].reset();
				break;
			case 'downloadfile':
				$('#download').attr('src','downloadTemplate.do');
				break;
			case 'detail':
				if(data.length!=1){
					layer.msg('请选择一条数据')
					return;
				}
				data = data[0];
				var htm = html;
				id = '#detail';
				htm= '<img width="524" src="downloadimg.do?id='+data.id+'" />';
				$(id).html(htm);
				layer.open({
					title:'资质证书',
					type:1,
					area:['524px','400px'],
					content: $(id),
					cancel:function(){
						$(id).hide();
					}				
				});
				break;
		}
	});
});

function submitForm(obj){
	$('#xiazai').form({
		url:'exportMineData.do',
		onSubmit: function(param){
			$.extend(true,param,obj);
	    }
	});
	$('#xiazai').submit();
}