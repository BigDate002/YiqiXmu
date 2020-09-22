package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.CodeEntity;
import com.netcity.service.BaseService;

public interface CodeService extends BaseService<CodeEntity> {
	int createNextNo(String paramString) throws ServiceException;
}