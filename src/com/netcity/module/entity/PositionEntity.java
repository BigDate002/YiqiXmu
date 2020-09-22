package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.netcity.base.entity.BaseEntity;

@ExcelTarget("positionEntity")
public class PositionEntity extends BaseEntity {
	private static final long serialVersionUID = 7669998263226771615L;
	@Excel(name = "岗位名称", orderNum = "1", width = 15.0D)
	private String name;
	@Excel(name = "岗位性质", orderNum = "2", replace = { "关键岗_1", "普通岗_0" }, width = 15.0D)
	private Long type;
	private Long deptId;
	@Excel(name = "复审时间（月）", orderNum = "6", width = 15.0D)
	private Long examPeriod;
	@Excel(name = "备注", orderNum = "7", width = 80.0D)
	private String remark;
	@Excel(name = "部门", orderNum = "3", width = 15.0D)
	private String department;
	@Excel(name = "科室/车间", orderNum = "4", width = 15.0D)
	private String workShop;
	@Excel(name = "班组", orderNum = "4", width = 15.0D)
	private String workGroup;
	private Long departmentId;
	private Long workGroupId;
	private Long courseId;


	//岗位类别 --字典 sys_post_category
	private Integer postCategory;

	private String postCategoryStr;

	public String getPostCategoryStr() {
		return postCategoryStr;
	}

	public void setPostCategoryStr(String postCategoryStr) {
		this.postCategoryStr = postCategoryStr;
	}

	public Integer getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(Integer postCategory) {
		this.postCategory = postCategory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getExamPeriod() {
		return this.examPeriod;
	}

	public void setExamPeriod(Long examPeriod) {
		this.examPeriod = examPeriod;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getWorkGroup() {
		return this.workGroup;
	}

	public void setWorkGroup(String workGroup) {
		this.workGroup = workGroup;
	}

	public Long getWorkGroupId() {
		return this.workGroupId;
	}

	public void setWorkGroupId(Long workGroupId) {
		this.workGroupId = workGroupId;
	}

	public Long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
}
