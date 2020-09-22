package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.SystemLogDao;
import com.netcity.module.entity.SystemLogEntity;
import com.netcity.module.service.SystemLogService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("systemLogService")
public class SystemLogServiceImpl<T extends BaseEntity> extends BaseServiceImpl<SystemLogEntity>
		implements SystemLogService {
	@Autowired
	SystemLogDao SystemLogDao;

	public BaseDao<SystemLogEntity> getDao() {
		return (BaseDao<SystemLogEntity>) this.SystemLogDao;
	}
}