package com.netcity.controller.biz;

import com.netcity.module.entity.ConfigEntity;
import com.netcity.module.service.ConfigService;
import com.netcity.util.ResponseFlag;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/examconfig" })
public class ExamConfigController {
	@Autowired
	ConfigService configService;

	@RequestMapping({ "/index.html" })
	public ModelAndView index(HttpServletRequest request) {
		ConfigEntity config = (ConfigEntity) this.configService.getById(Long.valueOf(1L));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("examconfig/index");
		request.getSession().setAttribute("config", config);
		return mv;
	}

	@RequiresPermissions({ "examconfig:update" })
	@RequestMapping({ "/update.do" })
	@ResponseBody
	public ResponseFlag update(@RequestBody ConfigEntity config) {
		ResponseFlag result = new ResponseFlag();
		try {
			config.setId(Long.valueOf(1L));
			this.configService.updateEntity(config);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			result.setFlag(ResponseFlag.Failed);
			result.setMessage("保存失败");
		}
		return result;
	}
}