package com.task.quartz;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.netcity.module.entity.SpecialCertificatesEntity;
import com.netcity.module.service.MobileService;
import com.netcity.module.service.SpecialCertificatesService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @outhor sunshaojun
 * @Create 2020-09-5:16 下午
 */
public class UpdateQualificaJob {

    @Autowired
    private SpecialCertificatesService specialCertificatesService;
    @Autowired
    private MobileService mobileService;

    public void queryCertAndSentMessage(){
        //查询出需要通知的复审的人
        SpecialCertificatesEntity specialCertificatesEntity = new SpecialCertificatesEntity();
        specialCertificatesEntity.setSentStatus(0);
        Date reviceDate = DateUtil.offset(new Date(), DateField.MONTH, 3);
        String formatDate = DateUtil.formatDate(reviceDate);
        specialCertificatesEntity.setReviewTimeStrByJob(formatDate);
        List<SpecialCertificatesEntity> list = this.specialCertificatesService.findList(specialCertificatesEntity);
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        for (SpecialCertificatesEntity special : list){
            //发送通知，更改状态
            mobileService.sentMessage(special.getUsercode(),"证件复审通知",special.getId()+"");
            SpecialCertificatesEntity newSpecial = new SpecialCertificatesEntity();
            newSpecial.setId(special.getId());
            newSpecial.setSentStatus(1);
            specialCertificatesService.certUpdateById(newSpecial);
        }
    }
}
