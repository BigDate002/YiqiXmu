layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload'], function() {
	var idx = 0;
	var f = true;
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
			url : basePath + 'position/queryPage.do',
			title : '角色列表',
			limit : 10,
			limits : [ 10, 15, 20, 30, 50, 100 ],
			where : {state:1},
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
				, {
					type: 'numbers',
					title: '序号',
					align : 'center',
					width : 60
				}
				, { field : 'name', title : '岗位名称', width : 120 , align:'center'}
				, { field : 'type', title : '岗位性质', width : 100, templet : function(d){return ['普通岗','关键岗'][d.type];} , align:'center'}
				, { field : 'postCategoryStr', title : ' 岗位类别', width : 120, align:'center'}
				, { field : 'department', title : ' 部门', width : 120, align:'center'}
				, { field : 'workShop', title : '科室/车间', width : 120, align:'center'}
				, { field : 'workGroup', title : '班组', width : 120, align:'center'}
				, { field : 'examPeriod', title : '复审周期(月)', width : 120, align:'center'}
				, { field : 'state', title : '状态',width: 55,templet: function(d){return d.state==1?'启用':'禁用'}}
				, { field : 'remark', title : '备注'}
				//, { title : '操作', fixed : 'right', toolbar : '#barDemo', width:120}
				] ],
			done : function(res) {
				layer.close(index1);
				form.val('queryform',_query);
				if(f){
					upload.render({
					    elem: $('[lay-event=importExcel]')
					  , url: 'importData.do'
					  , accept: 'file'
					  , field: 'filename'
					  , acceptMime: '.xlsx'
					  , exts: 'xlsx'
				      , multiple: true
				      ,before: function(obj){
						    layer.load();
					   }
					  , done: function(res){
						  layer.closeAll();
					      if(res.flag){
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

	window.reloadTable = function(param) {
		index1 = layer.load(1,
				{
					shade : [ 0.5, '#ccc' ]
				});
		$.extend(true, param, param);
		f = true;
		table.reload("userTable",{where:param});
	};

	form.on('submit(query)', function(data) {
		_query = data.field;
		reloadTable(data.field);
		return false;
	});

	form.on('submit(exportData)', function(data) {
		debugger
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
			  url:flag==1?'grant1.do':'grant.do',
			  dateType:"json",
			  contentType : 'application/json',
			  data:JSON.stringify({ids:ids}),
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
			layer.open({
				title:'查看岗位信息',
				skin : 'layui-layer-molv',
				type:1,
				area:['540px','420px'],
				closeBtn:1,
				shade:0.5,
				content: '<form class="layui-form">'
						+'<div class="layui-form-item"><label class="layui-form-label">部门</label>'
						+'<div class="layui-input-block" style="line-height:22px">'+data.department+'</div></div>'
						+'<div class="layui-form-item"><label class="layui-form-label">科室/车间</label>'
						+'<div class="layui-input-block" style="line-height:22px">'+data.workShop+'</div></div>'
						+'<div class="layui-form-item"><label class="layui-form-label">岗位名称</label>'
						+'<div class="layui-input-block" style="line-height:22px">'+data.name+'</div></div>'
						+'<div class="layui-form-item"><label class="layui-form-label">岗位性质</label>'
						+'<div class="layui-input-block" style="line-height:22px">'+(data.type==1?'关键':'普通岗')+'</div></div>'
						+'<div class="layui-form-item"><label class="layui-form-label">备注</label>'
						+'<div class="layui-input-block" style="line-height:22px">'+data.remark+'</div></div>'
						+'<div class="layui-form-item"><label class="layui-form-label">状态</label>'
						+'<div class="layui-input-block" style="line-height:22px">'+(data.state==1?'启用':'禁用')+'</div></div>'
						+'</form>'
			});
		} else if (layEvent === 'del') {
			deleteMethod(data.id)
		} else if (layEvent === 'edit') {
			loadKeshi(data.departmentId,false);
			loadBanzu(data.deptId,false);
			loadPostCategory(false);
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
	form.on('select(deptId)', function(data){
		var deptId = data.value;
		if(deptId=='')
			deptId=-1;
		loadBanzu(deptId);
	});
	function loadKeshi(deptId,isasync){
		try {
			combo.set({
				valueField:'code',
				elem : 'deptId',
				async : isasync===undefined?true:isasync,
				param:{parentId:deptId},
				url : basePath+'department/queryClass2.do'
			}).render();
		} catch (e) {
			layer.msg(e.msg)
		}
	}


	function loadPostCategory(isasync){
		try {
			combo.set({
				textField:'dictLabel',
				valueField:'dictValue',
				elem : 'postCategory',
				async : isasync===undefined?true:isasync,
				param:{dictType:'sys_post_category'},
				url : basePath+'dict/queryDict.do'
			}).render();
		} catch (e) {
			layer.msg(e.msg)
		}
	}


	function loadBanzu(deptId,isasync){
		try {
			combo.set({
				valueField:'code',
				elem : 'workGroupId',
				param:{parentId:deptId},
				async : isasync===undefined?true:isasync,
				url : basePath+'department/queryClass2.do'
			}).render();
		} catch (e) {
			layer.msg(e.msg)
		}
	}

	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data;
		switch (obj.event) {
			case 'create':
				loadKeshi(-1);
				loadBanzu(-1);
				loadPostCategory(false);
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
				layer.open({
					title:'查看岗位信息',
					skin : 'layui-layer-molv',
					type:1,
					area:['540px','420px'],
					closeBtn:1,
					shade:0.5,
					content: '<form class="layui-form">'
							+'<div class="layui-form-item"><label class="layui-form-label">部门</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.department+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">科室/车间</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.workShop+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">岗位名称</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.name+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">岗位性质</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+(data.type==1?'关键':'普通岗')+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">备注</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+data.remark+'</div></div>'
							+'<div class="layui-form-item"><label class="layui-form-label">状态</label>'
							+'<div class="layui-input-block" style="line-height:22px">'+(data.state==1?'启用':'禁用')+'</div></div>'
							+'</form>'
				});
				break;
			case 'edit':
				if(data.length!=1){
					layer.msg('请选择一条数据')
					return;
				}
				data = data[0];
				loadKeshi(data.departmentId,false);
				loadBanzu(data.deptId,false);
				loadPostCategory(false);
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
