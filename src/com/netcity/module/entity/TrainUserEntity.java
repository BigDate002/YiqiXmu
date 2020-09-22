package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class TrainUserEntity extends BaseEntity {
	private static final long serialVersionUID = 18L;
	private Long refId;
	private String userCode;

	public Long getRefId() {
		return this.refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}