package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.base.entity.LayTree;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.DepartmentDao;
import com.netcity.module.dao.UserDao;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.RoleDepartmentEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.DepartmentService;
import com.netcity.module.service.RoleDepartmentService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("departmentService1")
public class DepartmentServiceImpl<T extends BaseEntity> extends BaseServiceImpl<DepartmentEntity>
		implements DepartmentService {
	@Autowired
	RoleDepartmentService deptService;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	UserDao userDao;
	@Autowired
	RoleDepartmentService departmentService;

	public BaseDao<DepartmentEntity> getDao() {
		return (BaseDao<DepartmentEntity>) this.departmentDao;
	}

	public List<LayTree> findDepartments(UserEntity user) {
		return this.departmentDao.findDepartments(user);
	}

	public Map<String, DepartmentEntity> findDept(List<UserEntity> list) {
		return this.departmentDao.findDept(list);
	}

	public void importData() throws ServiceException {
		try {
			Date date = new Date();
			DepartmentEntity dept = this.departmentDao.findLastUpdateOne();
			List<DepartmentEntity> list = this.departmentDao.queryOutList(dept);
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			List<DepartmentEntity> all = findList(null);
			for (DepartmentEntity de : list) {
				dept = all.stream().filter(x -> x.getCode().equals(de.getCode())).findFirst().orElse(null);
				if (dept != null) {
					de.setId(dept.getId());
					de.setLastUpdateDate(date);
					updateEntity(de);

					continue;
				}

				de.setCreateUser("system");
				insert(de);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public List<DepartmentEntity> selectByRoleId(UserEntity query) {
		return this.departmentDao.selectByRoleId(query);
	}

	public void insertDept(DepartmentEntity dept, RoleDepartmentEntity roleDept) throws ServiceException {
		try {
			DepartmentEntity qr = new DepartmentEntity();
			qr.setParentId(dept.getParentId());
			qr.setName(dept.getName());
			if (CollectionUtils.isNotEmpty(findList(qr))) {
				throw new ServiceException("组织名称重复:" + dept.getName());
			}
			insert(dept);
			this.deptService.insert(roleDept);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
}