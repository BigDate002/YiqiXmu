package com.netcity.module.service;

import com.alibaba.fastjson.JSONObject;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.ExamEntity;
import com.netcity.module.entity.PaperEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface ExamService extends BaseService<ExamEntity> {
	void savePaper(String paramString, List<PaperEntity> paramList, UserEntity paramUserEntity) throws ServiceException;

	void updateScore(JSONObject paramJSONObject) throws ServiceException;

	void createFushen(JSONObject paramJSONObject) throws ServiceException;
}