package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.netcity.base.entity.BaseEntity;
import java.util.List;

@ExcelTarget("courseEntity")
public class CourseEntity extends BaseEntity {
	private static final long serialVersionUID = 21L;
	@Excel(name = "课程类型", width = 10.0D, orderNum = "1", replace = { "通用培训_0", "资质培训_1" })
	private Long type;
	@Excel(name = "课程名称", width = 20.0D, orderNum = "1")
	private String name;
	@Excel(name = "备注", width = 50.0D, orderNum = "3")
	private String remark;
	private String positionId;
	@Excel(name = "岗位", width = 30.0D, orderNum = "2")
	private String positionName;

	public String getName() {
		return this.name;
	}

	private Long departmentId;
	@Excel(name = "部门", width = 15.0D, orderNum = "0")
	private String department;
	private Long workShopId;
	@Excel(name = "科室/车间", width = 15.0D, orderNum = "0")
	private String workShop;
	private List<PositionEntity> positions;
	@Excel(name = "班组", width = 15.0D, orderNum = "0")
	private String workGroup;

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
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

	public List<PositionEntity> getPositions() {
		return this.positions;
	}

	public void setPositions(List<PositionEntity> positions) {
		this.positions = positions;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getWorkGroup() {
		return this.workGroup;
	}

	public void setWorkGroup(String workGroup) {
		this.workGroup = workGroup;
	}
}