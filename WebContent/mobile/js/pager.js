class Pager{
	constructor(_config){
		// 定义有关分页的全局变量
	    this.name = name;
	    this.$ = mui;
		this.pageNum = 1; // 当前页
		this.pageSize = 10; // 每页显示的数据
		this.pages = 10; // 总页数
		this.total = 100; // 总数据数
		this.navigatepageSize = 5; // 分页导航显示的页码数
		this.param = {};
		$.extend(true, this, _config);
	}
}

//重新渲染分页导航
Pager.prototype.renderMuiPagination=function(){
	var table = document.getElementById("pagination");
	var html = "";
	var n = Math.floor((this.pageNum-1)/this.navigatepageSize);
	var min = n*this.navigatepageSize+1;
	var max = (n+1)*this.navigatepageSize;
	max = max>this.pages?this.pages:max;
	html += '<li class="mui-previous'+(this.pageNum>1?'':' mui-disabled')+'"><a href="#">上一页</a></li>';
	for (var i = min; i <= max; i++) {
		if(i==this.pageNum){
			html += '<li class="mui-active"><a href="#">' + this.pageNum + '</a></li>';
		}else{
			html += '<li><a href="#">' + i + '</a></li>';
		}
	}
	html += '<li class="mui-next'+(this.pageNum<this.pages?'':' mui-disabled')+'"><a href="#">下一页</a></li>';
	table.innerHTML = html;
}

Pager.prototype.loadData = function(_param){
	var that = this;
	mui.showLoading('加载中...');
	_param = _param||{};
	$.extend(true, that.param, _param);
	that.param.limit=that.pageSize;
	that.param.page=that.pageNum;
	$.ajax(that.url,{
		data: that.param,
		dataType:'json',
		type:'post',
		timeout:60000,
		headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},	              
		success:function(data){
			that.total = data.count;
			var pages = that.total%that.pageSize==0?that.total/that.pageSize:parseInt(that.total/that.pageSize+1);
			pages = pages>0?pages:1;
			that.pages = pages;
			that.pageNum = that.pageNum>that.pages?that.pages:that.pageNum;
			that.renderMuiPagination();
			var table = document.getElementById("pagination");
			if(pages<2){
				table.style.display='none';
			}else{
				table.style.display='inline-block';
			}
			that.done(data);
		},
		error:function(xhr,type,errorThrown){
			mui.toast('网络错误');
		}
	});
}

function pager(_config){
	var ph = new Pager(_config);
	var $ = ph.$;
	$('.mui-pagination').on('tap', 'a', function() {
		var li = this.parentNode;
		var classList = li.classList;
		if (!classList.contains('mui-active') && !classList.contains('mui-disabled')) {
			if (classList.contains('mui-previous')) {
				ph.pageNum--;
			} else if (classList.contains('mui-next')) {
				ph.pageNum++;
			} else {
				var page = parseInt(this.innerText);
				if(ph.pageNum==page){
					return;
				}
				ph.pageNum=page;
			}
			ph.loadData();
		}
	});
	return ph;
}