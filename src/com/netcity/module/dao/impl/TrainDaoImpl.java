package com.netcity.module.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.TrainDao;
import com.netcity.module.entity.TrainEntity;
import com.netcity.module.entity.UserEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("trainDao")
public class TrainDaoImpl extends BaseDaoImpl<TrainEntity> implements TrainDao {
	public List<UserEntity> queryUserbypostion(TrainEntity q) {
		return sqlSessionTemplate.selectList(getSQL("queryUserbypostion"), q);
	}

	@Override
	public long selectPageCount(TrainEntity q) {
		return sqlSessionTemplate.selectOne(getSQL("selectPageCount"), q);
	}

	@Override
	public List<TrainEntity> selectPage(TrainEntity q) {
		return sqlSessionTemplate.selectList(getSQL("selectPage"), q);
	}

	@Override
	public PageInfo<TrainEntity> listByPage1(TrainEntity paramT, int paramInt1, int paramInt2) {
		PageHelper.startPage(paramInt1, paramInt2);
		List<TrainEntity> list = this.sqlSessionTemplate.selectList(getSQL("selectByEntity1"), paramT);
		PageInfo<TrainEntity> page = new PageInfo<TrainEntity>(list);
		return page;
	}

}
