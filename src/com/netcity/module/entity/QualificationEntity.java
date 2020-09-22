package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netcity.base.entity.BaseEntity;
import java.util.Date;

@ExcelTarget("qualificationEntity")
public class QualificationEntity extends BaseEntity {
	private static final long serialVersionUID = 14L;
	private String code;
	@Excel(orderNum = "1", name = "工号", width = 20.0D)
	private String userCode;
	private Long postionId;
	private String beginDate1;
	private String beginDate2;
	private String endDate1;
	private String endDate2;
	private String grantUser;
	@Excel(orderNum = "2", name = "姓名", width = 20.0D)
	private String username;
	@Excel(orderNum = "3", name = "部门", width = 20.0D)
	private String department;
	@Excel(orderNum = "4", name = "科室/车间", width = 20.0D)
	private String workShop;
	@Excel(orderNum = "5", name = "班组", width = 20.0D)
	private String workGroup;
	@Excel(orderNum = "6", name = "岗位", width = 20.0D)
	private String post;
	@Excel(orderNum = "7", name = "有效期限（月）", width = 20.0D)
	private Long effectiveDate;
	@Excel(orderNum = "8", name = "生效日期", width = 20.0D, format = "yyyy-MM-dd")
	private Date beginDate;
	@Excel(orderNum = "9", name = "失效日期", width = 20.0D, format = "yyyy-MM-dd")
	private Date endDate;
	@Excel(orderNum = "10", type = 10, name = "复审次数", width = 20.0D)
	private Long type;
	private Long state1;//收回/作废状态 0可用 1已收回
	/**
	 * 正常
	 */
	public static final Long NORMAL = 0L;
	/**
	 * 待复审
	 */
	public static final Long TOBEREVIEWED = 1L;
	/**
	 * 过期
	 */
	public static final Long EXPIRED = 2L;
	/**
	 * 复审中
	 */
	public static final Long REVIEWING = 3L;
	/**
	 * 不合格
	 */
	public static final Long UNQUALIFIED = 4L;
	private Long state;

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Long getPostionId() {
		return this.postionId;
	}

	public void setPostionId(Long postionId) {
		this.postionId = postionId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGrantUser() {
		return this.grantUser;
	}

	public void setGrantUser(String grantUser) {
		this.grantUser = grantUser;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Long effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getBeginDate1() {
		return this.beginDate1;
	}

	public void setBeginDate1(String beginDate1) {
		this.beginDate1 = beginDate1;
	}

	public String getBeginDate2() {
		return this.beginDate2;
	}

	public void setBeginDate2(String beginDate2) {
		this.beginDate2 = beginDate2;
	}

	public String getEndDate1() {
		return this.endDate1;
	}

	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}

	public String getEndDate2() {
		return this.endDate2;
	}

	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}

	public Long getState1() {
		return state1;
	}

	public void setState1(Long state1) {
		this.state1 = state1;
	}
	
}