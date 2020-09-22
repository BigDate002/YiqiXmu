package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.ExamResultDao;
import com.netcity.module.entity.ExamResultEntity;
import com.netcity.module.service.ExamResultService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("examResultService")
public class ExamResultServiceImpl<T extends BaseEntity> extends BaseServiceImpl<ExamResultEntity>
		implements ExamResultService {
	@Autowired
	ExamResultDao ExamResultDao;

	public BaseDao<ExamResultEntity> getDao() {
		return (BaseDao<ExamResultEntity>) this.ExamResultDao;
	}
}