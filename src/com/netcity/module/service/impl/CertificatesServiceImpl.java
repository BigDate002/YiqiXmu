package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.module.dao.CertificatesDao;
import com.netcity.module.entity.CertificatesEntity;
import com.netcity.module.service.CertificatesService;
import com.netcity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("certificatesService")
public class CertificatesServiceImpl<T extends BaseEntity> extends BaseServiceImpl<CertificatesEntity> implements CertificatesService {

    @Autowired
    CertificatesDao certificatesDao;

    public BaseDao<CertificatesEntity> getDao() {
        return (BaseDao<CertificatesEntity>) this.certificatesDao;
    }
}
