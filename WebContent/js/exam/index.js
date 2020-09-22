layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload', 'rest' ], function() {
	var _query = {};
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
			title : '课程列表',
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
							type : 'checkbox'
						},
						{
							title : '序号',
							type : 'numbers',
							align : 'center',
							width : 70
						},
				{
					field : 'department',
					title : '部门',
					align : 'center',
					width : 120
				}
				,
				{
					field : 'workShop',
					title : '科室/车间',
					align : 'center',
					width : 120
				}
				,
				{
					field : 'query',
					title : '班组',
					align : 'center',
					width : 100
				},
						/*{
							field : 'code',
							title : '编码',
							align : 'center',
							width : 160
						},*/
						{
							title : '岗位',
							align : 'center',
							width : 150,
							field : 'postionName'
						},
						{
							title : '课程',
							align : 'center',
							width : 150,
							field : 'courseName'
						},
						{
							title : '测试类型',
							width : 80,
							align : 'center',
							templet:function(d){return d.exam.type==1?'复审':'初审';}
						},
						{
							field : 'usercode',
							title : '工号',
							align : 'center',
							width : 120
						},
						{
							field : 'username',
							title : '姓名',
							align : 'center',
							width : 100
						},
						{
							templet : function(d){return d.exam.beginDate;},
							title : '开始时间',
							align : 'center',
							width : 100
						},
						{
							templet : function(d){return d.exam.endDate;},
							title : '结束时间',
							align : 'center',
							width : 100
						},
						{
							templet : function(d){return ['未开始','已开始','已结束','已取消'][d.status];},
							title : '状态',
							align : 'center',
							width : 80
						},
						{
							templet : function(d){return ['未开始','已提交','已评分'][d.state];},
							title : '答题状态',
							align : 'center',
							width : 80
						},
						{
							field : 'result',
							title : '考核结果',
							align : 'center',
							width : 80
						}

						/*,
						{
							toolbar : '#barDemo',
							title : '操作',
							fixed : 'right',
							width : 300,
							align : 'center'
						}*/
						] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform', _query);
			}
		});

	// 表刷新方法
	window.reloadTable = function(param) {
		index1 = layer.load(1,
				{
					shade : [ 0.5, '#ccc' ]
				});
		$.extend(true, _query, param);
		table.reload("userTable",
			{
				where : _query
			});
	};

	$('.btn-cancel').click(function(e) {
		var id = $(e.target).parents('.layui-layer').attr("id").replace("layui-layer", "");
		layer.close(id);
	});
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
		switch (obj.event) {
		case 'reset':
			$('#form1')[0].reset();
			return;
			break;
		}
		// if(obj.event!='cancel'&&data.length!=1){
		// 	layer.msg('请选择一条数据')
		// 	return;
		// }
		var dta = data[0];
		switch (obj.event) {
			case 'detail':
				if(data.length!=1){
					layer.msg('请选择一条数据')
					return;
				}
				top.active.tabAdd('exam/detail2.html?id='+dta.exam.id,'test2','测试详请');
				top.active.tabChange('test2');
				break;
			case 'edit':
				if(data.length!=1){
					layer.msg('请选择一条数据')
					return;
				}
				if(dta.state==1&&dta.status==1){

				}else{
					layer.msg('禁止操作,答题状态需为已提交且状态应为已开始');
					return;
				}
				var tabID = top.active.getCurrentID();
				top.active.tabAdd('exam/score.html?tab='+tabID+'&id1='+dta.id+'&id='+dta.exam.id,'mask','评分');
				top.active.tabChange('mask');
				break;
			case 'cancel':
				if(data.length==0){
					layer.msg('请选择数据')
					return;
				}
				for(var i=0;i<data.length;i++){
					if(data[i].status!=1){
						layer.msg('状态错误必须为已开始');
						return;
					}
				}
				updateExam('cancel.do',{ids:data.map(x=>x.id).join(',')});
				break;
			case 'hide':
				if(data.length===0){
					layer.msg('请选择数据')
					return;
				}
				layer.confirm('确定隐藏数据？',
					{
						btn : [ '确定', '取消' ]
					}, function(index) {
						layer.close(index);
						$.ajax(
							{
								type : "post",
								url : 'hideOropen.do?state1=1&ids='+data.map(x=>x.exam.id).join(','),
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
			case 'open':
				if(data.length===0){
					layer.msg('请选择数据')
					return;
				}
				debugger;
				let ids = data.map(x=>x.exam.id).join(',');

				layer.confirm('确定开启数据？',
					{
						btn : [ '确定', '取消' ]
					}, function(index) {
						layer.close(index);
						$.ajax(
							{
								type : "post",
								url : 'hideOropen.do?state1=0&ids='+data.map(x=>x.exam.id).join(','),
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
	// 监听头工具栏事件
	table.on('tool(test)', function(obj) {
		var data = obj.data
		, layEvent = obj.event;
		switch (layEvent) {
			case 'detail':
				top.active.tabAdd('exam/detail2.html?id='+data.exam.id,'test2','测试详请');
				top.active.tabChange('test2');
				break;
			case 'edit':
				var tabID = top.active.getCurrentID();
				top.active.tabAdd('exam/score.html?tab='+tabID+'&id1='+data.id+'&id='+data.exam.id,'mask','评分');
				top.active.tabChange('mask');
				break;
			case 'start':
				updateExam('start.do',{id:data.id});
				break;
			case 'cancel':
				updateExam('cancel.do',{id:data.id});
				break;
			case 'finish':
				updateExam('finish.do',{id:data.id});
				break;
		}
	});

	function updateExam(url,data){
		$.ajax({
			  type:"post",
			  url:url,
			  dateType:"json",
			  contentType : 'application/json',
			  data:JSON.stringify(data),
			  success:function(result){
				  if(result.flag){
					  reloadTable({});
					  commons.showInfo(result.message);
					  return;
				  }
				  layer.msg(result.message);
			  }
		  });
	}

	form.on('submit(exportData)', function(data) {
		// $.extend(true, data.field, _query);
		_query = data.field;
		$('#form1').form(
			{
				url : 'exportData.do',
				onSubmit : function(param) {
					$.extend(true, _query, param);

				}
			});
		$('#form1').submit();

		// submitForm(data.field);
		return false;
	});

	form.on('submit(query)', function(data) {
		_query = data.field;
		reloadTable(data.field);
		return false;
	});

});

function submitForm(obj) {
	 // var checkStatus = table.checkStatus('idTest')
     // ,data = checkStatus.data;
     // layer.alert(JSON.stringify(data));
	$('#form1').form(
		{
			url : 'exportData.do',
			onSubmit : function(param) {
				$.extend(true, _query, param);

				$.extend(true, param, obj);
			}
		});
	$('#form1').submit();
}
