package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.PositionDao;
import com.netcity.module.entity.JobReserveVO;
import com.netcity.module.entity.PositionEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.entity.WorkerReserveVO;
import com.netcity.module.service.PositionService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("positionService")
public class PositionServiceImpl<T extends BaseEntity> extends BaseServiceImpl<PositionEntity>
		implements PositionService {
	@Autowired
	PositionDao positionDao;

	public BaseDao<PositionEntity> getDao() {
		return (BaseDao<PositionEntity>) this.positionDao;
	}

	public Long findPositionByParam(PositionEntity pos) {
		return this.positionDao.findPositionByParam(pos);
	}

	public Map<String, PositionEntity> findPostions(UserEntity u) {
		return this.positionDao.findPostions(u);
	}

	public List<JobReserveVO> queryPositionData(Map<String, Object> param) {
		return this.positionDao.queryPositionData(param);
	}

	public List<WorkerReserveVO> queryStaffData(Map<String, Object> param) {
		return this.positionDao.queryStaffData(param);
	}

	public List<JobReserveVO> queryPositionList(Map<String, Object> param) {
		return this.positionDao.queryPositionList(param);
	}

	public List<JobReserveVO> queryStaffList(Map<String, Object> param) {
		return this.positionDao.queryStaffList(param);
	}

	public void insertImportList(List<PositionEntity> list) throws ServiceException {
		try {
			for (PositionEntity positionEntity : list) {
				PositionEntity query = new PositionEntity();
				query.setWorkGroupId(positionEntity.getWorkGroupId());
				query.setDeptId(positionEntity.getDeptId());
				query.setUpdateUser(positionEntity.getName());
				List<PositionEntity> li = findList(query);
				if (CollectionUtils.isNotEmpty(li)) {
					positionEntity.setId(((PositionEntity) li.get(0)).getId());
					updateEntity(positionEntity);
					continue;
				}
				insert(positionEntity);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public long queryPositionDataCount(Map<String, Object> param) {
		return this.positionDao.queryPositionDataCount(param);
	}

	public long queryStaffDataCount(Map<String, Object> param) {
		return this.positionDao.queryStaffDataCount(param);
	}
}