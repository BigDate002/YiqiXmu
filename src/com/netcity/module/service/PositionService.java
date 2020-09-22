package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.JobReserveVO;
import com.netcity.module.entity.PositionEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.entity.WorkerReserveVO;
import com.netcity.service.BaseService;
import java.util.List;
import java.util.Map;

public interface PositionService extends BaseService<PositionEntity> {
	Long findPositionByParam(PositionEntity paramPositionEntity);

	Map<String, PositionEntity> findPostions(UserEntity paramUserEntity);

	List<JobReserveVO> queryPositionData(Map<String, Object> paramMap);

	List<WorkerReserveVO> queryStaffData(Map<String, Object> paramMap);

	List<JobReserveVO> queryPositionList(Map<String, Object> paramMap);

	List<JobReserveVO> queryStaffList(Map<String, Object> paramMap);

	void insertImportList(List<PositionEntity> paramList) throws ServiceException;

	long queryPositionDataCount(Map<String, Object> paramMap);

	long queryStaffDataCount(Map<String, Object> paramMap);
}