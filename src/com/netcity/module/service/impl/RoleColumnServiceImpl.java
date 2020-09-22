package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.RoleColumnDao;
import com.netcity.module.entity.RoleColumnEntity;
import com.netcity.module.service.RoleColumnService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleColumnService")
public class RoleColumnServiceImpl<T extends BaseEntity> extends BaseServiceImpl<RoleColumnEntity>
		implements RoleColumnService {
	@Autowired
	RoleColumnDao RoleColumnDao;

	public BaseDao<RoleColumnEntity> getDao() {
		return (BaseDao<RoleColumnEntity>) this.RoleColumnDao;
	}
}