package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.QualificationDao;
import com.netcity.module.entity.QualificationEntity;
import org.springframework.stereotype.Repository;

@Repository("qualificationDao")
public class QualificationDaoImpl extends BaseDaoImpl<QualificationEntity> implements QualificationDao {

	@Override
	public void updateZizhi() {
		this.sqlSessionTemplate.update(getSQL("updateZizhi"));
	}
}