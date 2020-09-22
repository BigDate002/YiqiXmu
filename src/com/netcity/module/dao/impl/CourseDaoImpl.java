package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.CourseDao;
import com.netcity.module.entity.CourseEntity;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("courseDao")
public class CourseDaoImpl extends BaseDaoImpl<CourseEntity> implements CourseDao {
	public Map<Long, CourseEntity> queryMap() {
		CourseEntity q = new CourseEntity();
		q.setState(Long.valueOf(1L));
		return this.sqlSessionTemplate.selectMap(getSQL("selectByEntity"), "id");
	}

	public List<CourseEntity> findExportList(CourseEntity query) {
		return this.sqlSessionTemplate.selectList(getSQL("findExportList"), query);
	}
}