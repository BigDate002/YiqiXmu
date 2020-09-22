package com.netcity.module.service;

import com.netcity.module.entity.ColumnEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.service.BaseService;
import java.util.Map;

public interface ColumnService extends BaseService<ColumnEntity> {
	Map<String, Object> getPersonInfo(UserEntity paramUserEntity);
}