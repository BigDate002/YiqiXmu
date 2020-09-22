package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class CodeEntity extends BaseEntity {
	private static final long serialVersionUID = 23L;
	private String prefix;
	private Long no;

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getNo() {
		return this.no;
	}

	public void setNo(Long no) {
		this.no = no;
	}
}