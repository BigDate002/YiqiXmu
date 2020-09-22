package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.ExamCourseDao;
import com.netcity.module.entity.ExamCourseEntity;
import com.netcity.module.service.ExamCourseService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("examCourseService")
public class ExamCourseServiceImpl<T extends BaseEntity> extends BaseServiceImpl<ExamCourseEntity>
		implements ExamCourseService {
	@Autowired
	ExamCourseDao ExamCourseDao;

	public BaseDao<ExamCourseEntity> getDao() {
		return (BaseDao<ExamCourseEntity>) this.ExamCourseDao;
	}
}