layui.define([ 'jquery', 'element', 'common' ], function(exports) {
	var $ = layui.$, layer = parent.layer === undefined ? layui.layer : parent.layer;
	"use strict";
	var Commons = function() {
		this.config =
			{
				closeBtn : 1,
				type : 1,
				title : '提示',
				skin : 'layui-layer-molv',
				area : [ '200px', '150px' ],
				shade : 0,
				offset : 'rb',
				time : 3000,
				anim : 2
			};
	};
	/**
	 * 合并单元格
	 * 
	 * @param columsName
	 *            需要合并的列名称
	 * @param columsIndex
	 *            需要合并的列索引值
	 * @param res
	 *            表格数据
	 * @param curr
	 *            当前页
	 * @param count
	 *            总数
	 */
	Commons.prototype.mergeTable = function(res, columsName, columsIndex) {
		var data = res.data;
		var curr = res.page;
		var count = res.count;
		var columns = this.config.columns;
		// 定位需要添加合并属性的行数
		var mergeIndex = 0;
		// 这里涉及到简单的运算，mark是计算每次需要合并的格子数
		var mark = 1;
		// 这里循环所有要合并的列
		for (var k = 0; k < columsName.length; k++) {
			// 所有行
			var trArr = $(".layui-table-body>.layui-table").find("tr");
			// 这里循环表格当前的数据
			for (var i = 1; i < res.data.length; i++) {
				// 获取当前行的当前列
				var tdCurArr = trArr.eq(i).find("td").eq(columsIndex[k]);
				// 获取相同列的第一列
				var tdPreArr = trArr.eq(mergeIndex).find("td").eq(columsIndex[k]);
				// 后一行的值与前一行的值做比较，相同就需要合并
				var flag = true;
				for(var j = 0; j <= columsIndex; j++){
					if(data[i][columns[j].field] !== data[i - 1][columns[j].field]){
						flag = false;
						break;
					}
				}
				if (flag) {
					mark += 1;
					// 相同列的第一列增加rowspan属性
					tdPreArr.each(function() {
						$(this).attr("rowspan", mark);
					});
					// 当前行隐藏
					tdCurArr.each(function() {
						$(this).css("display", "none");
					});
				} else {
					// 一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
					mergeIndex = i;
					mark = 1;
				}
			}
		}
	}

	Commons.prototype.set = function(options) {
		var that = this;
		$.extend(true, that.config, options);
		return that;
	};
	Commons.prototype.showInfo = function(msg) {
		this.config.content = '<br/><center><h3>' + msg + '</h3></center>';
		layer.open(this.config);
	}
	var commons = new Commons();
	exports('commons', function(options) {
		return commons.set(options);
	});
});