package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class PracticeEntity extends BaseEntity {
	private static final long serialVersionUID = 17L;
	private String Code;
	private String usercode;
	private String kaohe1;
	private String kaohe2;
	private String kaohe3;
	private String kaohe4;
	private Long result;
	private Long score;

	public String getCode() {
		return this.Code;
	}

	public void setCode(String Code) {
		this.Code = Code;
	}

	public String getKaohe1() {
		return this.kaohe1;
	}

	public void setKaohe1(String kaohe1) {
		this.kaohe1 = kaohe1;
	}

	public String getKaohe2() {
		return this.kaohe2;
	}

	public void setKaohe2(String kaohe2) {
		this.kaohe2 = kaohe2;
	}

	public String getKaohe3() {
		return this.kaohe3;
	}

	public void setKaohe3(String kaohe3) {
		this.kaohe3 = kaohe3;
	}

	public String getKaohe4() {
		return this.kaohe4;
	}

	public void setKaohe4(String kaohe4) {
		this.kaohe4 = kaohe4;
	}

	public Long getResult() {
		return this.result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
}