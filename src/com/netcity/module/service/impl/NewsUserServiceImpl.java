package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.NewsUserDao;
import com.netcity.module.entity.NewsUserEntity;
import com.netcity.module.service.NewsUserService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("newsUserService")
public class NewsUserServiceImpl<T extends BaseEntity> extends BaseServiceImpl<NewsUserEntity>
		implements NewsUserService {
	@Autowired
	NewsUserDao NewsUserDao;

	public BaseDao<NewsUserEntity> getDao() {
		return (BaseDao<NewsUserEntity>) this.NewsUserDao;
	}
}