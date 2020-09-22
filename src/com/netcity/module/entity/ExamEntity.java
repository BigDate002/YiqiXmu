package com.netcity.module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netcity.base.entity.BaseEntity;
import java.util.Date;

public class ExamEntity extends BaseEntity {
	private static final long serialVersionUID = 16L;
	private String code;
	private Long courseId;
	private Long examtime;
	private Long passrate;
	private String courseName;
	private Date beginDate;
	private Date endDate;
	private Long totalScore;
	private Integer type;
	private Long trainId;
	private Long positionId;
	private String positionName;
	private Boolean ispass;
	private Integer state1; //数据 隐藏1  开启0


	public Integer getState1() {
		return state1;
	}

	public void setState1(Integer state1) {
		this.state1 = state1;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String Code) {
		this.code = Code;
	}

	public Long getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getPassrate() {
		return this.passrate;
	}

	public void setPassrate(Long passrate) {
		this.passrate = passrate;
	}

	public Long getExamtime() {
		return this.examtime;
	}

	public void setExamtime(Long examtime) {
		this.examtime = examtime;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTrainId() {
		return this.trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Boolean getIspass() {
		return this.ispass;
	}

	public void setIspass(Boolean ispass) {
		this.ispass = ispass;
	}

	public Long getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}
}
