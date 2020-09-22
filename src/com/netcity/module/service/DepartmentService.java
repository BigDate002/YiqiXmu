package com.netcity.module.service;

import com.netcity.base.entity.LayTree;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.RoleDepartmentEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.service.BaseService;
import java.util.List;
import java.util.Map;

public interface DepartmentService extends BaseService<DepartmentEntity> {
	List<LayTree> findDepartments(UserEntity paramUserEntity);

	Map<String, DepartmentEntity> findDept(List<UserEntity> paramList);

	void importData() throws ServiceException;

	List<DepartmentEntity> selectByRoleId(UserEntity paramUserEntity);

	void insertDept(DepartmentEntity paramDepartmentEntity, RoleDepartmentEntity paramRoleDepartmentEntity)
			throws ServiceException;
}