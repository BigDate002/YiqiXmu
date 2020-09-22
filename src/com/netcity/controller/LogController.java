package com.netcity.controller;

import com.netcity.module.entity.LandingLogEntity;
import com.netcity.module.service.LandingLogService;
import com.netcity.util.LayuiPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/log" })
public class LogController {
	@Autowired
	LandingLogService LandingLogService;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "log/index";
	}

	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			LandingLogEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			result = this.LandingLogService.listByPage(query, pageNum, pageSize);
			result.setCode(0);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("查询失败");
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}
}