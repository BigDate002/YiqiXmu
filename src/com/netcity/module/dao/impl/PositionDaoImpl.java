package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.PositionDao;
import com.netcity.module.entity.JobReserveVO;
import com.netcity.module.entity.PositionEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.entity.WorkerReserveVO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("positionDao")
public class PositionDaoImpl extends BaseDaoImpl<PositionEntity> implements PositionDao {
	public Long findPositionByParam(PositionEntity pos) {
		return (Long) this.sqlSessionTemplate.selectOne(getSQL("selectDepartment"), pos);
	}

	public Map<String, PositionEntity> findPostions(UserEntity lis) {
		return this.sqlSessionTemplate.selectMap(getSQL("selectPositions"), lis, "name");
	}

	public List<JobReserveVO> queryPositionData(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList(getSQL("queryPositionData"), param);
	}

	public List<WorkerReserveVO> queryStaffData(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList(getSQL("queryStaffData"), param);
	}

	public List<JobReserveVO> queryPositionList(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList(getSQL("queryPositionList"), param);
	}

	public List<JobReserveVO> queryStaffList(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList(getSQL("queryStaffList"), param);
	}

	public List<String> findUsers(UserEntity u) {
		return this.sqlSessionTemplate.selectList(getSQL("selectUsers"), u);
	}

	public long queryPositionDataCount(Map<String, Object> param) {
		return ((Long) this.sqlSessionTemplate.selectOne(getSQL("queryPositionDataCount"), param)).longValue();
	}

	public long queryStaffDataCount(Map<String, Object> param) {
		return ((Long) this.sqlSessionTemplate.selectOne(getSQL("queryStaffDataCount"), param)).longValue();
	}
}