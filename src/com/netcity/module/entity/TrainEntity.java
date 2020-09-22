package com.netcity.module.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netcity.base.entity.BaseEntity;
import java.util.Date;
import java.util.List;

@ExcelTarget("trainEntity")
public class TrainEntity extends BaseEntity {
	private static final long serialVersionUID = 15L;
	@Excel(name = "培训课程", width = 20.0D, orderNum = "1")
	private String courseName;
	@Excel(name = "培训类型", width = 10.0D, orderNum = "2", replace = { "通用培训_0", "资质培训_1" })
	private String type;
	private Long postionId;
	@Excel(name = "部门", width = 20.0D, orderNum = "3")
	private String department;
	@Excel(name = "科室/车间", width = 20.0D, orderNum = "4")
	private String workShop;
	@Excel(name = "班组", width = 20.0D, orderNum = "5")
	private String workGroup;
	@Excel(name = "培训岗位", width = 20.0D, orderNum = "6")
	private String position;
	@Excel(name = "培训人员", width = 20.0D, orderNum = "7")
	private String username;
	@Excel(name = "开始日期", width = 20.0D, orderNum = "8", format = "yyyy-MM-dd")
	private Date beginDate;
	@Excel(name = "结束日期", width = 20.0D, orderNum = "9", format = "yyyy-MM-dd")
	private Date endDate;
	@Excel(name = "状态", width = 10.0D, orderNum = "10", replace = { "已取消_0", "正常_1", "已结束_2" })
	private Long state;
	private String usercode;
	private String positionIds;
	private List<TrainCourseEntity> courseList;
	private Integer state1;//数据开启状态0开启1隐藏
	private String ucode1;//培训讲师工号
	private String name1;//培训讲师名称
	private String begintime;//培训开始时间
	private String endtime;//培训结束时间
	//人员状态:在职_1", "离职_0
	private Long userState;

	public Long getUserState() {
		return userState;
	}

	public void setUserState(Long userState) {
		this.userState = userState;
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

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPositionIds() {
		return this.positionIds;
	}

	public void setPositionIds(String positionIds) {
		this.positionIds = positionIds;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Long getPostionId() {
		return this.postionId;
	}

	public void setPostionId(Long postionId) {
		this.postionId = postionId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<TrainCourseEntity> getCourseList() {
		return this.courseList;
	}

	public void setCourseList(List<TrainCourseEntity> courseList) {
		this.courseList = courseList;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	public Integer getState1() {
		return state1;
	}

	public void setState1(Integer state1) {
		this.state1 = state1;
	}

	public String getUcode1() {
		return ucode1;
	}

	public void setUcode1(String ucode1) {
		this.ucode1 = ucode1;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
