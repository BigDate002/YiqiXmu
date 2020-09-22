package com.netcity.shiro;

import com.alibaba.fastjson.JSONObject;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.UserService;
import com.netcity.util.ResponseFlag;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
	@Autowired
	UserService userService;

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		boolean isMobile = (httpServletRequest.getRequestURL().indexOf("mobile") > 0);
		String token = request.getParameter("token");
		UserEntity usr = new UserEntity();
		if (isMobile && token != null) {
			usr.setToken(token.replaceAll(" ", "+"));
			List<UserEntity> ul = this.userService.findList(usr);
			if (ul.size() > 0) {
				return true;
			}
		}
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (isAjax(request)) {
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setContentType("application/json");
			ResponseFlag resultData = new ResponseFlag();
			resultData.setCode(1);
			resultData.setMessage("登录认证失效，请重新登录!");
			resultData.setMsg("登录认证失效，请重新登录!");
			httpServletResponse.getWriter().write(JSONObject.toJSON(resultData).toString());
		} else if (isMobile) {
			String path = httpServletRequest.getContextPath();
			String basePath = String.valueOf(request.getScheme()) + "://" + request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
			httpServletResponse.sendRedirect(String.valueOf(basePath) + "mobile/login.html");
		} else {
			String path = httpServletRequest.getContextPath();
			String basePath = String.valueOf(request.getScheme()) + "://" + request.getServerName() + ":"
					+ request.getServerPort() + path + "/";
			httpServletResponse.sendRedirect(String.valueOf(basePath) + "login.html");
		}
		return false;
	}

	private boolean isAjax(ServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			return Boolean.TRUE.booleanValue();
		}
		return Boolean.FALSE.booleanValue();
	}
}