/** combo.js By wangxiaofei*/
layui.define(['jquery','element', 'common', 'form'], function (exports) {
    "use strict";
    var $ = layui.$,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element,
        common = layui.common,
        form = layui.form,
        cacheName = 'mm_combo';

    var Combo = function () {
		/**
		 *  默认配置 
		 */
        this.config = {
    		//容器
            elem: undefined,
            //数据源
            data: undefined,
            //数据源地址
            url: undefined,
            //读取方式
            type: 'POST',
            //是否使用缓存
            cached: false,
            //值
            valueField: 'id',
            //显示字段
            titleField: 'name',
            //参数
            param : {},
            //是否异步
            async : true
        };
        this.v = '1.0.0';
    };
    //渲染
    Combo.prototype.render = function () {
        var _that = this;
        var _config = _that.config;
        if (typeof (_config.elem) !== 'string' && typeof (_config.elem) !== 'object') {
            common.throwError('Combo error: elem参数未定义或设置出错.');
        }
        var $container;
        if (typeof (_config.elem) === 'string') {
            $container = $('#' + _config.elem + '');
        }
        if (typeof (_config.elem) === 'object') {
            $container = _config.elem;
        }
        if ($container.length === 0) {
            common.throwError('Combo error:找不到elem参数配置的容器，请检查.');
        }
        if (_config.data === undefined && _config.url === undefined) {
            common.throwError('Combo error:请为Combo配置数据源.')
        }
        if (_config.data !== undefined && typeof (_config.data) === 'object') {
            var html = _that.getHtml(_config.data);
            $container.html(html);
            element.init();
            _that.config.elem = $container;
            form.render('select');
        } else {
            if (_config.cached) {
                var cacheCombo = layui.data(cacheName);
                if (cacheCombo.combo === undefined) {
                    $.ajax({
                        type: _config.type,
                        url: _config.url,
                        async: false,
                        data: _config.param,
                        dataType: 'json',
                        success: function (result, status, xhr) {
                        	if(result.flag===false||result.code===1){
                        		layer.msg(result.message);
                        		return;
                        	}
                            //添加缓存
                            layui.data(cacheName, {
                                key: 'combo',
                                value: result
                            });
                            var html = _that.getHtml(result.data);
                            $container.html(html);
                            element.init();
                            form.render('select');
                        },
                        error: function (xhr, status, error) {
                            common.msgError('Combo error:' + error);
                        },
                        complete: function (xhr, status) {
                            _that.config.elem = $container;
                        }
                    });
                } else {
                    var html = _that.getHtml(cacheCombo.combo);
                    $container.html(html);
                    element.init();
                    _that.config.elem = $container;
                }
            } else {
                //清空缓存
                layui.data(cacheName, null);
                var indexx = layer.load();
                $.ajax({
                    type: _config.type,
                    url: _config.url,
                    async: _config.async, //_config.async,
                    dataType: 'json',
                    data: _config.param,
                    success: function (result, status, xhr) {
                    	if(result.flag===false){
                    		layer.msg(result.message);
                    		return;
                    	}
                        var html = _that.getHtml(result.data);
                        $container.html(html);
                        element.init();
                        form.render('select');
                        _config.data = result.data;
                    },
                    error: function (xhr, status, error) {
                        common.msgError('Combo error:' + error);
                    },
                    complete: function (xhr, status) {
                    	layer.close(indexx);
                        _that.config.elem = $container;
                    }
                });
            }
        }
        return _that;
    };
	/**
	 * 配置Combo
	 * @param {Object} options
	 */
    Combo.prototype.set = function (options) {
        var that = new Combo();
        that.config.data = undefined;
        $.extend(true, that.config, options);
        if(options!=undefined&&options.param!=undefined)
        	that.config.param=options.param;
        return that;
    };
	/**
	 * 清除缓存
	 */
    Combo.prototype.cleanCached = function () {
        layui.data(cacheName, null);
    };
	/**
	 * 获取html字符串
	 * @param {Object} data
	 */
    Combo.prototype.getHtml = function(resultData) {
    	var combo = this;
    	var valueField = combo.config.valueField;
    	var titleField = combo.config.titleField;
        var ulHtml = '<option value>请选择</option>';
        for (var i = 0; i < resultData.length; i++) {
        	ulHtml += '<option value = "' + resultData[i][valueField] + '">' + resultData[i][titleField] + '</option>';
        }
        return ulHtml;
    }

    exports('combo', function (options) {
    	var combo = new Combo();
        return combo.set(options);
    });
    
});