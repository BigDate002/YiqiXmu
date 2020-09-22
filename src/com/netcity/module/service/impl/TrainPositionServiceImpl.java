package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.TrainPositionDao;
import com.netcity.module.entity.TrainPositionEntity;
import com.netcity.module.service.TrainPositionService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("trainPositionService")
public class TrainPositionServiceImpl<T extends BaseEntity> extends BaseServiceImpl<TrainPositionEntity>
		implements TrainPositionService {
	@Autowired
	TrainPositionDao TrainPositionDao;

	public BaseDao<TrainPositionEntity> getDao() {
		return (BaseDao<TrainPositionEntity>) this.TrainPositionDao;
	}
}