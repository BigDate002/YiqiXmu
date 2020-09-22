package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.NewsUserDao;
import com.netcity.module.entity.NewsUserEntity;
import org.springframework.stereotype.Repository;

@Repository("newsUserDao")
public class NewsUserDaoImpl extends BaseDaoImpl<NewsUserEntity> implements NewsUserDao {
}