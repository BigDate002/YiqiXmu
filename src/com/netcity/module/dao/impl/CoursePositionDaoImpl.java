package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.CoursePositionDao;
import com.netcity.module.entity.CoursePositionEntity;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("coursePositionDao")
public class CoursePositionDaoImpl extends BaseDaoImpl<CoursePositionEntity> implements CoursePositionDao {
	public static final String DELETEBYID2 = "deleteById2";
	@Override
	public void deleteById2(List<CoursePositionEntity> row) {
		// TODO Auto-generated method stub
		this.sqlSessionTemplate.delete(getSQL("deleteById2"), row);
		
	}
}