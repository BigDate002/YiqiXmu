package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.TrainPositionDao;
import com.netcity.module.entity.TrainPositionEntity;
import org.springframework.stereotype.Repository;

@Repository("trainPositionDao")
public class TrainPositionDaoImpl extends BaseDaoImpl<TrainPositionEntity> implements TrainPositionDao {
}