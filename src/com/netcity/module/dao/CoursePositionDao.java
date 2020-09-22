package com.netcity.module.dao;

import java.util.List;

import com.netcity.base.dao.BaseDao;
import com.netcity.module.entity.CoursePositionEntity;

public interface CoursePositionDao extends BaseDao<CoursePositionEntity> {
	void deleteById2(List<CoursePositionEntity> row);
}