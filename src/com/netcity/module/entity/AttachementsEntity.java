package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class AttachementsEntity extends BaseEntity {
	private static final long serialVersionUID = 41L;
	private String url;
	private Long newsId;
	private String fileName;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}