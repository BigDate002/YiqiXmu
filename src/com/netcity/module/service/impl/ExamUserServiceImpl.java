package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.ExamUserDao;
import com.netcity.module.entity.ExamExportEntity;
import com.netcity.module.entity.ExamUserEntity;
import com.netcity.module.service.ExamUserService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("examUserService")
public class ExamUserServiceImpl<T extends BaseEntity> extends BaseServiceImpl<ExamUserEntity>
		implements ExamUserService {
	@Autowired
	ExamUserDao ExamUserDao;

	public BaseDao<ExamUserEntity> getDao() {
		return (BaseDao<ExamUserEntity>) this.ExamUserDao;
	}

	public List<ExamExportEntity> findExamList(ExamUserEntity query) {
		return this.ExamUserDao.findExamList(query);
	}
}