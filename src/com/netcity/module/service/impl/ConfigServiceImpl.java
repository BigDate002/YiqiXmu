package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.ConfigDao;
import com.netcity.module.entity.ConfigEntity;
import com.netcity.module.service.ConfigService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("configService")
public class ConfigServiceImpl<T extends BaseEntity> extends BaseServiceImpl<ConfigEntity> implements ConfigService {
	@Autowired
	ConfigDao ConfigDao;

	public BaseDao<ConfigEntity> getDao() {
		return (BaseDao<ConfigEntity>) this.ConfigDao;
	}
}