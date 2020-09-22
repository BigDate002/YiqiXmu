package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.ExamUserDao;
import com.netcity.module.entity.ExamExportEntity;
import com.netcity.module.entity.ExamUserEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("examUserDao")
public class ExamUserDaoImpl extends BaseDaoImpl<ExamUserEntity> implements ExamUserDao {
	public List<ExamExportEntity> findExamList(ExamUserEntity query) {
		return this.sqlSessionTemplate.selectList(getSQL("findExamList"), query);
	}
}