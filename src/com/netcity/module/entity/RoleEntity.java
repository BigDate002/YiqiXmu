package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class RoleEntity extends BaseEntity {
	private static final long serialVersionUID = -6523889554439050631L;
	private String name;
	private String remark;
	private Long state;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}