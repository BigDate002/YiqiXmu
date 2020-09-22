package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.netcity.base.entity.BaseEntity;

@ExcelTarget("staffEntity")
public class StaffEntity extends BaseEntity {
	private static final long serialVersionUID = 18531261198L;
	@Excel(name = "工学工号", orderNum = "1", width = 10.0D)
	private String ycUsercode;
	@Excel(name = "工学姓名", orderNum = "2", width = 10.0D)
	private String ycUsername;
	@Excel(name = "正式工号", orderNum = "3", width = 10.0D)
	private String gwUsercode;
	@Excel(name = "正式姓名", orderNum = "4", width = 10.0D)
	private String gwUsername;

	public String getYcUsercode() {
		return this.ycUsercode;
	}

	public void setYcUsercode(String ycUsercode) {
		this.ycUsercode = ycUsercode;
	}

	public String getYcUsername() {
		return this.ycUsername;
	}

	public void setYcUsername(String ycUsername) {
		this.ycUsername = ycUsername;
	}

	public String getGwUsercode() {
		return this.gwUsercode;
	}

	public void setGwUsercode(String gwUsercode) {
		this.gwUsercode = gwUsercode;
	}

	public String getGwUsername() {
		return this.gwUsername;
	}

	public void setGwUsername(String gwUsername) {
		this.gwUsername = gwUsername;
	}
}