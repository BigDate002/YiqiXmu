package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.netcity.base.entity.BaseEntity;
import java.util.Date;

@ExcelTarget("examExport")
public class ExamExportEntity extends BaseEntity {
	private static final long serialVersionUID = 111L;
	@Excel(name = "工号", orderNum = "1", width = 15.0D, needMerge = true, mergeVertical = true, mergeRely = { 1 })
	private String usercode;
	@Excel(name = "姓名", orderNum = "2", width = 15.0D, needMerge = true, mergeVertical = true, mergeRely = { 1 })
	private String username;
	@Excel(name = "培训类型", orderNum = "3", width = 15.0D, needMerge = true, mergeVertical = true, mergeRely = { 1, 3 })
	private String trainType;
	@Excel(name = "部门", orderNum = "3", width = 30.0D, needMerge = true, mergeVertical = true, mergeRely = { 1 })
	private String department;
	@Excel(name = "科室/车间", orderNum = "3", width = 30.0D, needMerge = true, mergeVertical = true, mergeRely = { 1 })
	private String workShop;
	@Excel(name = "班组", orderNum = "3", width = 30.0D, needMerge = true, mergeVertical = true, mergeRely = { 1 })
	private String workGroup;
	@Excel(name = "岗位", orderNum = "3", width = 25.0D)
	private String post;
	@Excel(name = "课程", orderNum = "3", width = 25.0D)
	private String course;
	@Excel(name = "试题类型", orderNum = "3", width = 15.0D)
	private String type;
	@Excel(name = "题目", orderNum = "3", width = 80.0D)
	private String question;
	@Excel(name = "选项", orderNum = "4", width = 30.0D)
	private String selections;
	@Excel(name = "正确答案", orderNum = "4", width = 15.0D)
	private String answer;
	@Excel(name = "员工答案", orderNum = "5", width = 15.0D)
	private String userAnswer;
	@Excel(name = "题目分数", orderNum = "6", width = 15.0D)
	private Long fullscore;
	@Excel(name = "得分", orderNum = "7", width = 15.0D)
	private Long score;
	@Excel(name = "技能矩阵得分", orderNum = "7", width = 15.0D)
	private String skillScore;
	@Excel(name = "合格分数", orderNum = "7", width = 15.0D)
	private Long passScore;
	@Excel(name = "总分", orderNum = "7", width = 15.0D)
	private Long totalScore;
	@Excel(name = "考试得分", orderNum = "7", width = 15.0D)
	private Long actScore;
	@Excel(name = "考核结果", orderNum = "7", width = 15.0D, replace = { "合格_1", "不合格_2", "过期_3" })
	private Long result;
	@Excel(name = "交卷时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "7", width = 20.0D)
	private Date submitDate;

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getUserAnswer() {
		return this.userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public Long getFullscore() {
		return this.fullscore;
	}

	public void setFullscore(Long fullscore) {
		this.fullscore = fullscore;
	}

	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public String getSelections() {
		return this.selections;
	}

	public void setSelections(String selections) {
		this.selections = selections;
	}

	public String getTrainType() {
		return this.trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
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

	public String getWorkGroup() {
		return this.workGroup;
	}

	public void setWorkGroup(String workGroup) {
		this.workGroup = workGroup;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getCourse() {
		return this.course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSkillScore() {
		return this.skillScore;
	}

	public void setSkillScore(String skillScore) {
		this.skillScore = skillScore;
	}

	public Long getPassScore() {
		return this.passScore;
	}

	public void setPassScore(Long passScore) {
		this.passScore = passScore;
	}

	public Long getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Long totalScore) {
		this.totalScore = totalScore;
	}

	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Long getActScore() {
		return this.actScore;
	}

	public void setActScore(Long actScore) {
		this.actScore = actScore;
	}

	public Long getResult() {
		return this.result;
	}

	public void setResult(Long result) {
		this.result = result;
	}
}