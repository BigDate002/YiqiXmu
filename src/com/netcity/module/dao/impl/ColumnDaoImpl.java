package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.ColumnDao;
import com.netcity.module.entity.ColumnEntity;
import com.netcity.module.entity.UserEntity;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("columnDao")
public class ColumnDaoImpl extends BaseDaoImpl<ColumnEntity> implements ColumnDao {
	public Map<String, Object> getPersonInfo(UserEntity user) {
		return this.sqlSessionTemplate.selectOne(getSQL("getPersonInfo"), user);
	}
}