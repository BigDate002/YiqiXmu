package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.ConfigEntity;
import com.netcity.module.entity.ExamEntity;
import com.netcity.module.entity.PaperEntity;
import com.netcity.module.entity.QuestionEntity;
import com.netcity.module.entity.TrainEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.service.BaseService;
import com.netcity.util.LayuiPageInfo;

import java.util.List;
import java.util.Map;

public interface TrainService extends BaseService<TrainEntity> {
	void updateStop(ExamEntity paramExamEntity) throws ServiceException;

	long addQuestion(String paramString, ExamEntity paramExamEntity, UserEntity paramUserEntity,
			Map<String, List<QuestionEntity>> paramMap, List<PaperEntity> paramList, ConfigEntity paramConfigEntity);

	List<UserEntity> queryUserbypostion(TrainEntity paramTrainEntity);

	void createTrain(TrainEntity paramTrainEntity) throws ServiceException;

	LayuiPageInfo queryPage(TrainEntity course, int pageNum, int pageSize);
	
	LayuiPageInfo listByPage1(TrainEntity paramT, int paramInt1, int paramInt2);
}