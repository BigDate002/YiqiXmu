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
			url : basePath + 'qualify/queryPage.do',
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
				  { type : 'checkbox', fixed : 'left'}
				, { field : 'id', title : 'ID', width : 60 , align: 'center' }
				, { field : 'code', title : '证书编号', width : 160 , align:'center'}
				, { field : 'userCode', title : '工号', width : 120 , align:'center'}
				, { field : 'username', title : '姓名', width : 80 , align:'center'}
				, { field : 'department', title : '部门', width : 110, align:'center'}
				, { field : 'workShop', title : '科室/车间', width : 108, align:'center'}
				, { field : 'workGroup', title : '班组', width : 80, align:'center'}
				, { field : 'post', title : '岗位', width : 90, align:'center'}
				, { title : '有效期限', width : 80, align:'center', templet : function(d){return d.effectiveDate+'个月';}}
				, { field : 'beginDate', title : '生效日期', width : 100, align:'center'}
				, { field : 'endDate', title : '失效日期',width: 100}
				, { field : 'state', title : '状态',width: 60,	templet:function(d){return ['正常','待复审','过期','复审中','不合格'][d.state]}}
				, { field : 'type', title : '复审次数', width : 80 , align:'center'}
				, { field : 'iseffective', title : '是否持特种证件', width : 120 , align:'center'}
				//, { title : '操作', fixed : 'right', toolbar : '#barDemo', width:70}
				] ],
			done : function(res) {
				if(isFirst){
					upload.render({
						 elem: $('[lay-event=importExcel]')
						,url: 'importData.do'
						,accept: 'file'
						,field: 'filename'
						,acceptMime: '.xlsx'
						,exts: 'xlsx'
						,multiple: false
						,before: function(obj){
						    layer.load();
						}
						,done: function(res){
							layer.closeAll();
							if(res.flag){
								commons.showInfo(res.message);
								reloadTable();
								return;
							}
							layer.msg(res.message)
						} ,error: function(){

						}
					});
					isFirst = false;
				}
				form.val('queryform',_query);
				layer.close(index1);
			}
		});
	var isFirst = true;
	var html = $('#detail').html();
	var html1 = $('#detail1').html();
	window.reloadTable = function(param) {
		index1 = layer.load(1,
				{
					shade : [ 0.5, '#ccc' ]
				});
		table.reload("userTable",{where:param});
		isFirst = true;
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

	form.on('submit(tijiao)', function(data) {
		var row = JSON.stringify(data.field);
		  var url = "create.do";
		  if(data.field.id){
			  url = "update.do"
		  }
		  $.ajax({
			  type:"post",
			  url:url,
			  dateType:"json",
			  contentType : 'application/json',
			  data:row,
			  success:function(result){
				  if(result.flag){
					  layer.close(idx);
					  reloadTable({});
					  commons.showInfo(result.message);
					  return;
				  }
				  layer.msg(result.message);
			  }
		  });
		  return false;
		return false;
	});

	function updateUseable(data,flag){
		if(data.length==0){
			layer.msg('请选择数据')
			return;
		}
		var ids = data.map(function(item){return item.id;}).toString();
		$.ajax({
			  type:"post",
			  url:'grant.do',
			  dateType:"json",
			  contentType : 'application/json',
			  data:JSON.stringify({ids:ids,state:flag}),
			  success:function(result){
				  if(result.flag){
					  layer.close(idx);
					  reloadTable({});
					  commons.showInfo(result.message);
					  return;
				  }
				  layer.msg(result.message);
			  }
		  });
	}

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
		} else if (layEvent === 'del') {
			deleteMethod(data.id)
		} else if (layEvent === 'edit') {
			form.val('frm1',data);
			idx = layer.open({
				type: 1,
		    	content: $('#dlg'),
		    	title:'编辑岗位',
		    	area:['600px','425px']
				,cancel:function(){
					$('#dlg').hide();
				}
		    });
		}
	});

	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data;
		switch (obj.event) {
			case 'create':
				form.val('frm1',{
					deptId:'',
					departmentId:'',
					name:'',
					type:'',
					examPeriod:'',
					remark:''
				});
				idx = layer.open({
					type: 1,
			    	content: $('#dlg'),
			    	title:'添加岗位',
			    	area:['600px','425px']
					,cancel:function(){
						$('#dlg').hide();
					}
			    });
				break;
			case 'reset':
				$('#queryform')[0].reset();
				break;
			case 'enable':
				updateUseable(data,1);
				break;
			case 'disable':
				updateUseable(data,0);
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
					title:'查看资质信息',
					type:1,
					area:['524px','400px'],
					content: $(id),
					cancel:function(){
						$(id).hide();
					}
				});
				break;
			case 'certificateRecovery':
				if(data.length<1){
					layer.msg('请选择至少一条数据')
					return;
				}
				layer.confirm('确定收回选中数据？',
					{
						btn : [ '确定', '取消' ]
					}, function(index) {
						layer.close(index);
						$.ajax(
							{
								type : "post",
								url : 'certificateRecovery.do?ids='+data.map(x=>x.id).join(','),
								dateType : "json",
								contentType : 'application/json',
								success : function(result) {
									if (result.flag) {
										reloadTable({});
										commons.showInfo(result.message);
									}else{
										layer.msg(result.message);
									}
								}
							});
					});
				break;
		}
	});
});

function submitForm(obj){
	$('#xiazai').form({
		url:'exportData.do',
		onSubmit: function(param){
			$.extend(true,param,obj);
	    }
	});
	$('#xiazai').submit();
}
