package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class RightEntity extends BaseEntity {
	private static final long serialVersionUID = 7824219502745573807L;
	private String code;
	private String name;
	private Long columnId;
	private Boolean checked;
	private Long roleId;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Boolean getChecked() {
		return this.checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}