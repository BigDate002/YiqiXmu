package com.netcity.module.dao;

import com.netcity.base.dao.BaseDao;
import com.netcity.module.entity.ExamExportEntity;
import com.netcity.module.entity.ExamUserEntity;
import java.util.List;

public interface ExamUserDao extends BaseDao<ExamUserEntity> {
	List<ExamExportEntity> findExamList(ExamUserEntity paramExamUserEntity);
}