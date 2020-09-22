package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.netcity.base.entity.BaseEntity;
import javax.validation.constraints.NotNull;

@ExcelTarget("questionEntity")
public class QuestionEntity extends BaseEntity {
	private static final long serialVersionUID = 25L;
	@Excel(name = "试题类型", orderNum = "2", width = 10.0D)
	@NotNull
	private String type;
	private String title;
	@Excel(name = "试题内容", orderNum = "3", width = 60.0D)
	@NotNull(message = "bunengweikong")
	private String content;
	@Excel(name = "选项", orderNum = "4", width = 50.0D)
	private String selections;
	@Excel(name = "答案", orderNum = "5", width = 50.0D)
	private String answer;
	private Long required;
	private Long courseId;
	@Excel(name = "课程名称", orderNum = "1", width = 10.0D)
	private String courseName;
	@Excel(name = "部门", orderNum = "0", width = 10.0D)
	private String department;
	@Excel(name = "科室/车间", orderNum = "0", width = 10.0D)
	private String workShop;
	private String position;
	private Long workShopId;
	private Long departmentId;
	private Long trainId;
	private String code;
	private Long postionId;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getSelections() {
		return this.selections;
	}

	public void setSelections(String selections) {
		this.selections = selections;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWorkShop() {
		return this.workShop;
	}

	public void setWorkShop(String workShop) {
		this.workShop = workShop;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getRequired() {
		return this.required;
	}

	public void setRequired(Long required) {
		this.required = required;
	}

	public Long getWorkShopId() {
		return this.workShopId;
	}

	public void setWorkShopId(Long workShopId) {
		this.workShopId = workShopId;
	}

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getTrainId() {
		return this.trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getPostionId() {
		return this.postionId;
	}

	public void setPostionId(Long postionId) {
		this.postionId = postionId;
	}
}