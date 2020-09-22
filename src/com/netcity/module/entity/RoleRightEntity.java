package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class RoleRightEntity extends BaseEntity {
	private static final long serialVersionUID = 2806452392474318259L;
	private Long roleId;
	private Long rightId;

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRightId() {
		return this.rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}
}