package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.QualificationCopyDao;
import com.netcity.module.entity.QualificationCopyEntity;
import com.netcity.module.service.QualificationCopyService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("qualificationCopyService")
public class QualificationCopyServiceImpl<T extends BaseEntity> extends BaseServiceImpl<QualificationCopyEntity>
		implements QualificationCopyService {
	@Autowired
	QualificationCopyDao QualificationCopyDao;

	public BaseDao<QualificationCopyEntity> getDao() {
		return (BaseDao<QualificationCopyEntity>) this.QualificationCopyDao;
	}
}