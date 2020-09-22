package com.netcity.module.service.impl;

import com.netcity.module.entity.DictEntity;
import com.netcity.module.service.DictService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述
 *  字典表
 * @outhor sunshaojun
 * @Create 2020-08-19:18
 */
@Service("dictServiceImpl")
public class DictServiceImpl implements DictService {

    @Autowired
    @Qualifier("sqlsessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate;


    @Override
    public DictEntity queryDictNameByDictValue(String dictType, String dictValue) {
        if (null == dictType || null == dictValue){
            return null;
        }
        DictEntity dictEntity = new DictEntity();
        dictEntity.setDictType(dictType);
        dictEntity.setDictValue(dictValue);
        return sqlSessionTemplate.selectOne("com.netcity.module.entity.Dict.queryDictNameByDictValue",dictEntity);
    }

    @Override
    public List<DictEntity> queryDictByDictType(String dictType) {
        if (null == dictType){
            return null;
        }
        DictEntity dictEntity = new DictEntity();
        dictEntity.setDictType(dictType);
        return sqlSessionTemplate.selectList("com.netcity.module.entity.Dict.queryDictByDictType",dictEntity);
    }
}
