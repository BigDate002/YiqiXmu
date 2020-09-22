package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.CourseEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface CourseService extends BaseService<CourseEntity> {
	void insertRow(CourseEntity paramCourseEntity) throws ServiceException;

	void updateRow(CourseEntity paramCourseEntity) throws ServiceException;

	void insertImport(List<CourseEntity> paramList, UserEntity paramUserEntity) throws ServiceException;

	List<CourseEntity> findExportList(CourseEntity paramCourseEntity);
}