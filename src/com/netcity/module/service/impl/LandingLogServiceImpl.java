package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.LandingLogDao;
import com.netcity.module.entity.LandingLogEntity;
import com.netcity.module.service.LandingLogService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("landingLogService")
public class LandingLogServiceImpl<T extends BaseEntity> extends BaseServiceImpl<LandingLogEntity>
		implements LandingLogService {
	@Autowired
	LandingLogDao LandingLogDao;

	public BaseDao<LandingLogEntity> getDao() {
		return (BaseDao<LandingLogEntity>) this.LandingLogDao;
	}
}