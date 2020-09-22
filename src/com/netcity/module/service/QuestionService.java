package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.QuestionEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface QuestionService extends BaseService<QuestionEntity> {
	void insertImport(List<QuestionEntity> paramList) throws ServiceException;
}