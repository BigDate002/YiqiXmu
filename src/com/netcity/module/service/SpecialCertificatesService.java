package com.netcity.module.service;

import com.netcity.exception.ServiceException;
import com.netcity.module.entity.SpecialAttachementsEntity;
import com.netcity.module.entity.SpecialCertificatesEntity;
import com.netcity.service.BaseService;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SpecialCertificatesService extends BaseService<SpecialCertificatesEntity> {

    int insertSpecialAttachements(SpecialAttachementsEntity specialAttachementsEntity);

    void downloadFiles(SpecialCertificatesEntity query, HttpServletRequest request, HttpServletResponse response);

    void certUpdateById(SpecialCertificatesEntity query);

    void updateSpecialAttachementsById(Long specialId,List<SpecialAttachementsEntity> list);

}
