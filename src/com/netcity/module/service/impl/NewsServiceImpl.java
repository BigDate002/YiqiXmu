package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.NewsDao;
import com.netcity.module.entity.NewsEntity;
import com.netcity.module.service.NewsService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("newsService")
public class NewsServiceImpl<T extends BaseEntity> extends BaseServiceImpl<NewsEntity> implements NewsService {
	@Autowired
	NewsDao NewsDao;

	public BaseDao<NewsEntity> getDao() {
		return (BaseDao<NewsEntity>) this.NewsDao;
	}
}