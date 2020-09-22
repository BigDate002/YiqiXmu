package com.netcity.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticHtmlController {
	@RequestMapping({ "/plugins/**/*.html" })
	public String pluginsHtml(HttpServletRequest request) {
		String path = request.getRequestURI();
		path = path.substring(path.indexOf("plugins"));
		return path;
	}
}