package com.netcity.module.dao;

import com.netcity.base.dao.BaseDao;
import com.netcity.module.entity.CourseEntity;
import java.util.List;
import java.util.Map;

public interface CourseDao extends BaseDao<CourseEntity> {
	Map<Long, CourseEntity> queryMap();

	List<CourseEntity> findExportList(CourseEntity paramCourseEntity);
}