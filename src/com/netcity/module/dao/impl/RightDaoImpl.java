package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.RightDao;
import com.netcity.module.entity.RightEntity;
import org.springframework.stereotype.Repository;

@Repository("rightDao")
public class RightDaoImpl extends BaseDaoImpl<RightEntity> implements RightDao {
}