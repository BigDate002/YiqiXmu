package com.netcity.service;

import com.netcity.exception.ServiceException;
import com.netcity.util.LayuiPageInfo;
import java.util.List;
import java.util.Map;

public interface BaseService<T extends com.netcity.base.entity.BaseEntity> {
	T getById(Long paramLong);

	List<T> findList(T paramT);

	void updateEntity(T paramT) throws ServiceException;

	void deleteById(Long paramLong);

	T insert(T paramT);

	void deleteByIds(String paramString);

	void updateByIds(T paramT) throws ServiceException;

	LayuiPageInfo listByPage(T paramT, int paramInt1, int paramInt2);

	List<T> findListByCond(Map<String, Object> paramMap);

	void insertBatch(List<T> paramList) throws ServiceException;

	void insertBatchOnce(List<T> paramList) throws ServiceException;
}