package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;
import java.util.List;

public class NewsEntity extends BaseEntity {
	private static final long serialVersionUID = 11131L;
	private String title;
	private String content;
	private List<AttachementsEntity> files;
	private Boolean isRead;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<AttachementsEntity> getFiles() {
		return this.files;
	}

	public void setFiles(List<AttachementsEntity> files) {
		this.files = files;
	}

	public Boolean getIsRead() {
		return this.isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
}