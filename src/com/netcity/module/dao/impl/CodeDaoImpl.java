package com.netcity.module.dao.impl;

import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.CodeDao;
import com.netcity.module.entity.CodeEntity;
import org.springframework.stereotype.Repository;

@Repository("codeDao")
public class CodeDaoImpl extends BaseDaoImpl<CodeEntity> implements CodeDao {
}