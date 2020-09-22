package com.netcity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController {
	@RequestMapping({ "404" })
	public String page404() {
		return "HTTP-404";
	}

	@RequestMapping({ "500" })
	public String page500() {
		return "HTTP-500";
	}
}