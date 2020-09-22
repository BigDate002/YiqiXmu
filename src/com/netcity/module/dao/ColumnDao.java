package com.netcity.module.dao;

import com.netcity.base.dao.BaseDao;
import com.netcity.module.entity.ColumnEntity;
import com.netcity.module.entity.UserEntity;
import java.util.Map;

public interface ColumnDao extends BaseDao<ColumnEntity> {
	Map<String, Object> getPersonInfo(UserEntity paramUserEntity);
}