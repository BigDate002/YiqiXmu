package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class RoleColumnEntity extends BaseEntity {
	private static final long serialVersionUID = 1851888436887173921L;
	private Long roleId;
	private Long columnId;

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
}