package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.RoleDepartmentEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface RoleDepartmentService extends BaseService<RoleDepartmentEntity> {
	void addDept(Long paramLong, List<RoleDepartmentEntity> paramList) throws ServiceException;
}