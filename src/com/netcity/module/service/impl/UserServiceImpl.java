package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.UserDao;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.StaffEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.DepartmentService;
import com.netcity.module.service.PositionService;
import com.netcity.module.service.UserService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl<T extends BaseEntity> extends BaseServiceImpl<UserEntity> implements UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	PositionService positionService;

	public BaseDao<UserEntity> getDao() {
		return (BaseDao<UserEntity>) this.userDao;
	}

	public List<String> getPermissionsByUserName(String username) {
		return this.userDao.getPermissionsByUserName(username);
	}

	public void updatePasswordReset(String ids) {
		this.userDao.updatePasswordReset(ids);
	}

	public void insertImport(List<UserEntity> list) throws ServiceException {
		try {
			if (list.size() == 0)
				return;
			List<UserEntity> lis = new ArrayList<>();
			//获取所用工厂车间科室班组
			Map<String, DepartmentEntity> departments = this.departmentService.findDept(lis);
			for (UserEntity ue : list) {//处理人员关联班组或科室/车间编码
				ue.setUsercode(ue.getUsercode()!=null?ue.getUsercode().trim():null);
				ue.setName(ue.getName()!=null?ue.getName().trim():null);
				ue.setDepartment(ue.getDepartment()!=null?ue.getDepartment().trim():null);
				ue.setWorkShop(ue.getWorkShop()!=null?ue.getWorkShop().trim():null);
				ue.setDept(ue.getDept()!=null?ue.getDept().trim():null);
				ue.setRemark(ue.getRemark()!=null?ue.getRemark().trim():null);
				if (StringUtils.isNotBlank(ue.getDept())) {//判断导入数据中的班组是否在数据库存在
					String str = String.format("%s-%s-%s",
							new Object[] { ue.getDepartment(), ue.getWorkShop(), ue.getDept().trim() });
					DepartmentEntity departmentEntity = departments.get(str);
					if (departmentEntity == null) {
						throw new ServiceException(String.format("班组%s不存在", new Object[] { str }));
					}
					ue.setDeptId(Long.valueOf(departmentEntity.getCode()));//人员关联班组编码
					continue;
				}
				//如果导入数据没有班组级别，则判断科室/车间是否存在
				String key = String.format("%s-%s", new Object[] { ue.getDepartment(), ue.getWorkShop() });
				DepartmentEntity dept = departments.get(key);
				if (dept == null) {
					throw new ServiceException(String.format("科室/车间%s不存在", new Object[] { key }));
				}
				ue.setDeptId(Long.valueOf(dept.getCode()));//人员关联科室/车间
			}
			//查询所用人员id和usercode，处理根据id修改或新增
			Map<String, UserEntity> userMap = this.userDao.findUserMap();
			for (UserEntity u : list) {
				UserEntity user = userMap.get(u.getUsercode());
				if (user != null) {
					u.setId(user.getId());
					updateEntity(u);
					continue;
				}
				lis.add(u);
			}

			if (CollectionUtils.isNotEmpty(lis)) {
				insertBatchOnce(lis);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public List<UserEntity> findListByCourseId(Long courseId) {
		return this.userDao.findListByCourseId(courseId);
	}

	public List<UserEntity> findFushenList(UserEntity uq) {
		return this.userDao.findFushenList(uq);
	}

	public void importGBOMData() throws ServiceException {
		try {
			Date date = new Date();
			UserEntity dept = this.userDao.findLastUpdateOne();//获取最后lastUpdateDate的人员id和lastUpdateDate
			List<UserEntity> list = this.userDao.queryOutList(dept);//
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			List<UserEntity> all = findList(null);
			List<UserEntity> insertList = new ArrayList<>();
			for (UserEntity de : list) {
				dept = all.stream().filter(x -> x.getUsercode().equals(de.getUsercode())).findFirst().orElse(null);
				if (dept != null) {//人员是否存在数据库中，否则添加
					if (de.getUserState().longValue() == 1L) {//人员在职不进行操作否则更新
						continue;
					}
					de.setId(dept.getId());
					de.setLastUpdateDate(date);
					updateEntity(de);
					continue;
				}
				de.setCreateUser("system");
				insertList.add(de);
			}

			if (CollectionUtils.isNotEmpty(insertList)) {
				insertBatchOnce(insertList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public void updateUserInfo(List<StaffEntity> list) throws ServiceException {
		try {
			List<UserEntity> all = findList(null);
			for (StaffEntity st : list) {//判断工学/正式工号是否在数据库的Usercode存在
				UserEntity yc = all.stream().filter(x -> x.getUsercode().equals(st.getYcUsercode())).findFirst()
						.orElse(null);
				if (yc == null) {
					throw new ServiceException(String.valueOf(st.getYcUsercode()) + "工学工号不存在");
				}
				UserEntity gw = all.stream().filter(x -> x.getUsercode().equals(st.getGwUsercode())).findFirst()
						.orElse(null);
				if (gw == null) {
					throw new ServiceException(String.valueOf(st.getGwUsercode()) + "正式工号不存在");
				}
			}
			this.userDao.updateUserInfo(list);//将ex_exam_user、ex_train_user、ex_qualification中usercode的工学工号修改为正式工号
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}