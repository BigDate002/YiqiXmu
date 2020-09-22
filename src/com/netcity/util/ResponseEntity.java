package com.netcity.util;

import java.io.Serializable;

public class ResponseEntity implements Serializable {
	private static final long serialVersionUID = 826758075499102940L;
	private Boolean result;
	private String message;

	public Boolean getResult() {
		return this.result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}