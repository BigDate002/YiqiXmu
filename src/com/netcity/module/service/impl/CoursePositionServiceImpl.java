package com.netcity.module.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.CoursePositionDao;
import com.netcity.module.entity.CoursePositionEntity;
import com.netcity.module.service.CoursePositionService;
import com.netcity.service.impl.BaseServiceImpl;

@Service("coursePositionService")
public class CoursePositionServiceImpl<T extends BaseEntity>
  extends BaseServiceImpl<CoursePositionEntity>
  implements CoursePositionService
{
  @Autowired
  CoursePositionDao CoursePositionDao;
  
  public BaseDao<CoursePositionEntity> getDao() {
    return (BaseDao<CoursePositionEntity>)this.CoursePositionDao;
  }

@Override
public void deleteById2(List<CoursePositionEntity> row) throws ServiceException {
	// TODO Auto-generated method stub
	try {
		CoursePositionDao.deleteById2(row);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
}
}