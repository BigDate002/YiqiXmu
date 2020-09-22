package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.TrainUserDao;
import com.netcity.module.entity.TrainUserEntity;
import com.netcity.module.service.TrainUserService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("trainUserService")
public class TrainUserServiceImpl<T extends BaseEntity> extends BaseServiceImpl<TrainUserEntity>
		implements TrainUserService {
	@Autowired
	TrainUserDao TrainUserDao;

	public BaseDao<TrainUserEntity> getDao() {
		return (BaseDao<TrainUserEntity>) this.TrainUserDao;
	}
}