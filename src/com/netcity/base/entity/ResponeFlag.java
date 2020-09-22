package com.netcity.base.entity;

import java.io.Serializable;

public class ResponeFlag implements Serializable {
	private static final long serialVersionUID = 6L;
	private boolean isOk;
	private String message;

	public boolean isOk() {
		return this.isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponeFlag(boolean isOk, String message) {
		this.isOk = isOk;
		this.message = message;
	}

	public ResponeFlag() {
	}
}