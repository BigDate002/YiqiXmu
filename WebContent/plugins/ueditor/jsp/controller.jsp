<%@page import="com.alibaba.fastjson.JSONArray"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	String action = request.getParameter("action");
	String rootPath = application.getRealPath( "/" );
	String s = new ActionEnter( request, rootPath ).exec();
	System.out.println(s);
	JSONObject jobj = JSONObject.parseObject(s);
	if(jobj.getString("url")!=null){
		jobj.put("url", basePath+jobj.getString("url"));
	}
	JSONArray arr  = jobj.getJSONArray("list");
	if(arr!=null&&arr.size()>0){
		for(int i = 0; i <arr.size(); i++){
			JSONObject json = arr.getJSONObject(i);
			String url = json.getString("url");
			if("listimage".equals(action)){
				url = url.substring(url.indexOf("ueditor"));
			}
			json.put("url", basePath+"/"+url);
		}	
	}
	out.write(jobj.toJSONString());
%>