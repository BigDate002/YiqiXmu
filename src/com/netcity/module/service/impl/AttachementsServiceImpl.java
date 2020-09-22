package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.AttachementsDao;
import com.netcity.module.entity.AttachementsEntity;
import com.netcity.module.service.AttachementsService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("attachementsService")
public class AttachementsServiceImpl<T extends BaseEntity> extends BaseServiceImpl<AttachementsEntity>
		implements AttachementsService {
	@Autowired
	AttachementsDao AttachementsDao;

	public BaseDao<AttachementsEntity> getDao() {
		return (BaseDao<AttachementsEntity>) this.AttachementsDao;
	}
}