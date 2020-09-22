package com.netcity.util;

import com.netcity.base.entity.BaseEntity;
import java.util.List;

public class LayuiPageInfo {
	private int code;
	private Long count;
	private List<? extends BaseEntity> data;
	private String msg;
	private Integer page;
	private Integer limit;

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Long getCount() {
		return this.count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<? extends BaseEntity> getData() {
		return this.data;
	}

	public void setData(List<? extends BaseEntity> data) {
		this.data = data;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}