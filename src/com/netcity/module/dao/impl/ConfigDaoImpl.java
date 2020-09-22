package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.ConfigDao;
import com.netcity.module.entity.ConfigEntity;
import org.springframework.stereotype.Repository;

@Repository("configDao")
public class ConfigDaoImpl extends BaseDaoImpl<ConfigEntity> implements ConfigDao {
}