package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.RoleRightDao;
import com.netcity.module.entity.RoleRightEntity;
import com.netcity.module.service.RoleRightService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleRightService")
public class RoleRightServiceImpl<T extends BaseEntity> extends BaseServiceImpl<RoleRightEntity>
		implements RoleRightService {
	@Autowired
	RoleRightDao RoleRightDao;

	public BaseDao<RoleRightEntity> getDao() {
		return (BaseDao<RoleRightEntity>) this.RoleRightDao;
	}
}