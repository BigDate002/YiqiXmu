package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.RoleColumnEntity;
import com.netcity.module.entity.RoleEntity;
import com.netcity.module.entity.RoleRightEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface RoleService extends BaseService<RoleEntity> {
	void addColumn(Long paramLong, List<RoleColumnEntity> paramList) throws ServiceException;

	void addRight(Long paramLong, List<RoleRightEntity> paramList) throws ServiceException;
}