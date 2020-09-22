package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.NewsDao;
import com.netcity.module.entity.NewsEntity;
import org.springframework.stereotype.Repository;

@Repository("newsDao")
public class NewsDaoImpl extends BaseDaoImpl<NewsEntity> implements NewsDao {
}