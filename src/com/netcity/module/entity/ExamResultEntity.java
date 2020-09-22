package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.netcity.base.entity.BaseEntity;
import java.util.Date;

@ExcelTarget("examResultEntity")
public class ExamResultEntity extends BaseEntity {
	private static final long serialVersionUID = -2595880032564193823L;
	@Excel(name = "工号", width = 20.0D, orderNum = "5")
	private String usercode;
	@Excel(name = "年份", width = 20.0D, orderNum = "4")
	private Long year;
	@Excel(name = "月份", width = 20.0D, orderNum = "4")
	private Long month;
	@Excel(name = "结果", width = 20.0D, orderNum = "6")
	private String result;
	private Date createTime;

	public String getUsercode() {
		return this.usercode;
	}

	@Excel(name = "部门", width = 20.0D, orderNum = "1")
	private String department;
	@Excel(name = "科室/车间", width = 20.0D, orderNum = "2")
	private String workShop;
	@Excel(name = "班组", width = 20.0D, orderNum = "3")
	private String workGroup;
	@Excel(name = "姓名", width = 20.0D, orderNum = "5")
	private String username;
	private String beginDate;
	private String endDate;

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public Long getYear() {
		return this.year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Long getMonth() {
		return this.month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWorkShop() {
		return this.workShop;
	}

	public void setWorkShop(String workShop) {
		this.workShop = workShop;
	}

	public String getWorkGroup() {
		return this.workGroup;
	}

	public void setWorkGroup(String workGroup) {
		this.workGroup = workGroup;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}