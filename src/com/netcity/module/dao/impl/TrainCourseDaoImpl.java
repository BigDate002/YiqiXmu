package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.TrainCourseDao;
import com.netcity.module.entity.TrainCourseEntity;
import org.springframework.stereotype.Repository;

@Repository("trainCourseDao")
public class TrainCourseDaoImpl extends BaseDaoImpl<TrainCourseEntity> implements TrainCourseDao {
}