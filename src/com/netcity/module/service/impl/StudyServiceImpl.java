package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.StudyDao;
import com.netcity.module.entity.StudyEntity;
import com.netcity.module.service.StudyService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Azu
 * @Date: 2020/9/18-15:23
 * @Description:
 * @Param:
 **/

public class StudyServiceImpl<T extends BaseEntity> extends BaseServiceImpl<StudyEntity> implements StudyService {
    @Autowired
    StudyDao studyDao;

    @Override
    public BaseDao<StudyEntity> getDao() {
        return (BaseDao<StudyEntity>)this.studyDao;
    }
}
