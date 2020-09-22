package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;
import java.util.Date;

public class ExamUserEntity extends BaseEntity {
	private static final long serialVersionUID = 25L;
	private String code;
	private String name;
	private String department;
	private String workShop;
	private String usercode;
	private String username;
	private Integer state1;//数据开启状态0开启1隐藏

	//人员状态:在职_1", "离职_0
	private Long userState;


	public Long getUserState() {
		return userState;
	}

	public void setUserState(Long userState) {
		this.userState = userState;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static final Long CREATE = Long.valueOf(0L);
	public static final Long STARTE = Long.valueOf(1L);
	public static final Long FINISH = Long.valueOf(2L);
	public static final Long CANCLE = Long.valueOf(3L);
	private Long status;
	private ExamEntity exam;
	private Long positionId;
	private String postionName;

	public String getCode() {
		return this.code;
	}

	private Long chance;
	private String courseName;
	private Date submitDate;
	private String result;

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public ExamEntity getExam() {
		return this.exam;
	}

	public void setExam(ExamEntity exam) {
		this.exam = exam;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getPostionName() {
		return this.postionName;
	}

	public void setPostionName(String postionName) {
		this.postionName = postionName;
	}

	public Long getChance() {
		return this.chance;
	}

	public void setChance(Long chance) {
		this.chance = chance;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getState1() {
		return state1;
	}

	public void setState1(Integer state1) {
		this.state1 = state1;
	}

}
