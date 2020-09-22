package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.RoleColumnDao;
import com.netcity.module.dao.RoleDao;
import com.netcity.module.dao.RoleRightDao;
import com.netcity.module.entity.RoleColumnEntity;
import com.netcity.module.entity.RoleEntity;
import com.netcity.module.entity.RoleRightEntity;
import com.netcity.module.service.RoleService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl<T extends BaseEntity> extends BaseServiceImpl<RoleEntity> implements RoleService {
	@Autowired
	RoleDao RoleDao;
	@Autowired
	RoleColumnDao roleColumnDao;
	@Autowired
	RoleRightDao roleRightDao;

	public BaseDao<RoleEntity> getDao() {
		return (BaseDao<RoleEntity>) this.RoleDao;
	}

	public void addColumn(Long id, List<RoleColumnEntity> list) throws ServiceException {
		try {
			deleteById(id);
			if (list.size() == 0)
				return;
			for (RoleColumnEntity entity : list) {
				this.roleColumnDao.insert(entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void addRight(Long id, List<RoleRightEntity> list) throws ServiceException {
		try {
			this.roleRightDao.deleteById(id);
			if (list.size() == 0)
				return;
			for (RoleRightEntity entity : list) {
				this.roleRightDao.insert(entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
}