package com.netcity.module.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netcity.base.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @outhor sunshaojun
 * @Create 2020-08-15:41
 */
@ExcelTarget("certificatesEntity")
public class CertificatesEntity extends BaseEntity {

    private static final long serialVersionUID = 7669998263226771623L;
    /**
     * “部门”
     * “科室/车间”
     * “班组”
     * “姓名”
     * “工号”
     * “证书名称”
     * “证书编号”
     * “认证时间”
     * “复审次数”
     * “复审时间”
     * “有效期(月)”
     * “证件图片（jgp、png等图片格式）”
     * “证书状态（正常/过期/待复审/复审中）”
     */

    private Long departmentId;
    @Excel(name = "部门", width = 15.0D, orderNum = "0")
    private String department;
    private Long workShopId;
    @Excel(name = "科室/车间", width = 15.0D, orderNum = "0")
    private String workShop;
    @Excel(name = "班组", width = 15.0D, orderNum = "0")
    private String workGroup;
    @Excel(name = "工号", width = 20.0D, orderNum = "5")
    private String usercode;
    @Excel(name = "姓名", orderNum = "6", width = 20.0D)
    private String handlingAgency;
    @Excel(name = "证书名称", orderNum = "1", width = 15.0D)
    private String certificateName;
    @Excel(name = "证件编号", orderNum = "1", width = 15.0D)
    private String certificateNumber;
    //认证时间
    private Date certificationTime;
    // 复审次数
    private Integer reviewCount;

    //复审时间
    private Date reviewTime;
    //有效期(月)
    private Integer termOfValidity;
    //附件 证件图片（jgp、png等图片格式）
    private List<AttachementsEntity> files;
    //证书状态（0待复审/1复审中/2正常/3过期）
    private Integer status;

    @Override
    public Long getDepartmentId() {
        return departmentId;
    }

    @Override
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getWorkShopId() {
        return workShopId;
    }

    public void setWorkShopId(Long workShopId) {
        this.workShopId = workShopId;
    }

    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
    }

    public String getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(String workGroup) {
        this.workGroup = workGroup;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getHandlingAgency() {
        return handlingAgency;
    }

    public void setHandlingAgency(String handlingAgency) {
        this.handlingAgency = handlingAgency;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(Date certificationTime) {
        this.certificationTime = certificationTime;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Integer getTermOfValidity() {
        return termOfValidity;
    }

    public void setTermOfValidity(Integer termOfValidity) {
        this.termOfValidity = termOfValidity;
    }

    public List<AttachementsEntity> getFiles() {
        return files;
    }

    public void setFiles(List<AttachementsEntity> files) {
        this.files = files;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
}
