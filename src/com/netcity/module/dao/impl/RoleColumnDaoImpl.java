package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.RoleColumnDao;
import com.netcity.module.entity.RoleColumnEntity;
import org.springframework.stereotype.Repository;

@Repository("roleColumnDao")
public class RoleColumnDaoImpl extends BaseDaoImpl<RoleColumnEntity> implements RoleColumnDao {
}