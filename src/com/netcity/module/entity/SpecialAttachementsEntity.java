package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class SpecialAttachementsEntity extends BaseEntity {
	private static final long serialVersionUID = 4122L;
	private Long id;
	private String url;
	private Long businessId;
	private String fileName;
	//文件类型 0身份证 1学历 2其它
	private Integer fileType;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
}
