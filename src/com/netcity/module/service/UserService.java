package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.StaffEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface UserService extends BaseService<UserEntity> {
	List<String> getPermissionsByUserName(String paramString);

	void updatePasswordReset(String paramString);

	void insertImport(List<UserEntity> paramList) throws ServiceException;

	List<UserEntity> findListByCourseId(Long paramLong);

	List<UserEntity> findFushenList(UserEntity paramUserEntity);

	void importGBOMData() throws ServiceException;

	void updateUserInfo(List<StaffEntity> paramList) throws ServiceException;
}