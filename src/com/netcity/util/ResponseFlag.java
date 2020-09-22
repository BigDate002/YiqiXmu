package com.netcity.util;

import java.io.Serializable;

public class ResponseFlag implements Serializable {
	
	private static final long serialVersionUID = 10001;
	public static final Boolean Failed = Boolean.valueOf(false);
	public static final Boolean Success = Boolean.valueOf(true);

	private int code;
	private Object data;
	private Boolean flag;
	private Boolean success;
	private String message;
	private String msg;

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
}