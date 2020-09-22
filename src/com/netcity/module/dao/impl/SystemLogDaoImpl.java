package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.SystemLogDao;
import com.netcity.module.entity.SystemLogEntity;
import org.springframework.stereotype.Repository;

@Repository("systemLogDao")
public class SystemLogDaoImpl extends BaseDaoImpl<SystemLogEntity> implements SystemLogDao {
}