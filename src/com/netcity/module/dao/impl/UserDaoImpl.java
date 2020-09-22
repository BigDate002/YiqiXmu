package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.UserDao;
import com.netcity.module.entity.StaffEntity;
import com.netcity.module.entity.UserEntity;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<UserEntity> implements UserDao {
	public static final String SQL_GETPERMISSIONSBYUSERNAME = "getPermissionsByUserName";
	public static final String UPDATEPASSWORD = "updatePasswordReset";
	public static final String UPDATEUSERINFO = "updateUserInfo";
	public static final String FINDLISTBYCOURSEID = "findListByCourseId";
	public static final String SQL_FINDFUSHENLIST = "findFushenList";
	/**
	 * 人力库数据源SQLSERVER，读视图同步数据
	 */
	@Autowired
	@Qualifier("sqlsessionTemplate1")
	public SqlSessionTemplate SqlSessionTemplate1;

	public List<String> getPermissionsByUserName(String username) {
		return this.sqlSessionTemplate.selectList(getSQL("getPermissionsByUserName"), username);
	}

	public void updatePasswordReset(String ids) {
		this.sqlSessionTemplate.update(getSQL("updatePasswordReset"), ids);
	}

	public List<UserEntity> findListByCourseId(Long courseId) {
		return this.sqlSessionTemplate.selectList(getSQL("findListByCourseId"), courseId);
	}

	public List<UserEntity> findFushenList(UserEntity uq) {
		return this.sqlSessionTemplate.selectList(getSQL("findFushenList"), uq);
	}

	public List<UserEntity> queryOutList(UserEntity query) {
		return this.SqlSessionTemplate1.selectList(getSQL("queryOutList"), query);
	}

	public UserEntity findLastUpdateOne() {
		return (UserEntity) this.sqlSessionTemplate.selectOne(getSQL("findLastUpdateOne"));
	}

	public Map<String, UserEntity> findUserMap() {
		return this.sqlSessionTemplate.selectMap(getSQL("findUserMap"), "usercode");
	}

	public void updateUserInfo(List<StaffEntity> list) {
		this.sqlSessionTemplate.update(getSQL("updateUserInfo"), list);
	}
}