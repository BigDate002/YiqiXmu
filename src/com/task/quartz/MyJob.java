package com.task.quartz;

import com.netcity.module.service.DepartmentService;
import com.netcity.module.service.QualificationService;
import com.netcity.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class MyJob {
	@Autowired
	DepartmentService departmentService;
	@Autowired
	UserService userService;
	@Autowired
	QualificationService qualificationService;
	public void importData() {
		try {
			//更新资质状态
			qualificationService.updateZizhi();
			//同步部门
			departmentService.importData();
			//同步人员
			userService.importGBOMData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}