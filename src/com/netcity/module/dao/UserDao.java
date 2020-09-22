package com.netcity.module.dao;

import com.netcity.base.dao.BaseDao;
import com.netcity.module.entity.StaffEntity;
import com.netcity.module.entity.UserEntity;
import java.util.List;
import java.util.Map;

public interface UserDao extends BaseDao<UserEntity> {
	List<String> getPermissionsByUserName(String paramString);

	void updatePasswordReset(String paramString);

	List<UserEntity> findListByCourseId(Long paramLong);

	List<UserEntity> findFushenList(UserEntity paramUserEntity);

	UserEntity findLastUpdateOne();

	List<UserEntity> queryOutList(UserEntity paramUserEntity);

	Map<String, UserEntity> findUserMap();

	void updateUserInfo(List<StaffEntity> paramList);
}