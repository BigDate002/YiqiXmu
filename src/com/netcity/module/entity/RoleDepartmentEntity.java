package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class RoleDepartmentEntity extends BaseEntity {
	private static final long serialVersionUID = 51L;
	private Long roleId;
	private Long deptId;

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}