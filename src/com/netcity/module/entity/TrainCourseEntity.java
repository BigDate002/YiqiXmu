package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class TrainCourseEntity extends BaseEntity {
	private static final long serialVersionUID = 51L;
	private Long trainId;
	private Long courseId;
	private String usercode;
	private String begintime;//培训开始时间
	private String endtime;//培训结束时间

	public Long getTrainId() {
		return this.trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public Long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}