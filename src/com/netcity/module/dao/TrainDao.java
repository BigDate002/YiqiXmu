package com.netcity.module.dao;

import com.github.pagehelper.PageInfo;
import com.netcity.base.dao.BaseDao;
import com.netcity.module.entity.TrainEntity;
import com.netcity.module.entity.UserEntity;
import java.util.List;

public interface TrainDao extends BaseDao<TrainEntity> {
	List<UserEntity> queryUserbypostion(TrainEntity paramTrainEntity);

	long selectPageCount(TrainEntity q);

	List<TrainEntity> selectPage(TrainEntity q);
	
	PageInfo<TrainEntity> listByPage1(TrainEntity paramT, int paramInt1, int paramInt2);
}