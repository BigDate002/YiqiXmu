package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.base.entity.LayTree;
import com.netcity.module.dao.DepartmentDao;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.UserEntity;
import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("departmentDao1")
public class DepartmentDaoImpl extends BaseDaoImpl<DepartmentEntity> implements DepartmentDao {
	public static final String SELECT_TREE = "findDepartments";
	public static final String SELECT_DEPT = "findDept";
	/**
	 * 人力库数据源SQLSERVER，读视图同步数据
	 */
	@Autowired
	@Qualifier("sqlsessionTemplate1")
	public SqlSessionTemplate SqlSessionTemplate1;

	public List<LayTree> findDepartments(UserEntity user) {
		return this.sqlSessionTemplate.selectList(getSQL("findDepartments"), user);
	}

	public Map<String, DepartmentEntity> findDept(List<UserEntity> list) {
		return this.sqlSessionTemplate.selectMap(getSQL("findDept"), list, "name");
	}

	public List<DepartmentEntity> queryOutList(DepartmentEntity query) {
		return this.SqlSessionTemplate1.selectList(getSQL("queryOutList"), query);
	}

	public DepartmentEntity findLastUpdateOne() {
		return (DepartmentEntity) this.sqlSessionTemplate.selectOne(getSQL("findLastUpdateOne"));
	}

	public List<DepartmentEntity> selectByRoleId(UserEntity user) {
		return this.sqlSessionTemplate.selectList(getSQL("selectByRoleId"), user);
	}
}