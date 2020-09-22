package com.netcity.module.dao.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.dao.impl.BaseDaoImpl;
import com.netcity.module.dao.StudyDao;
import com.netcity.module.entity.StudyEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class StudyDaoImpl extends BaseDaoImpl<StudyEntity> implements StudyDao {
    //打卡查询已经在queryPage.do调用了无需再调
    //更新打卡状态
    public List<StudyEntity> updateStudy(StudyEntity q) {
        return sqlSessionTemplate.selectList(getSQL("updateStudy"), q);
    }
    //记录学习数据
    public List<StudyEntity> insertStudy(StudyEntity q) {
        return sqlSessionTemplate.selectList(getSQL("insertStudy"), q);
    }
}
