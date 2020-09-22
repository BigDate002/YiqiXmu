package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.netcity.base.entity.BaseEntity;

@ExcelTarget("jobReserveVO")
public class JobReserveVO extends BaseEntity {
	private static final long serialVersionUID = 24L;
	@Excel(name = "部门", orderNum = "1", width = 20.0D)
	private String department;
	@Excel(name = "科室/车间", orderNum = "2", width = 20.0D)
	private String workShop;
	@Excel(name = "班组", orderNum = "2", width = 20.0D)
	private String query;
	@Excel(name = "班组", orderNum = "7", width = 20.0D)
	private String workGroup;
	@Excel(name = "姓名", orderNum = "6", width = 20.0D)
	private String username;
	@Excel(name = "人员工号", orderNum = "5", width = 20.0D)
	private String usercode;
	@Excel(name = "岗位", orderNum = "3", width = 20.0D)
	private String postName;

	public String getDepartment() {
		return this.department;
	}

	private Long postId;
	private Long postType;
	@Excel(name = "储备人数", orderNum = "4", width = 20.0D)
	private Long postCount;
	private Long keyPostCount;
	private Long departmentId;
	private Long workShopId;
	private Long workGroupId;
	private Long rate;
	private Long total;

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

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Long getPostType() {
		return this.postType;
	}

	public void setPostType(Long postType) {
		this.postType = postType;
	}

	public Long getPostCount() {
		return this.postCount;
	}

	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}

	public Long getKeyPostCount() {
		return this.keyPostCount;
	}

	public void setKeyPostCount(Long keyPostCount) {
		this.keyPostCount = keyPostCount;
	}

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getWorkShopId() {
		return this.workShopId;
	}

	public void setWorkShopId(Long workShopId) {
		this.workShopId = workShopId;
	}

	public Long getWorkGroupId() {
		return this.workGroupId;
	}

	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	public Long getPostId() {
		return this.postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getRate() {
		return this.rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	public Long getTotal() {
		return this.total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}