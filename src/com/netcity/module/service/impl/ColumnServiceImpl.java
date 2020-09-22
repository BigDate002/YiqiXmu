package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.ColumnDao;
import com.netcity.module.entity.ColumnEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.ColumnService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("columnService")
public class ColumnServiceImpl<T extends BaseEntity> extends BaseServiceImpl<ColumnEntity> implements ColumnService {
	@Autowired
	ColumnDao ColumnDao;

	public BaseDao<ColumnEntity> getDao() {
		return (BaseDao<ColumnEntity>) this.ColumnDao;
	}

	public Map<String, Object> getPersonInfo(UserEntity user) {
		return this.ColumnDao.getPersonInfo(user);
	}
}