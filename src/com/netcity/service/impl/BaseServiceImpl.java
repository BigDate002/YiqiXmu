package com.netcity.service.impl;

import com.github.pagehelper.PageInfo;
import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.service.BaseService;
import com.netcity.util.LayuiPageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("baseService")
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {
	public abstract BaseDao<T> getDao();

	public T getById(Long id) {
		return (T) getDao().getEntity(id);
	}

	public List<T> findList(T e) {
		return getDao().findList(e);
	}

	public void updateEntity(T e) throws ServiceException {
		getDao().updateRow(e);
	}

	public void deleteById(Long id) {
		getDao().deleteById(id);
	}

	public T insert(T t) {
		getDao().insert(t);
		return t;
	}

	public LayuiPageInfo listByPage(T t, int pageNum, int pageSize) {
		LayuiPageInfo result = new LayuiPageInfo();
		PageInfo<T> page = getDao().listByPage(t, pageNum, pageSize);
		if (pageNum > 1 && page.getList().size() == 0)
			if (page.getTotal() > 0L) {
				pageNum = (int) Math.ceil((page.getTotal() / pageSize));
				page = getDao().listByPage(t, pageNum, pageSize);
			} else {
				pageNum = 1;
			}
		result.setCode(0);
		result.setLimit(Integer.valueOf(pageSize));
		result.setPage(Integer.valueOf(pageNum));
		result.setCount(Long.valueOf(page.getTotal()));
		result.setData(page.getList());
		return result;
	}

	public void deleteByIds(String ids) {
		getDao().deleteByIds(ids);
	}

	public List<T> findListByCond(Map<String, Object> map) {
		return getDao().findList(map);
	}

	public void insertBatch(List<T> list) throws ServiceException {
		try {
			for (T baseEntity : list)
				getDao().insert(baseEntity);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void updateByIds(T t) throws ServiceException {
		getDao().updateByIds(t);
	}

	public void insertBatchOnce(List<T> list) throws ServiceException {
		getDao().insertBatchOnce(list);
	}
}