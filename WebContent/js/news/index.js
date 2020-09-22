function downloadFile(id){
	$("#download").attr("src",'downloadFile.do?id='+id);
}
layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload'], function() {
	var idx = 0;
	var laypage = layui.laypage
	, upload = layui.upload
	, layer = layui.layer
	, table = layui.table
	, element = layui.element
	, slider = layui.slider
	, form = layui.form, commons = layui.commons()
	, $ = layui.jquery, combo = layui.combo();
    var ue = UE.getEditor('editor');
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
			url : basePath + 'news/queryPage.do',
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
				, { field : 'title', title : '标题', width : 300 , align:'left'}
				, { title : '附件(点击下载)', width : 300 , align:'left',templet:function(d){
					if(d.files!=null)return d.files.map(function(x){return '<button onclick="downloadFile('+x.id+')" class="layui-btn layui-btn-xs layui-btn-primary layui-bg-orange">'+x.fileName+'</button>';
					}).join(' ').toString();
				}}
				, { title : '操作', fixed : 'right', toolbar : '#barDemo', width:120}
				] ],
			done : function(res) {
				layer.close(index1);
			}
		});
		//执行实例
		var uploadInst = upload.render({
		   elem: '#test1'
		  , url: 'upload.do'
		  , accept : 'file'
		  , field : 'filename'
		  , multiple : false
		  , before : function(){
			  index1 = layer.load(1,
				{
				   content : '<br/><br/><label>     上传中....</label>',
				   icon:4,
				   time:60000,
				   shade : [ 0.4, '#eff' ]
				});
		  }
		  ,done: function(res){
			  layer.close(index1);
			  if(res.flag){
				  commons.showInfo('上传成功');
				  $('#file').append('<span class="layui-badge layui-bg-orange" style="width:auto;display:inline-block;text-align:left;margin-right:5px;margin-top:5px;">'
				  + res.message
				  + '<i data-title="'+res.message+'" data-id="'+res.msg+'" class="layui-icon fujian" style="float:right;cursor: pointer;color:white;" onclick="removeThis(this)">&#x1006;</i></span>');
			  }else{
				  commons.showInfo(res.message);
			  }
		  }
		});
		
		window.removeThis = function (obj) {
			$(obj).parent().remove();
		}
		form.on('submit(tijiao)', function(data) {
		  var files = [];
		  $('.fujian').each(function(e){
			  var obj = {};
			  obj.url = $(this).attr('data-id');
			  obj.fileName = $(this).attr('data-title');
			  files.push(obj);
		  });
		  data.field.files = files;
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
					  table.reload('userTable');
					  commons.showInfo(result.message);
					  return;
				  }
				  layer.msg(result.message);
			  }
		  });
		  return false;
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
			var files = '';
			if(data.files!=null){
				files = data.files.map(function(x){return '<button onclick="downloadFile('+x.id+')" class="layui-btn layui-btn-xs layui-btn-primary layui-bg-orange">'+x.fileName+'</button>';
				}).join(' ').toString();
				files = '<h4>附件:'+files+'</h4>';
			}
			layer.open({
				title:'查看详情',
				skin : 'layui-layer-molv',
				type:1,
				area:['640px','430px'],
				closeBtn:1,
				shade:0.5,
				content: '<center><h2>'+data.title+'</h2>'+files+'</center><div>'+data.content+'</div>'
			});
			//parent.showMessage('查看详情',5000);
		} else if (layEvent === 'delete') {
			layer.confirm('确定删除选中数据？',{
					btn : [ '确定', '取消' ]
				}, function(index) {
				layer.close(index);
				$.ajax(
					{
						type : "post",
						url : "delete.do?id=" + data.id,
						dateType : "json",
						contentType : 'application/json',
						success : function(result) {
							if (result.flag) {
								table.reload('userTable');
								commons.showInfo(result.message);
							}
						}
					});
			});
		} else if (layEvent === 'edit') {
			loadKeshi(data.departmentId);
			form.val('frm1',data);
			idx = layer.open({
				type: 1,
		    	content: $('#dlg'),
		    	title:'编辑公告',
		    	area:['800px','425px']
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
				document.getElementById('frm1').reset();
				$('#file').html('');
				idx = layer.open({
					type: 1,
			    	content: $('#dlg'),
			    	title:'添加公告',
			    	area:['800px','450px']
					,cancel:function(){
						$('#dlg').hide();
					}
			    });
				break;
		}
	});
});