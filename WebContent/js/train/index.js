layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload', 'laydate'], function() {
	var laypage = layui.laypage
	, layer = layui.layer
	, table = layui.table
	, element = layui.element
	, form = layui.form
	, commons = layui.commons()
	, $ = layui.jquery
	, combo = layui.combo()
	, laydate = layui.laydate;
	var idx = 0;
	var _query = {};
	var isopEN = false;
	var wh = ($(window).height()-10)+'px';
	lay('.test-item').each(function () {
        laydate.render({
            elem: this
            , format: 'yyyy-MM-dd'
            , trigger: 'click'
			, range: '~'
			, done: function (value) {
				if(value){
					let strArr = value.split('~');
					$('#beginDate').val(strArr[0].trim());
					$('#endDate').val(strArr[1].trim());

					$('#beginDate3').val(strArr[0].trim());
					$('#endDate3').val(strArr[1].trim());
				}else{
					$('#beginDate').val('');
					$('#endDate').val('');

					$('#beginDate3').val('');
					$('#endDate3').val('');
				}
			}
        });
    });
	var index1 = layer.load(1,
		{
			shade : [ 0.5, '#ccc' ]
		});
	var teach = combo.set({
		elem : 'teacher',
		valueField : 'usercode',
		url : 'queryUserbypostion.do'
	});
	teach.render();

	table.render(
		{
			id : 'userTable',
			elem : '#demo',
			height : 'full-0',
			method : 'post',
			url : basePath + 'train/queryPage1.do',
			title : '培训列表',
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
					,{ type : 'numbers', title : '序号', width : 60 , align: 'center' }
					,{ field : 'courseName', title : '培训课程', width : 200 , align:'center'}
					,{ field : 'name1', title : '指导人', width : 120 , align:'center'}
					,{ title : '课程类型', width : 80 , align:'center',templet : function(d){return ['通用培训','资质培训'][d.type];}}
					,{ field : 'department', title : '部门', width : 150 , align:'center'}
					,{ field : 'workShop', title : '科室/车间', width : 150 , align:'center'}
					,{ field : 'workGroup', title : '班组', width : 150 , align:'center'}
					,{ field : 'position', title : '培训岗位', width : 150 , align:'center'}
					,{ field : 'username', title : '培训人员', width : 150 , align:'center'}
					,{ field : 'beginDate', title : '开始日期', width : 120 , align:'center'}
					,{ field : 'endDate', title : '结束日期', width : 120 , align:'center'}
					,{ field : 'begintime', title : '开始时间', width : 120 , align:'center'}
					,{ field : 'endtime', title : '结束时间', width : 120 , align:'center'}
					,{ title : '状态', width : 80 , align:'center', templet : function(d){return ['已取消', '正常', '已结束'][d.state];}}
					//,{ title : '操作', templet : '#barDemo', width : 160,fixed:'right', align:'center'}
				] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform',_query);
			}
		});

	function initUserTable(where){
		$('[name=query]').val('');
		table.render(
				{
					id : 'user',
					elem : '#user',
					height : 'full-55',
					url:'queryUserbypostion.do',
					where:where,
					method : 'post',
					title : '人员列表',
					page : false,
					toolbar : '#toolbar1',
					defaultToolbar : [],
					totalRow : false,
					parseData : function(res) {
						var data = res.data;
						data.forEach(function(item) {
							if ($('.person[data-id='+item.usercode+']').length>0) {
								item.LAY_CHECKED = true;
							}
						});
						return res;
					},
					cols : [ [
							 { type : 'checkbox', fixed : 'left'}
							 ,{ field : 'department', title : '部门', width : 110 , align:'center'}
							 ,{ field : 'workShop', title : '科室/车间', width : 110 , align:'center'}
							 ,{ field : 'dept', title : '班组', width : 110 , align:'center'}
							 ,{ field : 'usercode', title : '工号', width : 130 , align:'center'}
							 ,{ field : 'name', title : '姓名', width : 115, align:'center'}
						] ],
					done : function(res) {
						form.val('queryform',_qr);
					}
				});
	}
	window.reloadTable = function(param) {
		index1 = layer.load(1,
				{
					shade : [ 0.5, '#ccc' ]
				});
		if(param&&param.departmentId&&param.departmentId>0){
			_departmentId = param.departmentId;
		}
		if(isopEN){
			var where = {};
			if(_departmentId&&_departmentId>0){
				where.departmentId = _departmentId;
			}
			where.query = $('#query').val();
			table.reload('user',{
				url:'queryUserbypostion.do',
				where:where
			});
		}
		table.reload("userTable",{where:param});
	};

	form.on('submit(query)', function(data) {
		_query = data.field;
		reloadTable(data.field);
		return false;
	});
	var _qr = {};
	form.on('submit(queryuser)', function(data) {
		_qr = data.field;
		data.field.id = $('#position').val();
		table.reload('user',{
			url:'queryUserbypostion.do',
			where:data.field
		});
		return false;
	});

	form.on('submit(tijiao)', function(data) {
		var arr = [];
		$('#fbpx').find('.person').each(function(e){
			arr.push($(this).attr('data-id'));
		});
		if(arr.length==0){
			layer.msg('请选择培训人员');
			return false;
		}
		var cour = [];
		var obj = {};
		obj.courseId = data.field.courseId;
		obj.usercode = data.field.teacher;
		cour.push(obj);
		data.field.courseList = cour;
		data.field.type = 0;
		data.field.ids = arr.join(',');
		var row = JSON.stringify(data.field);
		_query = data.field;
		if(_query.beginDate >_query.endDate){
			layer.msg('开始时间不能大于结束时间');
			return false;
		}
		var url = "create.do";
		if(data.field.id){
		  url = "update.do"
		}
		var indexx = layer.load();
		$.ajax({
		  type:"post",
		  url:url,
		  dateType:"json",
		  contentType : 'application/json',
		  data:row,
		  success:function(result){
			  layer.close(indexx);
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
	});

	form.on('submit(submitBtn)', function(data) {
		var arr = [];
		var cour = [];
		$('.course').each(function(e){
			var id = $(this).attr('data-id');
			var obj = {};
			obj.courseId = id;
			obj.usercode=data.field['teacher'+id];
			cour.push(obj);
		});
		if(cour.length==0){
			layer.msg('请选择课程');
			return false;
		}
		data.field.courseList = cour;
		$('#fbpx2').find('.person').each(function(e){
			arr.push($(this).attr('data-id'));
		});
		if(arr.length==0){
			layer.msg('请选择培训人员');
			return false;
		}
		data.field.type = 0;
		data.field.ids = arr.join(',');
		var row = JSON.stringify(data.field);
		_query = data.field;
		var url = "createI.do";
		if(_query.beginDate >_query.endDate){
			layer.msg('开始时间不能大于结束时间');
			return false;
		}
		var indexx = layer.load();
		$.ajax({
			type:"post",
			url:url,
			dateType:"json",
			contentType : 'application/json',
			data:row,
			success:function(result){
				layer.close(indexx);
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
	});

	form.on('submit(submit)', function(data) {
		var indexx = layer.load();
		$.ajax({
			  type:"post",
			  url:'stop.do',
			  dateType:"json",
			  contentType : 'application/json',
			  data:JSON.stringify(data.field),
			  success:function(result){
				  layer.close(indexx);
				  if(result.flag){
					  reloadTable({});
					  layer.close(idx);
					  $('#dlg1').hide();
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


	form.on('select(postionId)',function(obj){
		var indexx = layer.load();
		$.ajax({
			  type:"post",
			  url:basePath+'course/queryList.do',
			  dateType:"json",
			  data:{positionId:obj.value,type:1},
			  success:function(result){
				  layer.close(indexx);
				  if(result.flag){
					  $('#courseList').html('').append(result.data.map(function(cobj){
						  return '<div class="layui-form-item"><label class="layui-form-label" style="width:250px;text-align:right;"><span style="height:22px;line-height:22px;" class="layui-badge layui-bg-blue course" data-id="'+cobj.id+'">'+cobj.name+'</span>&nbsp;指导人</label>'
						  + '<div class="layui-input-inline" style="padding-top: 1px;"><select name="teacher'+cobj.id+'" class="teacher layui-input" style="width:100px;" required lay-verify="required" lay-search></select></div></div>';
					  }).join(''));
					  $('.teacher').each(function(e){
						  combo.set({
							  elem:$(this),
							  valueField : 'usercode',
							  data:teach.config.data
						  }).render();
					  });
					  form.render('checkbox');
					  return;
				  }
				  layer.msg(result.message);
			  }
		  });
	});

	table.on('tool(test)', function(obj) {
		if (obj === false)
			return;
		var data = obj.data
		, layEvent = obj.event;
		if(layEvent === 'cancel'){
			var url = 'cancel.do';
			$.ajax({
				  type:"post",
				  url:url,
				  dateType:"json",
				  contentType : 'application/json',
				  data:JSON.stringify({id:data.id}),
				  success:function(result){
					  if(result.flag){
						  reloadTable({});
						  commons.showInfo(result.message);
						  return;
					  }
					  layer.msg(result.message);
				  }
			  });
		}else if (layEvent === 'stop') {
			form.val('frm2',{id:data.id});
			idx = layer.open({
				type: 1,
		    	content: $('#dlg1'),
		    	title:'结束培训',
		    	area:['350px','250px']
				,cancel:function(){
					$('#dlg1').hide();
				}
		    });
		}
	});

	var layid = 0;

	var target;
	$('.chooseUser').click(function(){
		target = $('#'+$(this).attr('target'));
//		if($(this).attr('id')=='chooseuser'){
//			if($('#postionId').val()==null){
//				layer.msg('请选择岗位!');
//				return;
//			}
//		}
		var where = {departmentId:_departmentId};
		initUserTable(where);
		isopEN = true;
		layid = layer.open({
			type: 1,
	    	content: $('#dlg2'),
	    	title:'选择人员',
	    	area:['650px',wh]
			,cancel:function(){
				$('#dlg2').hide();
			}
	    });
	});
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data;
		switch (obj.event) {
			case 'create':
				$('#fbpx')[0].reset();
				$('.userlist').html('');
				idx = layer.open({
					type: 1,
			    	content: $('#dlg'),
			    	title:'发布通用培训',
			    	area:['650px', wh]
					,cancel:function(){
						$('#dlg').hide();
					}
			    });
				break;
			case 'create2':
				$('#fbpx2')[0].reset();
				$('.userlist').html('');
				$('#courseList').html('');
				idx = layer.open({
					type: 1,
			    	content: $('#dlg3'),
			    	title:'发布资质培训',
			    	area:['650px', wh]
					,cancel:function(){
						$('#dlg3').hide();
					}
			    });
				break;
			case 'reset':
				$('#queryform')[0].reset();
				break;
			case 'cancel':
				if(data.length!=1){
					layer.msg('请选择一条数据')
					return;
				}
				data = data[0];
				if(data.state!=1){
					layer.msg('状态错误')
					return;
				}
				var url = 'cancel.do';
				$.ajax({
					  type:"post",
					  url:url,
					  dateType:"json",
					  contentType : 'application/json',
					  data:JSON.stringify({id:data.id}),
					  success:function(result){
						  if(result.flag){
							  reloadTable({});
							  commons.showInfo(result.message);
							  return;
						  }
						  layer.msg(result.message);
					  }
				  });
				break;
			case 'stop':
				if(data.length!=1){
					layer.msg('请选择一条数据')
					return;
				}
				data = data[0];
				if(data.state!=1){
					layer.msg('状态错误')
					return;
				}
				frm2.reset();
				form.val('frm2',{id:data.id});
				idx = layer.open({
					type: 1,
			    	content: $('#dlg1'),
			    	title:'结束培训',
			    	area:['350px','250px']
					,cancel:function(){
						$('#dlg1').hide();
					}
			    });
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
								url : 'hideOropen.do?state1=1&ids='+data.map(x=>x.id).join(','),
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
				layer.confirm('确定开启数据？',
					{
						btn : [ '确定', '取消' ]
					}, function(index) {
						layer.close(index);
						$.ajax(
							{
								type : "post",
								url : 'hideOropen.do?state1=0&ids='+data.map(x=>x.id).join(','),
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

	window.removeThis = function (obj) {
		$(obj).parent().remove();
	}

	// 监听头工具栏事件
	table.on('toolbar(user)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data;
		switch (obj.event) {
		case 'adduser':
			//$('.userlist').html('');
			var data1 = [];
			data.forEach(function(obj){
				if($('.person[data-id='+obj.usercode+']').length==0){
					data1.push(obj);
				}
			});
			data1.forEach(function(obj){
				target.append('<span class="layui-badge layui-bg-green" style="margin-right:3px;margin-top:3px">'
									+ obj.name
									+ '<i data-id="'+obj.usercode+'" class="layui-icon person" style="float:right;cursor: pointer;color:white;" onclick="removeThis(this)">&#x1006;</i></span>');
			});
			layer.close(layid);
			break;
		case 'closethis':
			layer.close(layid);
			break;
		}
	});

	combo.set({
		valueField:'code',
		elem : 'departmentId',
		param:{level:'二级'},
		url : basePath+'department/queryClass2.do'
	}).render();

	combo.set({
		valueField:'code',
		elem : 'department',
		param:{level:'二级'},
		url : basePath+'department/queryClass2.do'
	}).render();

	var _departmentId = '';
	form.on('select(departmentId)', function(data){
		$('#courseList').html('');
		var deptId = data.value;
		loadBanzu('');
		loadPosition('');
		loadKeshi(deptId);
	});

	form.on('select(department)', function(data){
		var deptId = data.value;
		loadKeshi1(deptId);
	});

	form.on('select(deptId)', function(data){
		$('#courseList').html('');
		var deptId = data.value;
		loadBanzu(deptId);
		loadPosition(deptId,0);
	});

	function loadPosition(deptId,workGroupId){
		var param = {};
		if(workGroupId==0){
			param.deptId = deptId;
		}else{
			param.workGroupId = workGroupId;
		}
		combo.set({
			valueField:'id',
			elem : 'postionId',
			data : deptId===''?[]:undefined,
			url : basePath+'position/queryList.do',
			param : param
		}).render();
	}

	combo.set({
		elem : 'courseId',
		valueField : 'id',
		url : basePath+'course/queryList.do',
		param : {type:0}
	}).render();

	form.on('select(workGroupId)', function(data){
		$('#courseList').html('');
		var deptId = data.value;
		loadPosition(0,deptId);
	});

	function loadKeshi(deptId){
		try {
			combo.set({
				valueField:'code',
				elem : 'deptId',
				param:{parentId:deptId},
				data : deptId==''?[]:undefined,
				url : basePath+'department/queryClass2.do'
			}).render();
		} catch (e) {
			layer.msg(e.msg)
		}
	}

	function loadKeshi1(deptId){
		try {
			combo.set({
				valueField:'code',
				elem : 'dept',
				param:{parentId:deptId},
				data : deptId==''?[]:undefined,
				url : basePath+'department/queryClass2.do'
			}).render();
		} catch (e) {
			layer.msg(e.msg)
		}
	}

	form.on('submit(exportData)', function(data) {
		$.extend(true, data.field, _query);
		submitForm(data.field);
		return false;
	});

	function loadBanzu(deptId){
		try {
			combo.set({
				valueField:'code',
				elem : 'workGroupId',
				param:{parentId:deptId},
				data : deptId==''?[]:undefined,
				url : basePath+'department/queryClass2.do'
			}).render();
		} catch (e) {
			layer.msg(e.msg)
		}
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
