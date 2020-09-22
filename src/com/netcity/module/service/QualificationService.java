package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.QualificationEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.service.BaseService;
import java.util.List;

public interface QualificationService extends BaseService<QualificationEntity> {
	void insertImport(List<QualificationEntity> paramList, UserEntity paramUserEntity) throws ServiceException;

	void updateZizhi();

	void certificateRecovery(QualificationEntity query) throws ServiceException;
}
