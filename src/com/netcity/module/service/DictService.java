package com.netcity.module.service;

import com.netcity.module.entity.DictEntity;

import java.util.List;

public interface DictService {

    DictEntity queryDictNameByDictValue(String dictType,String dictValue);

    List<DictEntity> queryDictByDictType(String dictType);



}
