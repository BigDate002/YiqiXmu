package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.TrainCourseDao;
import com.netcity.module.entity.TrainCourseEntity;
import com.netcity.module.service.TrainCourseService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("trainCourseService")
public class TrainCourseServiceImpl<T extends BaseEntity> extends BaseServiceImpl<TrainCourseEntity>
		implements TrainCourseService {
	@Autowired
	TrainCourseDao TrainCourseDao;

	public BaseDao<TrainCourseEntity> getDao() {
		return (BaseDao<TrainCourseEntity>) this.TrainCourseDao;
	}
}