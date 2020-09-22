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
			height : 'full-170',
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
							title : '课程类型',
							width : 90,
							align : 'center',
							templet:function(d){return ['通用培训','资质培训'][d.type]}
						},
						{
							field : 'name',
							title : '课程名称',
							width : 120,
							align : 'center'
						},
						{
							field : 'remark',
							title : '备注',
							width : 240
						},
						{
							field : 'state',
							title : '状态',
							align : 'center',
							width : 60,
							templet : '#state'
						} ] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform', _query);
				table.reload('position',{
					where:{courseId:0},
					data:[]
				});
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
						where:{state:1},
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
							layer.msg(res.message);
						}
					});
					f = false;
				}
			}
		});

	// 查询岗位明细列表
	table.on('row(test)', function(obj){
		$(obj.tr).siblings().css('background','white').css('color','black');
		$(obj.tr).css('background','#009688').css('color','white');
		table.reload('position',{
			url : basePath+'position/queryList.do?courseId='+obj.data.id,
			done : function () {
				$("#courseId").val(obj.data.id);
			}
		});

	});

	table.render(
			{
				id : 'position',
				elem : '#demo1',
				height : '170',
				title : '岗位列表',
				data : [],
				method : 'post',
				page:false,
				defaultToolbar : [],
				totalRow : false,
				toolbar : '#toolbarson',
				cols : [ [
							{
								type : 'checkbox'
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
								width : 110,
								align : 'center'
							},
							{
								field : 'workShop',
								title : '科室/车间',
								width : 110,
								align : 'center'
							},
							{
								field : 'workGroup',
								title : '班组',
								width : 110,
								align : 'center'
							},
							{
								title : '岗位性质',
								width : 80,
								align : 'center',
								templet:function(d){return ['普通岗','关键岗'][d.type]}
							},
							{
								field : 'name',
								title : '岗位名称',
								width : 120,
								align : 'center'
							},
							{
								field : 'remark',
								title : '备注',
								width : 80
							}
							] ],
				done : function(res) {
				}
			});

	var checklist = [];
	var initTable = function() {
		table.render(
			{
				id : 'deptTable',
				elem : '#dept',
				height : '340',
				method : 'post',
				data : [],
				title : '岗位列表',
				limit : 10,
				limits : [ 10, 15, 20, 30, 50, 100 ],
				page :
					{
						layout : [ 'limit', 'prev', 'page', 'next', 'skip', 'count' ],
						groups : 5
					},
				defaultToolbar : [],
				totalRow : false,
				cols : [ [
					{
						type : 'checkbox',
						fixed : 'left'
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
						field : 'name',
						title : '岗位',
						align : 'center'
					}, ] ],
				done : function(res) {
					checklist = res.data;
				}
			});
	}
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
		$('[lay-filter=frm1]')[0].reset();
		$('#xx').html('');
	}

	// 监听提交
	form.on('submit(tijiao)', function(data) {
		if(data.field.type==1){
			if(postions.length==0){
				layer.msg('请选择岗位')
				return false;
			}
		}
		debugger;
		data.field.positions = postions;
		return rest.formSubmit(data);
	});
	// 监听提交
	form.on('submit(query2)', function(data) {
		table.reload('deptTable',
			{
				url : basePath + 'position/queryPage1.do',
				where : data.field,
				parseData : function(res) {
					var data = res.data;
					data.forEach(function(item) {
						if (postions.findIndex(function(obj) {
							return item.id === obj.id;
						}) >= 0) {
							item.LAY_CHECKED = true;
						}
					});
					return res;
				}
			});
		return false;
	});

	var postions = [];
	function addPosition(obj) {
		if (postions.find(function(item) {
			return item.id === obj.id;
		}) === undefined) {
			postions.push(obj);
		}
	}
	function addPositions(list) {
		for (var i = 0; i < list.length; i++) {
			addPosition(list[i]);
		}
	}
	function removePosition(obj) {
		var index = postions.findIndex(function(item) {
			return item.id == obj.id;
		});
		postions.splice(index, 1);
	}
	function removePositions(list) {
		for (var i = 0; i < list.length; i++) {
			removePosition(list[i]);
		}
	}
	window.removeThis = function(el){
		removePosition({id:$(el).attr('data-id')});
		$(el).parent().remove();
		table.reload('deptTable');
	}
	function getstring() {
		return postions.map(
				function(obj) {
					return '<span class="layui-badge layui-bg-green" style="width:380px;text-align:left;margin-left:3px;margin-bottom:3px">' + (obj.workGroup==null?'':obj.workGroup + '－') + obj.name
							+ '<i data-id="'+obj.id+'" class="layui-icon" style="float:right;cursor: pointer;color:white;" onclick="removeThis(this)">&#x1006;</i></span>';
				}).toString().replace(/,/g, '');
	}
	table.on('checkbox(dept)', function(obj) {
		var data = obj.data;
		if (obj.type == 'all') {
			if (obj.checked) {
				addPositions(checklist);
			} else {
				removePositions(checklist);
			}
		} else {
			if (obj.checked) {
				addPosition(data);
			} else {
				removePosition(data);
			}
		}
		$('#xxx').html(getstring());
	});

	$('#checkdept').click(function(data) {
		var deptId = $('#workShopId').val();
		if(deptId==null||deptId==''){
			layer.msg('请选择科室/车间!');
			return;
		}
		table.reload('deptTable',
				{
					url : basePath + 'position/queryPage1.do',
					where : {departmentId:deptId},
					parseData : function(res) {
						var data = res.data;
						data.forEach(function(item) {
							if (postions.findIndex(function(obj) {
								return item.id === obj.id;
							}) >= 0) {
								item.LAY_CHECKED = true;
							}
						});
						return res;
					}
				});
		layer.open(
			{
				type : 1,
				content : $('#dlg1'),
				title : '添加岗位',
				area : [ '640px', '425px' ],
				cancel:function(){
					$('#dlg1').hide();
				}
			});
	});
	$('.btn-cancel').click(function(e) {
		var id = $(e.target).parents('.layui-layer').attr("id").replace("layui-layer", "");
		layer.close(id);
	});
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
		switch (obj.event) {
		case 'create':
			$('.zzkc').hide();
			loadKeshi(-1);
			reset();
			clearxxx();
			initTable();
			rest.config.idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '添加课程',
					area : [ '600px', '425px' ],
					cancel:function(){
						$('#dlg').hide();
					}
				});
			break;
		case 'reset':
			document.getElementById('form1').reset();
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
			layer.open(
				{
					title : '查看课程信息',
					skin : 'layui-layer-molv',
					type : 1,
					area : [ '660px', '450px' ],
					closeBtn : 1,
					shade : 0.5,
					content : '<form class="layui-form">'
							+ '<div class="layui-form-item"><label class="layui-form-label">课程名称</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + data.name + '</div></div>'
							+ '<div class="layui-form-item"><label class="layui-form-label">岗位</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + data.positionName + '</div></div>'
							+ '<div class="layui-form-item"><label class="layui-form-label">备注</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + data.remark + '</div></div>'
							+ '<div class="layui-form-item"><label class="layui-form-label">状态</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + (data.userState == 1 ? '启用' : '禁用') + '</div></div>'
							+ '</form>'
				});
			break;
		case 'del':
			if(data.length!=1){
				layer.msg('请选择一条数据')
				return;
			}
			data = data[0];
			rest.deleteMethod(data.id);
			break;
		case 'enable':
			if(data.length!=1){
				layer.msg('请选择一条数据')
				return;
			}
			data = data[0];
			rest.enableMethod(data.id);
			break;
		case 'edit':
			if(data.length!=1){
				layer.msg('请选择一条数据')
				return;
			}
			data = data[0];
			if(data.type==0){
				$('.zzkc').hide();
			}else{
				$('.zzkc').show();
			}
			clearxxx();
			if(data.positions!=null)
			postions = data.positions.concat([]);
			debugger;
			$('#xxx').html(getstring());
			initTable();
			loadKeshi(data.departmentId,false);
			form.val('frm1', data);
			rest.config.idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '编辑课程',
					area : [ '600px', '425px' ],
					cancel:function(){
						$('#dlg').hide();
					}
				});
			break;
		}
	});
	// 监听头工具栏事件
		table.on('toolbar(test1)', function(obj) {
			var checkStatus = table.checkStatus(obj.config.id),
				data = checkStatus.data; // 获取选中的数据

			switch (obj.event) {

			case 'deleteGW':
				if (data.length != 1) {
					layer.msg('请选择一条课程下的数据岗位')
					return;
				}
				rest.config.deleteUrl = 'deleteGW.do';
				rest.delFun('确定删除选中数据？', {id: data[0].id});
				break;
			 case 'selectGW':
				 var obj = $("#form3").serializeObject();
				 if (obj.courseId === ''){
					 layer.msg('请选择一条课程下的数据岗位');
					 return;
				 }
				 table.reload("position", {
					 where : obj,
					 done : function(res){
						 form.val('queryform3', obj);
					 }
				 });
				break;
			}
		});
	// 监听行工具事件
	table.on('tool(test)', function(obj) { // 注：tool 是工具条事件名，test 是 table
											// 原始容器的属性 lay-filter="对应的值"
		if (obj === false)
			return;
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			layer.open(
				{
					title : '查看课程信息',
					skin : 'layui-layer-molv',
					type : 1,
					area : [ '660px', '450px' ],
					closeBtn : 1,
					shade : 0.5,
					content : '<form class="layui-form">'
							+ '<div class="layui-form-item"><label class="layui-form-label">课程名称</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + data.name + '</div></div>'
							+ '<div class="layui-form-item"><label class="layui-form-label">岗位</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + data.positionName + '</div></div>'
							+ '<div class="layui-form-item"><label class="layui-form-label">备注</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + data.remark + '</div></div>'
							+ '<div class="layui-form-item"><label class="layui-form-label">状态</label>'
							+ '<div class="layui-input-block" style="line-height:22px">' + (data.userState == 1 ? '启用' : '禁用') + '</div></div>'
							+ '</form>'
				});
		} else if (layEvent === 'del') {
			rest.deleteMethod(data.id);
		} else if (layEvent === 'enable') {
			rest.enableMethod(data.id);
		} else if (layEvent === 'edit') {
			clearxxx();
			if(data.positions!=null)
			postions = data.positions.concat([]);
			$('#xxx').html(getstring());
			initTable();
			form.val('frm1', data);
			rest.config.idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '编辑课程',
					area : [ '600px', '425px' ],
					cancel:function(){
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

	combo.set({
		valueField:'code',
		elem : 'departmentId',
		param:{level:'二级'},
		url : basePath+'department/queryClass2.do'
	}).render();

	form.on('select(departmentId)', function(data){
		var deptId = data.value;
		if(deptId=='')
			deptId=-1;
		loadKeshi(deptId);
	});

	form.on('select(type)', function(data){
		if(data.value==1){
			$('.zzkc').show();
		}else{
			$('.zzkc').hide();
		}
	});

	function loadKeshi(deptId,isasync){
		try {
			combo.set({
				valueField:'code',
				elem : 'workShopId',
				param:{parentId:deptId},
				async : isasync===undefined?true:isasync,
				url : basePath+'department/queryClass2.do'
			}).render();
		} catch (e) {
			layer.msg(e.msg)
		}
	}

	var clearxxx = function() {
		postions = [];
		$('#xxx').html('');
	}

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






function updateExam(){
	$.ajax({
		  type:"post",
//		  url:"../positionNature/queryList.do",
//		  url:"../positionNature/create.do",
//		  url:"../positionNature/update.do",
		  url:"../positionNature/delete.do",
		  dateType:"json",
		  contentType : 'application/json',
		  data:JSON.stringify(),
		  success:function(result){
			  if(result.flag){
				  reloadTable({});
				//  commons.showInfo(result.message);
				//  return;
				  alert('111111111 ');

			  }else{
				  alert('22222 ');
			  }
			  layer.msg(result.message);
		  }
	  });
}

