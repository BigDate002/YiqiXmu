package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;



public class CoursePositionEntity
  extends BaseEntity
{
  private static final long serialVersionUID = 22L;
  private Long courseId;
  private Long positionId;
  private PositionEntity position;
  
  public Long getCourseId() {
    return this.courseId;
  }
  
  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
  
  public Long getPositionId() {
    return this.positionId;
  }
  
  public void setPositionId(Long positionId) {
    this.positionId = positionId;
  }


  
  public PositionEntity getPosition() {
    return this.position;
  }
  
  public void setPosition(PositionEntity position) {
    this.position = position;
  }
}