layui.config(
	{
		base : basePath + 'js/',
		version : new Date().getTime()
	});
layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'combo', 'form', 'upload', 'laydate' ], function() {
	var idx = 0;
	var _query = {};
	var laypage = layui.laypage // 分页
	, layer = layui.layer // 弹层
	, table = layui.table // 表格
	, element = layui.element // 元素操作
	, slider = layui.slider // 滑块
	, form = layui.form, commons = layui.commons() // 右下角提示
	, $ = layui.jquery, combo = layui.combo(), upload = layui.upload
		, laydate = layui.laydate;
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
			url : basePath + 'specialCertificates/newClaim/queryPage.do',
			title : '人员列表',
			limit : 10,
			limits : [ 10, 15, 20, 30, 50, 100 ],
			where : {type:'0'},
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
					width : 120
					,align : 'center'
				},
				{
					field : 'workShop',
					title : '科室/车间',
					width : 120
					,align : 'center'
				},
				{
					field : 'dept',
					title : '班组',
					width : 120
					,align : 'center'
				},
				{
					field : 'usercode',
					title : '工号',
					width : 120
					,align : 'center'
				},
				{
					field : 'username',
					title : '姓名',
					width : 120
					,align : 'center'
				},
				{
					title : '办理机构',
					width : 120
					,align : 'center',
					templet : function(d) {
						return [ '个人办理', '公司办理' ][d.handlingAgency];
					}
				},
				{
					field : 'certificateName',
					title : '证件名称',
					width : 120
					,align : 'center'
				},
				{
					title : '提报时间',
					width : 110
					,align : 'center',
					templet:function(d){return d.reportingTime;}
				},
				{
					title : '业务类型',
					width : 100
					,align : 'center',
					templet : function(d) {
						return [ '新申领', '复审' ][d.type];
					}
				},
				{
					field : 'files',
					title : '附件',
					align : 'center',
					templet : function(d) {
						var images = "<div class='' >";
						if (null != d.files){
							d.files.forEach(function (value) {
								images += "<div><img style='width: 120px;height: 48px;' src=downloadimg.do?path=" + value.url +" ></div>";
							});
							images += "</dvi>";
						}
						return images;
					},
				},
				{
					field : 'status',
					title : '状态',
					width : 90,
					align : 'center',
					templet : function(d) {
						return [ "待复审","复审中","正常","过期" ][d.status];
					}
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

				layer.close(index1);
				form.val('queryform', _query);

			}
		});
	var f = true;
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
	window.removeThis = function (obj) {

		$(obj).parent().remove();
	}
	function reset() {
		// $('#idCardImg').empty();
		// $('#educationCertificateImg').empty();
		// $('#otherImg').empty();
		// $('#idCardHidden').val('');
		$('[lay-filter=frm1]')[0].reset();

	}

	// 监听提交
	form.on('submit(tijiao)', function(data) {
		if(data.field.deptId==''){
			data.field.deptId = data.field.workShopId;
		}
		if(data.field.idCardHidden===''){
			commons.showInfo('请上传身份证照片!');
			return false;
		}
		var files = [];
		$('.layui-upload-img').each(function(e){
			var obj = {};
			obj.url = $(this).attr('data-path');
			obj.fileName = $(this).attr('data-filename');
			obj.fileType = $(this).attr('data-type');
			files.push(obj);
		});
		data.field.files = files;
		var row = JSON.stringify(data.field);
		var url = "create.do";
		if (data.field.id) {
			url = "update.do"
		}
		$.ajax(
			{
				type : "post",
				url : url,
				dateType : "json",
				contentType : 'application/json',
				data : row,
				success : function(result) {
					if (result.flag) {
						layer.close(idx);
						$('#uploader-idCardList').empty();
						$('#uploader-educationCertificateList').empty();
						$('#uploader-otherList').empty();
						$('#idCardHidden').val('');
						reloadTable({});
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
		$('#idCardImg').empty();
		$('#educationCertificateImg').empty();
		$('#otherImg').empty();
		$('#idCardHidden').val('');
	});
	// 监听头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
		switch (obj.event) {
		case 'create':
			reset();
			idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					closeBtn: 0,
					title : '资料提报-特种工证件新申领',
					area : [ '650px', '525px' ],
					cancel : function() {
						$('#dlg').hide();
						$('#uploader-idCardList').empty();
						$('#uploader-educationCertificateList').empty();
						$('#uploader-otherList').empty();
						$('#idCardHidden').val('');
					}
				});
			break;
		case 'reset':
			$('#form1')[0].reset();
			_query =
				{
					departmentId : ''
				};
			break;
		case 'enable':
			updateUseable(data, 1);
			break;
		case 'disable':
			// 判断如果存在数据 不能禁用
			if (data[0].id > 0) {
				layer.msg('存在人员数据不能禁用')
				return;
			}
			updateUseable(data, 0);
			break;
		case 'downloadfile':
			$('#download').attr('src', 'downloadTemplate.do');
			break;
			case 'detail':
			if(data.length!=1){
				layer.msg('请选择一条数据');
				return;
			}
			data =data[0];

			if (data.handlingAgency === 0){
				data.handlingAgency = '个人办理';
			}else {
				data.handlingAgency = '公司办理';
			}
			// 图片赋值
			data.files.forEach(function (value) {
				var htm = '<img style="width:150px;height: 100px;" src="downloadimg.do?path='+ value.url+'" onclick="previewImg(this)" />';
				if (value.fileType === 0){
					$('#idCardImgDetail').append(htm);
				}else if (value.fileType === 1){
					$('#educationCertificateImgDetail').append(htm);
				}else if (value.fileType === 2){
					$('#otherImgDetail').append(htm);
				}
			});
			form.val("frm1Detail", data);
			idx = layer.open(
				{
					title : '查看-特种工证件新申领',
					skin : 'layui-layer-molv',
					type : 1,
					area : [ '650px', '525px' ],
					closeBtn : 1,
					shade : 0.5,
					content : $('#dlgDetail'),
					cancel : function() {
						$('#frm1Detail').hide();
						$('#idCardImgDetail').empty();
						$('#educationCertificateImgDetail').empty();
						$('#otherImgDetail').empty();
					}
				});
				break;
			case 'edit':
				if(data.length!=1){
					layer.msg('请选择一条数据');
					return;
				}
				data = data[0];
				loadKeshi(data.departmentId,false);
				loadBanzu(data.workShopId,false);
				form.val('frm1', data);
				idx = layer.open(
					{
						type : 1,
						content : $('#dlg'),
						title : '编辑人员',
						area : [ '600px', '425px' ],
						cancel : function() {
							$('#dlg').hide();
						}
					});
				break;
		}
	});

	function updateUseable(data, flag) {
		if (data.length == 0) {
			layer.msg('请选择数据')
			return;
		}
		var ids = data.map(function(item) {
			return item.id;
		}).toString();
		$.ajax(
			{
				type : "post",
				url : flag==1?'grant.do':'grant1.do',
				dateType : "json",
				contentType : 'application/json',
				data : JSON.stringify(
					{
						ids : ids
					}),
				success : function(result) {
					if (result.flag) {
						layer.close(idx);
						reloadTable({});
						commons.showInfo(result.message);
						return;
					}
					layer.msg(result.message);
				}
			});
	}

	try {
		combo.set(
			{
				valueField : 'code',
				elem : 'department',
				param :
					{
						level : '二级'
					},
				url : basePath + 'department/queryClass2.do'
			}).render();
	} catch (e) {
		layer.msg(e.message)
	}

	form.on('select(department)', function(data) {
		if (deptId == '')
			deptId = -1;
		var deptId = data.value;
		loadKeshi(deptId);
		loadBanzu(-1);
	});

	function loadKeshi(deptId,isasync) {
		combo.set(
			{
				valueField : 'code',
				elem : 'workShop',
				async : isasync===undefined?true:isasync,
				param :
					{
						parentId : deptId
					},
				url : basePath + 'department/queryClass2.do'
			}).render();
	}

	form.on('select(workShop)', function(data) {
		if (deptId == '')
			deptId = -1;
		var deptId = data.value;
		loadBanzu(deptId);
	});

	function loadBanzu(deptId,isasync) {
		combo.set(
			{
				valueField : 'code',
				elem : 'deptId',
				async : isasync===undefined?true:isasync,
				param :
					{
						parentId : deptId
					},
				url : basePath + 'department/queryClass2.do'
			}).render();
	}

	// 监听行工具事件
	table.on('tool(test)', function(obj) { // 注：tool 是工具条事件名，test 是 table
											// 原始容器的属性 lay-filter="对应的值"
		if (obj === false)
			return;
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			idx = layer.open(
				{
					title : '查看人员信息',
					skin : 'layui-layer-molv',
					type : 1,
					area : [ '460px', '350px' ],
					closeBtn : 1,
					shade : 0.5,
					content : '<form class="layui-form">'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">工号</label>'
								+ '<label class="layui-form-label c1">' + data.usercode + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">姓名</label>'
								+ '<label class="layui-form-label c1">' + data.name + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">性别</label>'
								+ '<label class="layui-form-label c1">' + ['女','男'][data.sex] + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">部门</label>'
								+ '<label class="layui-form-label c1">' + data.department + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">科室/车间</label>'
								+ '<label class="layui-form-label c1">' + data.workShop + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">班组</label>'
								+ '<label class="layui-form-label c1">' + data.dept + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">状态</label>'
								+ '<label class="layui-form-label c1">' + (data.userState == 1 ? '在职' : '离职') + '</label></div>'
							+ '<div class="layui-form-item">'
								+ '<label class="layui-form-label">备注</label>'
								+ '<label class="layui-form-label c1" style="width:320px">' + data.remark + '</label></div>'
							+ '</form>'
				});
		} else if (layEvent === 'del') {
			deleteMethod(data.id)
		} else if (layEvent === 'edit') {
			loadKeshi(data.departmentId);
			loadBanzu(data.workShopId);
			form.val('frm1', data);
			idx = layer.open(
				{
					type : 1,
					content : $('#dlg'),
					title : '编辑人员',
					area : [ '600px', '425px' ],
					cancel : function() {
						$('#dlg').hide();
					}
				});
		}
	});
	form.on('submit(query)', function(data) {
		$.extend(true, _query, data.field);
		reloadTable(data.field);
		return false;
	});

	form.on('submit(exportData)', function(data) {
		$.extend(true, data.field, _query);
		submitForm(data.field);
		return false;
	});

	form.on('submit(downloadPicturesInBulk)', function(data) {
		debugger;
		$.extend(true, data.field, _query);
		submitImgForm(data.field);
		return false;
	});

	var uploadidCard = upload.render({
		elem: '#idCard'
		, url: 'upload.do'
		, accept : 'images'
		, field : 'filename'
		, multiple: true
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
				$('#uploader-idCardList').append( '<div id="" class="file-iteme">' +
					'<div class="handle"><i class="layui-icon layui-icon-delete">删除</i></div>' +
					'<img data-filename="'+res.message+'" data-path="'+res.msg+'" data-type="0" onclick="previewImg(this)" src="downloadimg.do?path='+ res.msg+'" alt="'+ res.message +'" class="layui-upload-img" style="width: 90px;height: 90px">'+
					'</div>'
				)
				$("#idCardHidden").val(res.msg)
			}else{
				commons.showInfo(res.message);
			}
		}
	});


	var uploadidCard = upload.render({
		elem: '#educationCertificate'
		, url: 'upload.do'
		, accept : 'images'
		, field : 'filename'
		, multiple: true
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
				$('#uploader-educationCertificateList').append( '<div id="" class="file-iteme">' +
					'<div class="handle"><i class="layui-icon layui-icon-delete">删除</i></div>' +
					'<img data-filename="'+res.message+'" data-path="'+res.msg+'" data-type="1" onclick="previewImg(this)" src="downloadimg.do?path='+ res.msg+'" alt="'+ res.message +'" class="layui-upload-img" style="width: 90px;height: 90px">'+
					'</div>'
				)
			}else{
				commons.showInfo(res.message);
			}
		}
	});


	var uploadidCard = upload.render({
		elem: '#other'
		, url: 'upload.do'
		, accept : 'images'
		, field : 'filename'
		, multiple: true
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
				$('#uploader-otherList').append( '<div id="" class="file-iteme">' +
					'<div class="handle"><i class="layui-icon layui-icon-delete">删除</i></div>' +
					'<img data-filename="'+res.message+'" data-path="'+res.msg+'" data-type="2" onclick="previewImg(this)" src="downloadimg.do?path='+ res.msg+'" alt="'+ res.message +'" class="layui-upload-img" style="width: 90px;height: 90px">'+
					'</div>'
				)
			}else{
				commons.showInfo(res.message);
			}
		}
	});

	$(document).on("mouseenter mouseleave", ".file-iteme", function(event){

		if(event.type === "mouseenter"){

			//鼠标悬浮
			$(this).children(".info").fadeIn("fast");

			$(this).children(".handle").fadeIn("fast");
		}else if(event.type === "mouseleave") {

			//鼠标离开

			$(this).children(".info").hide();

			$(this).children(".handle").hide();

		}

	});

	// 删除图片
	$(document).on("click", ".file-iteme .handle", function(event){

		$(this).parent().remove();

	});




});


function removeImg(obj) {
	$(obj).remove();
	//判断是不是都清空了
	var flg = false;
	$('.idClass').each(function(e){
		flg = true;
	});
	if (!flg){
		$('#idCardHidden').val('');
	}
}

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

function submitImgForm(obj) {
	$('#downloadPictures').form(
		{
			url : 'downloadFiles.do',
			onSubmit : function(param) {
				$.extend(true, param, obj);
			}
		});
	$('#downloadPictures').submit();
}

function previewImg(obj) {
	var img = new Image();
	img.src = obj.src;
	var imgHtml = "<img src='" + obj.src + "' width='500px' height='500px'/>";
	//弹出层
	layer.open({
		type: 1,
		shade: 0.8,
		offset: 'auto',
		area: [500 + 'px',550+'px'],
		shadeClose:true,
		scrollbar: false,
		title: false, //不显示标题
		content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
		cancel: function () {
			//layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
		}
	});
}
