<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>列表</title>
	<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
<style>
	table{border-collapse: separate;}
	.layui-table, .layui-table-view {margin-top:0px;}
</style>
</head>
<body style="min-height: 100%; padding: 0px; margin: 0px;">
	<iframe id="export" style="display:none;" ></iframe>
	<div class="layui-tab layui-tab-brief" id="tabs" lay-filter="tab">
		<ul class="layui-tab-title site-demo-title">
			<li class="layui-this">表格统计</li>
			<li>图表统计</li>
		</ul>
		<div class="layui-tab-content" style="padding:0px;">
			<div class="layui-tab-item layui-show clz">
				<script type="text/html" id="toolbar">
					<a href="javascript:;" class="layui-btn layui-primary layui-btn-xs" lay-event="exportFile">导出EXCEL</a>
				</script>
               	<table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
               </div>
			<div class="layui-tab-item clz" >
               	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
   				<div id="main" style="width: 800px;height:400px;"></div>
               </div>
		</div>
	</div>
	
	<script src="../js/json2.js"></script>
	<!-- 引入 echarts.js -->
	<script src="../plugins/layui/layui.js"></script>
    <script src="../plugins/echarts/dist/echarts.min.js"></script>
	<script src="../plugins/jquery/jquery-1.9.1.js"></script>
	<script>
		 layui.config({
			 base:'../js/'
			 ,version: '<%=System.currentTimeMillis()%>' //为了更新 js 缓存，可忽略
			});
		layui.use([ 'laypage', 'layer', 'table', 'element', 'slider', 'commons', 'form' ], function() {
			var laypage = layui.laypage //分页
			, layer = layui.layer //弹层
			, table = layui.table //表格
			, element = layui.element //元素操作
			, slider = layui.slider //滑块
			, form = layui.form
			, $ = layui.jquery;
			var columns = [ //表头
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
					field : 'query',
					title : '班组',
					align : 'center',
					width : 100
				},
				{
					field : 'postName',
					title : '岗位',
					align : 'center',
					width : 100
				},
				{
					field : 'postCount',
					title : '储备人数',
					align : 'center',
					width : 80
				},
				{
					field : 'usercode',
					title : '人员工号',
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
					field : 'workGroup',
					title : '班组',
					align : 'center',
					width : 100
				} ];
			var commons = layui.commons({columns:columns}); //右下角提示
			var index1 = layer.load(1,
				{
					shade : [ 0.5, '#ccc' ]
				});
			var queryChartsData = function(param){
				$('#main').removeAttr("_echarts_instance_");
				$('#main').html('<h1>正在加载,请稍后....');
				var url = '../chart/queryPositionList.do';
				if(param&&param.departmentId){
					url+='?departmentId='+ param.departmentId;
				}
				$.ajax({
                    url: url,
                    type: 'POST',
                    async: true,
                    dataType: 'json',
                    success: function (result, status, xhr) {
                    	loadCharts(result);
                    }
                });
			}
			//执行一个 table 实例
			table.render(
				{
					id : 'userTable',
					elem : '#demo',
					height : 'full-66',
					method : 'post',
					url : '..//chart/queryPositionData.do',
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
					cols : [ columns ],
					beforeLoad : function(param){
						queryChartsData(param);
					},
					done : function(res) {
						//loadCharts(res);
						layer.close(index1);
						commons.mergeTable(res,['department'],[0]);
						commons.mergeTable(res,['workShop'],[1]);
						commons.mergeTable(res,['query'],[2]);
						commons.mergeTable(res,['postName'],[3]);
						commons.mergeTable(res,['postCount'],[4]);
						$('.layui-table-view,.layui-table').css('margin-bottom','-3px');
					}
				});
				table.on('toolbar(test)',function(obj){
					var checkStatus = table.checkStatus(obj.config.id), data = checkStatus.data; // 获取选中的数据
					switch (obj.event) {
					case 'exportFile':
						$('#export').attr('src','exportFile.do?id='+dept);
						break;
					}
				});
				window.reloadTable = function(param) {
					dept = param.departmentId;
					index1 = layer.load(1,
							{
								shade : [ 0.5, '#ccc' ]
							});
					table.reload("userTable",{where:param});
				};
		});
		var mystyle = {
				    normal:{
						label : {
							show : true,
							position : 'top',
							color:'black',
							formatter:function(param){
								var unit = ''
								if(param.seriesName=='合格率'){
									unit = '%';
								}
								return param.data + unit;
							}
						}
					}
				};
		var dept = 0;
		var option = {
				title:{
			        text:'岗位储备率',
			        x:'center',
			        y:'top',
			        textAlign:'left'
				},
		        tooltip: {
		            show: false
		        },
		        grid:{
		        	 top:'20%',
		        	 left:'50px',
		        	 bottom:'80px'
		        },
		        legend: {
		              data:['岗位总数','合格数','合格率']
		        	, x: 'right'
		        	, y: 18
		        },
		        xAxis : [
		            {
		                type : 'category',
		                splitLine:{show: false},
			            axisLabel:{
	    	        		interval: 0,
	    	        		rotate:35
	    	        	}
		            }
		        ],
		        yAxis : [
		            {
		                type : 'value',
		                minInterval: 1,
		                max:18,
		                splitLine:{show: false}
		            },
                    {
           	            type: 'value',
           	            min: -50,
           	            max: 100,
           	            minInterval: 50,
           	            axisLabel: {
           	                formatter: '{value} %'
           	            },
           				splitLine:{show: false}
           	        }
		        ],
		        series : [
		            {
		                name:"岗位总数",
		                itemStyle:mystyle,
		                color:'#BC1717',
		                type:"bar",
		                barWidth:32
		                
		            },
		            {
		                name:"合格数",
		                itemStyle:mystyle,
		                type:"bar",
		                color:'#23238E',
		                barGap:'0%',
		                barWidth:32
		            },
		            {
		                name:"合格率",
		                type:"line",
		                color:'green',
		                itemStyle:mystyle,
		                yAxisIndex:1
		            }
		        ]
		    };
		function loadCharts(res){
			var list = res.list;
			$('#main').width(list.length*80+220);
			var myChart = echarts.init(document.getElementById('main'));
			var max = 0 ;
			option.series[0].data = list.map(function(d){if(max<d.total){max=d.total;}return d.total;});
			option.series[1].data = list.map(function(d){if(max<d.postCount){max=d.postCount;}return d.postCount;});
			option.series[2].data = list.map(function(d){return d.rate;});
			option.xAxis[0].data = list.map(function(d){return d.department;});
			option.yAxis[0].max = max*5;
		    myChart.setOption(option);
		}
	</script>
</body>
</html>