package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.RoleDepartmentDao;
import com.netcity.module.entity.RoleDepartmentEntity;
import com.netcity.module.service.RoleDepartmentService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleDepartmentService")
public class RoleDepartmentServiceImpl<T extends BaseEntity> extends BaseServiceImpl<RoleDepartmentEntity>
		implements RoleDepartmentService {
	@Autowired
	RoleDepartmentDao RoleDepartmentDao;

	public BaseDao<RoleDepartmentEntity> getDao() {
		return (BaseDao<RoleDepartmentEntity>) this.RoleDepartmentDao;
	}

	public void addDept(Long id, List<RoleDepartmentEntity> list) throws ServiceException {
		try {
			deleteById(id);
			insertBatchOnce(list);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
}