package com.netcity.util;

import com.netcity.base.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;

public class QueryResult implements Serializable {
	private static final long serialVersionUID = -8521357889162060465L;
	public static final Boolean Failed = Boolean.valueOf(false);
	private Boolean flag;
	private List<? extends BaseEntity> data;
	private List<? extends BaseEntity> list;
	public static final Boolean Success = Boolean.valueOf(true);

	private String message;

	private Long code;

	public Boolean getFlag() {
		return this.flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<? extends BaseEntity> getData() {
		return this.data;
	}

	public void setData(List<? extends BaseEntity> data) {
		this.data = data;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCode() {
		return this.code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public List<? extends BaseEntity> getList() {
		return this.list;
	}

	public void setList(List<? extends BaseEntity> list) {
		this.list = list;
	}
}