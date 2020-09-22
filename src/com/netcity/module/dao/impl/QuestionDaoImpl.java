package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.QuestionDao;
import com.netcity.module.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

@Repository("questionDao")
public class QuestionDaoImpl extends BaseDaoImpl<QuestionEntity> implements QuestionDao {
}