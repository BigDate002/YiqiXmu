package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.PracticeDao;
import com.netcity.module.entity.PracticeEntity;
import org.springframework.stereotype.Repository;

@Repository("practiceDao")
public class PracticeDaoImpl extends BaseDaoImpl<PracticeEntity> implements PracticeDao {
}