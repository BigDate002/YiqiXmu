package com.netcity.module.service;

import java.util.List;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.CoursePositionEntity;
import com.netcity.service.BaseService;

public interface CoursePositionService extends BaseService<CoursePositionEntity> {
	void deleteById2(List<CoursePositionEntity> row) throws ServiceException;
}