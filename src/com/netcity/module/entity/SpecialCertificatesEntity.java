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
@ExcelTarget("specialCertificatesEntity")
public class SpecialCertificatesEntity extends BaseEntity {

    private static final long serialVersionUID = 7669998263226771623L;
    private Long departmentId;
    @Excel(name = "部门", width = 15.0D, orderNum = "0")
    private String department;
    private Long workShopId;
    @Excel(name = "科室/车间", width = 15.0D, orderNum = "1")
    private String workShop;
    private Long deptId;
    @Excel(name = "班组", width = 15.0D, orderNum = "2")
    private String dept;
    @Excel(name = "工号", width = 20.0D, orderNum = "3")
    private String usercode;
    @Excel(name = "姓名", orderNum = "4", width = 20.0D)
    private String username;
    @Excel(name = "办理机构", width = 15.0D, orderNum = "5", replace = { "公司办理_0", "个人办理_1"})
    private Integer handlingAgency;
    //提报时间
    @Excel(name = "提报时间", orderNum = "7", width = 15.0D,exportFormat = "yyyy-MM-dd")
    private Date reportingTime;
    //业务类型 0新申领 1复审
    @Excel(name = "业务类型", orderNum = "8", width = 15.0D, replace = { "新申领_0", "复审_1"})
    private Integer type;
    //附件
    private List<SpecialAttachementsEntity> files;

    //状态 0待复审/1复审中/2正常/3过期
    @Excel(name = "状态", orderNum = "9", width = 15.0D, replace = { "初审中_0", "复审中_1", "正常_2", "过期_3", "待换证_4"})
    private Integer status;

    private String beginDate;
    private String endDate;

    //证书名称
    @Excel(name = "证件名称", orderNum = "6", width = 15.0D)
    private String certificateName;
    //证书编号
    private String certificateNumber;

    //认证时间
    private Date certificationTime;
    // 复审次数
    private Integer reviewCount;

    //复审时间
    private Date reviewTime;
    //有效期(月)
    private Integer termOfValidity;

    //人员状态:在职_1", "离职_0
    private Long userState;

    //认证时间
    private String certificationTimeStr;

    //复审时间
    private String reviewTimeStr;
    private String reviewTimeStrByJob;

    //复审待办发送状态 0未发送 1已发送
    private Integer sentStatus;

    private String frontOfIDCard;
    private String frontOfIDCardName;
    private String reverseSideOfIDCard;
    private String reverseSideOfIDCardName;
    private String educationCertificate;
    private String educationCertificateName;
    private String otherMaterials;
    private String otherMaterialsName;

    public String getFrontOfIDCardName() {
        return frontOfIDCardName;
    }

    public void setFrontOfIDCardName(String frontOfIDCardName) {
        this.frontOfIDCardName = frontOfIDCardName;
    }

    public String getReverseSideOfIDCardName() {
        return reverseSideOfIDCardName;
    }

    public void setReverseSideOfIDCardName(String reverseSideOfIDCardName) {
        this.reverseSideOfIDCardName = reverseSideOfIDCardName;
    }

    public String getEducationCertificateName() {
        return educationCertificateName;
    }

    public void setEducationCertificateName(String educationCertificateName) {
        this.educationCertificateName = educationCertificateName;
    }

    public Integer getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(Integer sentStatus) {
        this.sentStatus = sentStatus;
    }

    public String getOtherMaterialsName() {
        return otherMaterialsName;
    }

    public void setOtherMaterialsName(String otherMaterialsName) {
        this.otherMaterialsName = otherMaterialsName;
    }

    public String getFrontOfIDCard() {
        return frontOfIDCard;
    }

    public void setFrontOfIDCard(String frontOfIDCard) {
        this.frontOfIDCard = frontOfIDCard;
    }

    public String getReverseSideOfIDCard() {
        return reverseSideOfIDCard;
    }

    public void setReverseSideOfIDCard(String reverseSideOfIDCard) {
        this.reverseSideOfIDCard = reverseSideOfIDCard;
    }

    public String getEducationCertificate() {
        return educationCertificate;
    }

    public void setEducationCertificate(String educationCertificate) {
        this.educationCertificate = educationCertificate;
    }

    public String getReviewTimeStrByJob() {
        return reviewTimeStrByJob;
    }

    public void setReviewTimeStrByJob(String reviewTimeStrByJob) {
        this.reviewTimeStrByJob = reviewTimeStrByJob;
    }

    public String getOtherMaterials() {
        return otherMaterials;
    }

    public void setOtherMaterials(String otherMaterials) {
        this.otherMaterials = otherMaterials;
    }

    public String getCertificationTimeStr() {
        return certificationTimeStr;
    }

    public void setCertificationTimeStr(String certificationTimeStr) {
        this.certificationTimeStr = certificationTimeStr;
    }

    public String getReviewTimeStr() {
        return reviewTimeStr;
    }

    public void setReviewTimeStr(String reviewTimeStr) {
        this.reviewTimeStr = reviewTimeStr;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getHandlingAgency() {
        return handlingAgency;
    }

    public void setHandlingAgency(Integer handlingAgency) {
        this.handlingAgency = handlingAgency;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getReportingTime() {
        return reportingTime;
    }

    public void setReportingTime(Date reportingTime) {
        this.reportingTime = reportingTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<SpecialAttachementsEntity> getFiles() {
        return files;
    }

    public void setFiles(List<SpecialAttachementsEntity> files) {
        this.files = files;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(Date certificationTime) {
        this.certificationTime = certificationTime;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
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

    public Long getUserState() {
        return userState;
    }

    public void setUserState(Long userState) {
        this.userState = userState;
    }
}
