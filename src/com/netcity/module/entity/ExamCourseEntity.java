package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class ExamCourseEntity extends BaseEntity {
	private static final long serialVersionUID = 61L;
	private String code;
	private Long courseId;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
}