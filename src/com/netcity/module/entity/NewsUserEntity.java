package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class NewsUserEntity extends BaseEntity {
	private static final long serialVersionUID = 71L;
	private String usercode;
	private Long newsId;

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public Long getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
}