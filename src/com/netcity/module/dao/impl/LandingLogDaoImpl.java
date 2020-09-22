package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.LandingLogDao;
import com.netcity.module.entity.LandingLogEntity;
import org.springframework.stereotype.Repository;

@Repository("landingLogDao")
public class LandingLogDaoImpl extends BaseDaoImpl<LandingLogEntity> implements LandingLogDao {
}