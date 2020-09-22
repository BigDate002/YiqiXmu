package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.AttachementsDao;
import com.netcity.module.entity.AttachementsEntity;
import org.springframework.stereotype.Repository;

@Repository("attachementsDao")
public class AttachementsDaoImpl extends BaseDaoImpl<AttachementsEntity> implements AttachementsDao {
}