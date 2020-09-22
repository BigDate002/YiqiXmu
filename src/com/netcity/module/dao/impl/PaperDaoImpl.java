package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.PaperDao;
import com.netcity.module.entity.PaperEntity;
import org.springframework.stereotype.Repository;

@Repository("paperDao")
public class PaperDaoImpl extends BaseDaoImpl<PaperEntity> implements PaperDao {
}