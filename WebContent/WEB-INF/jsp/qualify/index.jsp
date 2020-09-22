<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>资质管理</title>
    <link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../plugins/zTree/css/zTreeStyle/zTreeStyle.css" media="all">
    <style>
        .layui-table, .layui-table-view {
            margin: 0 0 !important;
        }

        u {
            color: #8E2323
        }

        hr {
            border: 0;
            border-bottom: 2px solid #999999;
        }

        #queryform .layui-form-label {
            width: auto;
        }

        #queryform .layui-input-inline {
            width: 150px;
        }

        #queryform .layui-btn {
            vertical-align: top;
        }

    </style>
</head>
<body style="min-height: 100%; margin: 0px;">
<form id="xiazai" method="post" style="display:none;"></form>
<iframe class="layui-hide" id="download"></iframe>


<div class="layui-tab-content">
    <div class="layui-tab-item layui-show">

        <script type="text/html" id="toolbar">
            <form method="POST action="
                  exportData.do" class="layui-form" id="queryform" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
            <div class="layui-form-item" style="float:right;">
                <div class="layui-input-inline">
                    <select name="state" >
                        <option value="">隐藏数据</option>
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="query" placeholder="请输入人员工号或姓名" autocomplete="off"
                           class="layui-input"/>
                </div>
                <div class="layui-input-inline" style="width:auto">
                    <shiro:hasPermission name="qualify:query">
                        <a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
                        <a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
                    </shiro:hasPermission>
                </div>
            </div>

            <shiro:hasPermission name="qualify:certificateRecovery">
                <a class="layui-btn layui-btn-xs" lay-event="certificateRecovery">证件收回</a>
            </shiro:hasPermission>

            <shiro:hasPermission name="qualify:exportData">
                <a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="qualify:importData">
                <a class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
                <a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
            </shiro:hasPermission>
            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
            </form>
        </script>
        <script type="text/html" id="barDemo">
            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
        </script>
        <table class="layui-hide" id="demo" style="margin: 0px;" lay-filter="test"></table>
        <div id="detail" style="min-width:480px;display:none;">
        </div>
    </div>
    <div class="layui-tab-item">
        <script type="text/html" id="toolbar2">
            <form method="POST action="
                  exportData.do" class="layui-form" id="queryform" lay-filter="queryform" onkeydown="if(event.keyCode==13){return false}">
            <div class="layui-form-item" style="float:right;">
                <div class="layui-input-inline">
                    <input type="text" name="query" placeholder="请输入人员工号或姓名" autocomplete="off"
                           class="layui-input"/>
                </div>
                <div class="layui-input-inline" style="width:auto">
                    <shiro:hasPermission name="qualify:query">
                        <a class="layui-btn layui-btn-xs" lay-submit lay-filter="query">查询</a>
                        <a class="layui-btn layui-btn-xs" lay-event="reset">重置</a>
                    </shiro:hasPermission>
                </div>
            </div>
            <shiro:hasPermission name="qualify:exportData">
                <a class="layui-btn layui-btn-xs" lay-submit lay-filter="exportData">批量导出</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="qualify:importData">
                <a class="layui-btn layui-btn-xs" lay-event="importExcel">批量导入</a>
                <a class="layui-btn layui-btn-xs" lay-event="downloadfile" style="margin-left:10px;">导入模板下载</a>
            </shiro:hasPermission>
            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail1">查看</a>
            </form>
        </script>
        <script type="text/html" id="barDemo2">
            <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail1">查看</a>
        </script>
        <table class="layui-hide" id="demo2" style="margin: 0px;" lay-filter="test2"></table>
        <div id="detail1" style="min-width:480px;display:none;">
        </div>

    </div>

</div>


<script>
    var basePath = '../';
</script>
<script src="../js/json2.js"></script>
<script src="../plugins/layui/layui.js"></script>
<script src="../js/qualify/index.js"></script>
<%--	<script src="../js/qualify/index2.js"></script>--%>
<script src="../plugins/jquery/jquery-1.9.1.js"></script>
<script src="../plugins/easyui/jquery.easyui.min.js"></script>
</body>
</html>
