package com.netcity.controller;

import com.netcity.module.entity.StudyEntity;
import com.netcity.module.entity.TrainEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.TrainService;
import com.netcity.util.LayuiPageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "mytrain" })
public class MytrainController {
	@Autowired
	TrainService trainService;

	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			TrainEntity te) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			te.setUsercode(user.getUsercode());
			result = this.trainService.listByPage(te, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	/**
	 * 新增方法1
	 * 显示打卡状态信息
	 */
	@RequestMapping(value = "/updateStudy",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public LayuiPageInfo updateStudy(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
									 TrainEntity te){
		return getLayuiPageInfo(te);
	}
	/**
	 * 新增方法2
	 * 记录学习
	 */
	@RequestMapping(value = "/insertStudy",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public LayuiPageInfo insertStudy(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
									 TrainEntity te){
		return getLayuiPageInfo(te);
	}
	private LayuiPageInfo getLayuiPageInfo(TrainEntity te) {
		LayuiPageInfo result = new LayuiPageInfo();
		try{
			StudyEntity study= (StudyEntity) SecurityUtils.getSubject().getPrincipal();
			te.setUsercode(study.getUsercode());
		}catch(Exception e){
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}
}