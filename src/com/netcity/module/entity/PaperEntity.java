package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class PaperEntity extends BaseEntity {
	private static final long serialVersionUID = 13L;
	private String code;
	private Long questionId;
	private String answer;
	private String userCode;
	private Long score;
	private QuestionEntity question;
	private Long score1;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public QuestionEntity getQuestion() {
		return this.question;
	}

	public void setQuestion(QuestionEntity question) {
		this.question = question;
	}

	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getScore1() {
		return this.score1;
	}

	public void setScore1(Long score1) {
		this.score1 = score1;
	}
}