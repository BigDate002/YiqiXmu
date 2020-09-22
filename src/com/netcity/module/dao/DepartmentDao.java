package com.netcity.module.dao;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.LayTree;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.UserEntity;
import java.util.List;
import java.util.Map;

public interface DepartmentDao extends BaseDao<DepartmentEntity> {
	List<LayTree> findDepartments(UserEntity paramUserEntity);

	Map<String, DepartmentEntity> findDept(List<UserEntity> paramList);

	List<DepartmentEntity> queryOutList(DepartmentEntity paramDepartmentEntity);

	DepartmentEntity findLastUpdateOne();

	List<DepartmentEntity> selectByRoleId(UserEntity paramUserEntity);
}