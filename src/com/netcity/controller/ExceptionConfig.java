package com.netcity.controller;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ExceptionConfig {
	@ExceptionHandler({ AuthorizationException.class })
	@ResponseStatus(HttpStatus.OK)
	public void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		e.printStackTrace();
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");
			JSONObject res = new JSONObject();
			res.put("code", "1");
			res.put("msg", "没有权限,请联系管理员");
			res.put("message", "没有权限,请联系管理员");
			response.getWriter().write(res.toJSONString());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	@ResponseStatus(HttpStatus.OK)
	public void uploaderrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		e.printStackTrace();
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");
			JSONObject res = new JSONObject();
			res.put("code", "1");
			res.put("message", "文件最大不能超过100M");
			response.getWriter().write(res.toJSONString());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.OK)
	public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		e.printStackTrace();
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=UTF-8");
			JSONObject res = new JSONObject();
			res.put("flag", Boolean.valueOf(false));
			res.put("message", "输入有误请检查格式");
			response.getWriter().write(res.toJSONString());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}