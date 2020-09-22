layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'laydate'], function() {
	var _query = {};
	var _data = [];
	var laypage = layui.laypage // 分页
	, layer = layui.layer // 弹层
	, table = layui.table // 表格
	, element = layui.element // 元素操作
	, slider = layui.slider // 滑块
	, form = layui.form
	, commons = layui.commons() // 右下角提示
	, $ = layui.jquery
	, combo = layui.combo()
	, laydate = layui.laydate;
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
			url : basePath+'qualify/queryPage.do',
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
							type : 'numbers',
							title : '序号',
							align : 'center',
							width : 70
						},
						{
							field : 'userCode',
							title : '工号',
							align : 'center',
							width : 100
						},
						{
							field : 'username',
							title : '姓名',
							align : 'center',
							width : 100
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
							field : 'workGroup',
							title : '班组',
							align : 'center',
							width : 100
						},
						{
							field : 'post',
							title : '岗位',
							align : 'center',
							width : 100
						},
						{
							field : 'beginDate',
							title : '生效时间',
							align : 'center',
							width : 100
						},
						{
							field : 'endDate',
							title : '失效时间',
							align : 'center',
							width : 100
						},
						{
							templet : function(d){return ['正常','待复审','过期','复审中','复审未通过'][d.state*1]},
							title : '状态',
							align : 'center',
							width : 100
						}
						] ],
			done : function(res) {
				lay('.test-item').each(function () {
			        laydate.render({
			            elem: this
			            , format: 'yyyy-MM-dd'   
			            , trigger: 'click'
			        });
			    });
				_data = res.data;
				layer.close(index1);
				form.val('queryform', _query);
				var that = this.elem.next();
		          res.data.forEach(function (item, index) {
		        	  if(item.state==4){
			        	  var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "red").css("color", "white");
			          }else if(item.state==3){
			        	  var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "blue").css("color", "white");
			          }else if(item.state==2){
			        	  var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "#FF2400").css("color", "white");
			          }else if (item.state==1) {
			              var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "yellow");
			          }else {
			              var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "green").css("color", "white");;
			          }
		          });
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
	
	/*table.on('checkbox(test)', function(obj){
		if(obj.checked){
			table.reload('userTable',{data:_data});
		}	
	});*/
	
	$('.btn-cancel').click(function(e) {
		var id = $(e.target).parents('.layui-layer').attr("id").replace("layui-layer", "");
		layer.close(id);
	});
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
		switch (obj.event) {
		case 'start':
			$('#fushen')[0].reset();
			idx = layer.open({
				type: 1,
		    	content: $('#dlg'),
		    	title:'发布复审',
		    	area:['400px','250px']
				,cancel:function(){
					$('#dlg').hide();
				}
		    });
			break;
		case 'reset':
			$('#form1')[0].reset();
			break;
		}
	});
	var idx = 0;

	form.on('submit(query)', function(data) {
		_query = data.field;
		reloadTable(data.field);
		return false;
	});
	
	form.on('submit(tijiao)', function(data) {
		var checkStatus = table.checkStatus('userTable'), dat = checkStatus.data;
		if(dat.length==0){
			layer.msg('请选择复审人员');
			return false;
		}
		data.field.userlist = dat;
		var id1 =layer.load();
		$.ajax({
			  type:"post", 
			  url:'fushen.do', 
			  dateType:"json", 
			  contentType : 'application/json', 
			  data:JSON.stringify(data.field), 
			  success:function(result){
				  layer.closeAll();
				  if(result.flag){
					  reloadTable({});
					  commons.showInfo(result.message);
					  return;
				  }
				  //layer.close(id1);
				  layer.msg(result.message);
			  }
		  });
		return false;
	});

});