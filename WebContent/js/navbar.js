/** navbar.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn 
 *  change by wxf
 * */
layui.define(['jquery','element', 'common'], function (exports) {
    "use strict";
    var $ = layui.$,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element,
        common = layui.common,
        cacheName = 'tb_navbar';

    var Navbar = function () {
		/**
		 *  默认配置 
		 */
        this.config = {
            elem: undefined, //容器
            data: undefined, //数据源
            url: undefined, //数据源地址
            type: 'POST', //读取方式
            cached: false, //是否使用缓存
            spreadOne: true //设置是否只展开一个二级菜单
        };
        this.v = '1.0.0';
    };
    //渲染
    Navbar.prototype.render = function () {
        var _that = this;
        var _config = _that.config;
        if (typeof (_config.elem) !== 'string' && typeof (_config.elem) !== 'object') {
            common.throwError('Navbar error: elem参数未定义或设置出错，具体设置格式请参考文档API.');
        }
        var $container;
        if (typeof (_config.elem) === 'string') {
            $container = $('' + _config.elem + '');
        }
        if (typeof (_config.elem) === 'object') {
            $container = _config.elem;
        }
        if ($container.length === 0) {
            common.throwError('Navbar error:找不到elem参数配置的容器，请检查.');
        }
        if (_config.data === undefined && _config.url === undefined) {
            common.throwError('Navbar error:请为Navbar配置数据源.')
        }
        if (_config.data !== undefined && typeof (_config.data) === 'object') {
            var html = getHtml(_config.data);
            $container.html(html);
            element.init();
            _that.config.elem = $container;
        } else {
            if (_config.cached) {
                var cacheNavbar = layui.data(cacheName);
                if (cacheNavbar.navbar === undefined) {
                    $.ajax({
                        type: _config.type,
                        url: _config.url,
                        async: false, //_config.async,
                        dataType: 'json',
                        success: function (result, status, xhr) {
                            //添加缓存
                            layui.data(cacheName, {
                                key: 'navbar',
                                value: result
                            });
                            var html = getHtml(result);
                            $container.html(html);
                            element.init();
                        },
                        error: function (xhr, status, error) {
                            common.msgError('Navbar error:' + error);
                        },
                        complete: function (xhr, status) {
                            _that.config.elem = $container;
                        }
                    });
                } else {
                    var html = getHtml(cacheNavbar.navbar);
                    $container.html(html);
                    element.init();
                    _that.config.elem = $container;
                }
            } else {
                //清空缓存
                layui.data(cacheName, null);
                $.ajax({
                    type: _config.type,
                    url: _config.url,
                    async: false, //_config.async,
                    dataType: 'json',
                    success: function (result, status, xhr) {
                        var html = getHtml(result);
                        $container.html(html);
                        element.init();
                        _config.done();
                    },
                    error: function (xhr, status, error) {
                        common.msgError('Navbar error:' + error);
                    },
                    complete: function (xhr, status) {
                        _that.config.elem = $container;
                    }
                });
            }
        }

        if (_config.spreadOne) {
            var $ul = $container.children('ul');
            $ul.find('li.layui-nav-item').each(function () {
                $(this).on('click', function () {
                    $(this).siblings().removeClass('layui-nav-itemed');
                });
            });
        }
        return _that;
    };
	/**
	 * 配置Navbar
	 * @param {Object} options
	 */
    Navbar.prototype.set = function (options) {
        var that = this;
        that.config.data = undefined;
        $.extend(true, that.config, options);
        return that;
    };
	/**
	 * 绑定事件
	 * @param {String} events
	 * @param {Function} callback
	 */
    Navbar.prototype.on = function (events, callback) {
        var that = this;
        var _con = that.config.elem;
        if (typeof (events) !== 'string') {
            common.throwError('Navbar error:事件名配置出错，请参考API文档.');
        }
        var lIndex = events.indexOf('(');
        var eventName = events.substr(0, lIndex);
        var filter = events.substring(lIndex + 1, events.indexOf(')'));
        if (eventName === 'click') {
            if (_con.attr('lay-filter') !== undefined) {
                _con.children('ul').find('li').each(function () {
                    var $this = $(this);
                    if ($this.find('dl').length > 0) {
                        var $dd = $this.find('dd').each(function () {
                            $(this).on('click', function () {
                                var $a = $(this).children('a');
                                var href = $a.data('url');
                                var icon = $a.children('i:first').data('icon');
                                var title = $a.children('cite').text();
                                var id = $a.data('id')
                                var parentId = $a.children('cite').attr('parent');
                                //var parent = layui.data(cacheName).navbar.find(function(i){return i.id==parentId;});
                                var data = {
                                    elem: $a,
                                    field: {
                                    	id : id,
                                        href: href,
                                        icon: icon,
                                        title: title
                                        //,parent: parent
                                    }
                                }
                                callback(data);
                            });
                        });
                    } else {
                        $this.on('click', function () {
                            var $a = $this.children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            var title = $a.children('cite').text();
                            var id = $a.data('id');
                            var data = {
                                elem: $a,
                                field: {
                                	id: id,
                                    href: href,
                                    icon: icon,
                                    title: title
                                }
                            }
                            callback(data);
                        });
                    }
                });
            }
        }
    };
	/**
	 * 清除缓存
	 */
    Navbar.prototype.cleanCached = function () {
        layui.data(cacheName, null);
    };
	/**
	 * 获取html字符串
	 * @param {Object} data
	 */
    function getHtml(data) {
        var ulHtml = '<ul class="layui-nav layui-nav-tree beg-navbar">';
        for (var i = 0; i < data.length; i++) {
            if (i==0) {
                ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
            } else {
                ulHtml += '<li class="layui-nav-item">';
            }
            var dataUrl = (data[i].url !== undefined && data[i].url !== '#') ? 'data-url="' + data[i].url + '"' : '';
            ulHtml += '<a href="javascript:;" ' + dataUrl + ' data-id="'+data[i].id+'" >';
            if (data[i].icon !== undefined && data[i].icon !== '') {
                if (data[i].icon.indexOf('fa-') !== -1) {
                    ulHtml += '<i class="fa ' + data[i].icon + '" aria-hidden="true" data-icon="' + data[i].icon + '"></i>&nbsp;';
                } else {
                    ulHtml += '<i class="layui-icon" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>&nbsp;';
                }
            }
            ulHtml += '<cite>' + data[i].title + '</cite>'
            ulHtml += '</a>';
            ulHtml += getChildHtml(data[i]);
            ulHtml += '</li>';
        }
        ulHtml += '</ul>';
        return ulHtml;
    }
    function getChildHtml(data){
    	
    	var ulHtml = '';
    	if (data!=undefined&&data.childrens !== undefined && data.childrens !== null && data.childrens.length > 0) {
            ulHtml += '<dl class="layui-nav-child">'
	    	for (var j = 0; j < data.childrens.length; j++) {
	            ulHtml += '<dd title="' + data.childrens[j].title + '">';
	            ulHtml += '<a href="javascript:;" data-id="'+data.childrens[j].id+'" data-url="' + data.childrens[j].url + '" data-title="'+data.childrens[j].title+'">';
	            if (data.childrens[j].icon !== undefined && data.childrens[j].icon !== '') {
	                if (data.childrens[j].icon.indexOf('fa-') !== -1) {
	                    ulHtml += '<i class="fa ' + data.childrens[j].icon + '" data-icon="' + data.childrens[j].icon + '" aria-hidden="true"></i>&nbsp;';
	                } else {
	                    ulHtml += '<i class="layui-icon" data-icon="' + data.childrens[j].icon + '">' + data.childrens[j].icon + '</i>&nbsp;';
	                }
	            }
	            ulHtml += '<cite style="margin-left:24px" parent='+data.id+'>' + data.childrens[j].title + '</cite>';
	            if(data.childrens[j].descript){
	            	ulHtml += '<span style="margin-left:5px" class="layui-badge-rim">'+data.childrens[j].descript+'</span>';
	            }
	            ulHtml += '</a>';
	            //ulHtml += getChildHtml1(data.childrens[j]);
	            ulHtml += '</dd>';
	        }
        }
    	ulHtml += '</dl>';
    	return ulHtml;
    }
//    	 function getChildHtml1(data){
//    	    	var ulHtml = '';
//    	    	if (data!=undefined&&data.childrens !== undefined && data.childrens !== null && data.childrens.length > 0) {
//    	            ulHtml += '<ol class="layui-nav-child" style="padding-left:15px;">'
//    		    	for (var j = 0; j < data.childrens.length; j++) {
//    		            ulHtml += '<li title="' + data.childrens[j].title + '">';
//    		            ulHtml += '<a href="javascript:;" data-id="'+data.childrens[j].id+'" data-url="' + data.childrens[j].url + '" data-title="'+data.childrens[j].title+'">&nbsp;&nbsp;&nbsp;';
//    		            if (data.childrens[j].icon !== undefined && data.childrens[j].icon !== '') {
//    		            	if(!data.childrens[j].icon||data.childrens[j].icon=='')continue;
//    		                if (data.childrens[j].icon.indexOf('fa-') !== -1) {
//    		                    ulHtml += '<i class="fa ' + data.childrens[j].icon + '" data-icon="' + data.childrens[j].icon + '" aria-hidden="true"></i>&nbsp;';
//    		                } else {
//    		                    ulHtml += '<i class="layui-icon" data-icon="' + data.childrens[j].icon + '">' + data.childrens[j].icon + '</i>&nbsp;';
//    		                }
//    		            }
//    		            ulHtml += '<cite parent='+data.id+'>' + data.childrens[j].title + '</cite>';
//    		            ulHtml += '</a>';
//    		            ulHtml += '</li>';
//    		        }
//    	            ulHtml += '</ol>';
//    	        }
//    	return ulHtml;
//    }
    var navbar = new Navbar();

    exports('navbar', function (options) {
        return navbar.set(options);
    });
});