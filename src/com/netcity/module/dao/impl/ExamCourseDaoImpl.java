package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.ExamCourseDao;
import com.netcity.module.entity.ExamCourseEntity;
import org.springframework.stereotype.Repository;

@Repository("examCourseDao")
public class ExamCourseDaoImpl extends BaseDaoImpl<ExamCourseEntity> implements ExamCourseDao {
}