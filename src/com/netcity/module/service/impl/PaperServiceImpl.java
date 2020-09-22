package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.PaperDao;
import com.netcity.module.entity.PaperEntity;
import com.netcity.module.service.PaperService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paperService")
public class PaperServiceImpl<T extends BaseEntity> extends BaseServiceImpl<PaperEntity> implements PaperService {
	@Autowired
	PaperDao PaperDao;

	public BaseDao<PaperEntity> getDao() {
		return (BaseDao<PaperEntity>) this.PaperDao;
	}
}