package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcity.base.entity.BaseEntity;
import java.util.Date;

@ExcelTarget("userEntity")
public class UserEntity extends BaseEntity {
	public static final long count = 3L;
	public static final long lockMs = 300000L;
	public static final String msg = "该账号已被锁定,请五分钟后重试";
	private static final long serialVersionUID = 8L;
	@Excel(name = "工号", orderNum = "1", width = 10.0D)
	private String usercode;
	@Excel(name = "姓名", orderNum = "2", width = 10.0D)
	private String name;
	@Excel(name = "性别", orderNum = "3", width = 6.0D, replace = { "男_1", "女_0" })
	private Long sex;
	@Excel(name = "部门", orderNum = "4", width = 15.0D)
	private String department;
	@Excel(name = "科室/车间", orderNum = "5", width = 15.0D)
	private String workShop;
	@Excel(name = "班组", orderNum = "6", width = 15.0D)
	private String dept;
	private Long deptId;
	private String postionId;
	private Long roleId;
	@JsonIgnore
	private String password;
	@Excel(name = "状态", orderNum = "7", width = 6.0D, replace = { "在职_1", "离职_0" })
	private Long userState;
	@Excel(name = "备注", orderNum = "8", width = 25.0D)
	private String remark;
	private Boolean isDelete;
	private String role;
	private Long departmentId;
	private Long workShopId;
	private Date lastUpdateDate;
	private String token;
	private Long chance;
	private Date lockTime;

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostionId() {
		return this.postionId;
	}

	public void setPostionId(String postionId) {
		this.postionId = postionId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUserState() {
		return this.userState;
	}

	public void setUserState(Long userState) {
		this.userState = userState;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getWorkShopId() {
		return this.workShopId;
	}

	public void setWorkShopId(Long workShopId) {
		this.workShopId = workShopId;
	}

	public String getWorkShop() {
		return this.workShop;
	}

	public void setWorkShop(String workShop) {
		this.workShop = workShop;
	}

	public boolean equals(Object obj) {
		UserEntity u = (UserEntity) obj;
		return (u.getDept().equals(getDept()) && u.getDepartment().equals(getDepartment())
				&& u.getWorkShop().equals(getWorkShop()));
	}

	public Long getSex() {
		return this.sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getChance() {
		return this.chance;
	}

	public void setChance(Long chance) {
		this.chance = chance;
	}

	public Date getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
}