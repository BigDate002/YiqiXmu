/* rest operations crud...and so on*/
layui.define(['jquery','element', 'common', 'commons'], function (exports) {
    "use strict";
    var $ = layui.$,
        layer = layui.layer,
        element = layui.element,
        common = layui.common,
        commons = layui.commons(),
        cacheName = 'mm_rest';
    
    var Rest = function () {
        this.config = {
        	idx:0,//弹出层编号
        	queryUrl: 'query.do',
    		createUrl: 'create.do',//添加
    		updateUrl: 'update.do',//修改
    		grantUrl: 'grant.do',//启用禁用
    		deleteUrl: 'delete.do'//删除
        };
        this.v = '1.0.0';
    };
    
    Rest.prototype.formSubmit = function(data){
    	var that = this;
    	var _conf = that.config;
    	var row = JSON.stringify(data.field);
		var url = _conf.createUrl;
		if(data.field.id){
		  url = _conf.updateUrl;
		}
		that.updateMethod(row,url);
		return false;
    }
    
    Rest.prototype.updateMethod = function(row,url,suc,err){
    	var that = this;
    	var _conf = that.config;
    	var indexx = layer.load();
    	$.ajax({
		  type:"post", 
		  url:url, 
		  dateType:"json", 
		  contentType : 'application/json', 
		  data:row, 
		  success:function(result){
			  layer.close(indexx);
			  if (result.flag) {
				if(suc){
					suc(result);
				}else{
					layer.close(_conf.idx);
					reloadTable({});
					commons.showInfo(result.message);
				}
			}else{
				if(err){
					err(result);
				}else{
					layer.msg(result.message);
				}
			}
		  }
	  });
    }
    
    Rest.prototype.delFun = function(title,param,suc,err) {
    	var that = this;
    	var _conf = that.config;
    	layer.confirm(title,
    			{
    				btn : [ '确定', '取消' ]
    			}, function(index) {
    			layer.close(index);
    			$.ajax(
    				{
    					type : "post",
    					url : _conf.deleteUrl,
    					dateType : "json",
    					data: JSON.stringify(param),
    					contentType : 'application/json',
    					success : function(result) {
    						if (result.flag) {
    							if(suc){
    								suc(result);
    							}else{
    								layer.close(_conf.idx);
    								reloadTable({});
    								commons.showInfo(result.message);
    							}
    						}else{
    							if(err){
    								err(result);
    							}else{
    								layer.msg(result.message);
    							}
    						}
    					}
    				});
    		});
    	}
    
    Rest.prototype.deleteMethod = function(ids,suc,err) {
    	var that = this;
    	var _conf = that.config;
    	layer.confirm('确定禁用选中数据？',
    			{
    				btn : [ '确定', '取消' ]
    			}, function(index) {
    			layer.close(index);
    			$.ajax(
    				{
    					type : "post",
    					url : _conf.deleteUrl+"?ids=" + ids,
    					dateType : "json",
    					contentType : 'application/json',
    					success : function(result) {
    						if (result.flag) {
    							if(suc){
    								suc(result);
    							}else{
    								layer.close(_conf.idx);
    								reloadTable({});
    								commons.showInfo(result.message);
    							}
    						}else{
    							if(err){
    								err(result);
    							}else{
    								layer.msg(result.message);
    							}
    						}
    					}
    				});
    		});
    	}
    
    Rest.prototype.deleteMethod1 = function(ids,suc,err) {
    	var that = this;
    	var _conf = that.config;
    	layer.confirm('确定启用选中数据？',
    			{
    				btn : [ '确定', '取消' ]
    			}, function(index) {
    			layer.close(index);
    			$.ajax(
    				{
    					type : "post",
    					url : _conf.grantUrl+"?ids=" + ids,
    					dateType : "json",
    					contentType : 'application/json',
    					success : function(result) {
    						if (result.flag) {
    							if(suc){
    								suc(result);
    							}else{
    								layer.close(_conf.idx);
    								reloadTable({});
    								commons.showInfo(result.message);
    							}
    						}else{
    							if(err){
    								err(result);
    							}else{
    								layer.msg(result.message);
    							}
    						}
    					}
    				});
    		});
    	}
    
    Rest.prototype.enableMethod = function(ids,suc,err) {
    	var that = this;
    	var _conf = that.config;
    	$.ajax(
				{
					type : "post",
					url : _conf.grantUrl+"?id=" + ids,
					dateType : "json",
					contentType : 'application/json',
					success : function(result) {
						if (result.flag) {
							if(suc){
								suc(result);
							}else{
								layer.close(_conf.idx);
								reloadTable({});
								commons.showInfo(result.message);
							}
						}else{
							if(err){
								err(result);
							}else{
								layer.msg(result.message);
							}
						}
					}
				});
    	}
    
	/**
	 * 配置Rest
	 * @param {Object} options
	 */
    Rest.prototype.set = function (options) {
        var that = this;
        that.config.data = undefined;
        $.extend(true, that.config, options);
        return that;
    };

    var rest = new Rest();
    exports('rest', function (options) {
        return rest.set(options);
    });
    
});