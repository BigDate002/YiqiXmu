package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.PracticeDao;
import com.netcity.module.entity.PracticeEntity;
import com.netcity.module.service.PracticeService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("practiceService")
public class PracticeServiceImpl<T extends BaseEntity> extends BaseServiceImpl<PracticeEntity>
		implements PracticeService {
	@Autowired
	PracticeDao PracticeDao;

	public BaseDao<PracticeEntity> getDao() {
		return (BaseDao<PracticeEntity>) this.PracticeDao;
	}
}