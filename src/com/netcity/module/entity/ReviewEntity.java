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
@ExcelTarget("reviewEntity")
public class ReviewEntity extends BaseEntity {

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

    //证书名称
    @Excel(name = "证书名称", orderNum = "6", width = 15.0D)
    private String certificateName;
    //证书编号
    @Excel(name = "证书编号", orderNum = "7", width = 15.0D)
    private String certificateNumber;

    //提报时间
    @Excel(name = "提报时间", orderNum = "8", width = 15.0D,exportFormat = "yyyy-MM-dd")
    private Date reportingTime;
    //业务类型 0新申领 1复审
    @Excel(name = "业务类型", orderNum = "9", width = 15.0D, replace = { "新申领_0", "复审_1"})
    private Integer type;
    //附件
    private List<SpecialAttachementsEntity> files;

    //状态 0待复审/1复审中/2正常/3过期
    @Excel(name = "状态", orderNum = "10", width = 15.0D, replace = { "待复审_0", "复审中_1", "正常_2", "过期_3"})
    private Integer status;

    private String beginDate;
    private String endDate;

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

}
