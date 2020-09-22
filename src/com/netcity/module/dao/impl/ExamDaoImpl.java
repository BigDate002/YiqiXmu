package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.ExamDao;
import com.netcity.module.entity.ExamEntity;
import org.springframework.stereotype.Repository;

@Repository("examDao")
public class ExamDaoImpl extends BaseDaoImpl<ExamEntity> implements ExamDao {
}