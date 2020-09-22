package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;
import java.util.Date;

public class SystemLogEntity extends BaseEntity {
	private static final long serialVersionUID = -2594230140605659382L;
	private String usercode;
	private String action;
	private Boolean result;
	private Long executeMillis;
	private Date operateTime;
	private String error;
	private String param;

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getResult() {
		return this.result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public Long getExecuteMillis() {
		return this.executeMillis;
	}

	public void setExecuteMillis(Long executeMillis) {
		this.executeMillis = executeMillis;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}