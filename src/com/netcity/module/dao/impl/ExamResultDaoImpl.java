package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.ExamResultDao;
import com.netcity.module.entity.ExamResultEntity;
import org.springframework.stereotype.Repository;

@Repository("examResultDao")
public class ExamResultDaoImpl extends BaseDaoImpl<ExamResultEntity> implements ExamResultDao {
}