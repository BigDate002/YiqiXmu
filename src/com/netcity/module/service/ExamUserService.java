package com.netcity.module.service;

import com.netcity.module.entity.ExamExportEntity;
import com.netcity.module.entity.ExamUserEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface ExamUserService extends BaseService<ExamUserEntity> {
	List<ExamExportEntity> findExamList(ExamUserEntity paramExamUserEntity);
}