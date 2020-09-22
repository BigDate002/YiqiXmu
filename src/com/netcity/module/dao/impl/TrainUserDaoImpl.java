package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.TrainUserDao;
import com.netcity.module.entity.TrainUserEntity;
import org.springframework.stereotype.Repository;

@Repository("trainUserDao")
public class TrainUserDaoImpl extends BaseDaoImpl<TrainUserEntity> implements TrainUserDao {
}