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
			url : 'queryPerson.do',
			where : {state : 1},
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
							width : 70,
							align : 'center'
						},
						{
							field : 'postionName',
							title : '岗位',
							width : 100,
							align : 'center'
						},
						{
							title : '测试类型',
							width : 80,
							align : 'center',
							templet:function(d){return d.exam.type==1?'复审':'初审';}
						},
						{
							templet : function(d){return d.exam.ispass?'通过':'未通过';},
							title : '结果',
							align : 'center',
							width : 80
						},
						{
							title : '答题状态',
							width : 120,
							align : 'center',
							templet : function(d){return ['未开始','已提交','已评分'][d.state];}
						},
						{
							title : '开始时间',
							width : 120,
							align : 'center',
							templet:function(d){return d.exam.beginDate;}
						},
						{
							title : '结束时间',
							width : 120,
							align : 'center',
							templet:function(d){return d.exam.endDate;}
						},
						{
							title : '操作',
							width : 90,
							align : 'center',
							toolbar : '#bar'
						}
						] ],
			done : function(res) {
				layer.close(index1);
			}
		});
	table.on('tool(test)',function(obj){
		if (obj === false)
			return;
		var data = obj.data // 获得当前行数据
		, layEvent = obj.event; // 获得 lay-event 对应的值
		if (layEvent === 'detail') {
			top.active.tabAdd('exam/detail2.html?id='+data.exam.id,'test2','测试详请');
			top.active.tabChange('test2');
		}
	});
});