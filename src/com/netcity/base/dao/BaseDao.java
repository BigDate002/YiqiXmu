package com.netcity.base.dao;

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

public interface BaseDao<T extends com.netcity.base.entity.BaseEntity> {
	T getEntity(Long paramLong);

	List<T> findList(T paramT);

	List<T> findList(Map<String, Object> paramMap);

	void updateRow(T paramT);

	void deleteById(Long paramLong);

	void insert(T paramT);

	PageInfo<T> listByPage(T paramT, int paramInt1, int paramInt2);

	void deleteByIds(String paramString);

	void updateByIds(T paramT);

	void insertBatchOnce(List<T> paramList);
}