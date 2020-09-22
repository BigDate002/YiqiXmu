package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.RightDao;
import com.netcity.module.entity.RightEntity;
import com.netcity.module.service.RightService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rightService")
public class RightServiceImpl<T extends BaseEntity> extends BaseServiceImpl<RightEntity> implements RightService {
	@Autowired
	RightDao RightDao;

	public BaseDao<RightEntity> getDao() {
		return (BaseDao<RightEntity>) this.RightDao;
	}
}