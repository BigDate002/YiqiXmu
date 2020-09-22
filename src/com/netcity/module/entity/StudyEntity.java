package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

/**
 * @Author: Azu
 * @Date: 2020/9/18-14:00
 * @Description: 学习实体类
 * @Param:
 **/

public class StudyEntity extends BaseEntity {
    //序列化 西内
    private static final long serialVersionUID = 2546427709L;
    /**
     * id
     * 用户工号
     * 用户课程id
     * 用户打卡状态
     * 用户笔记
     * 用户图片记录url
     * 与train表关联字段 打卡状态 studyState
     */
    private long id;
    private String usercode;
    private long courseId;
    private int studyState;
    private String studyNotes;
    private String studyImgUrl;

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getStudyState() {
        return studyState;
    }

    public void setStudyState(int studyState) {
        this.studyState = studyState;
    }

    public String getStudyNotes() {
        return studyNotes;
    }

    public void setStudyNotes(String studyNotes) {
        this.studyNotes = studyNotes;
    }

    public String getStudyImgUrl() {
        return studyImgUrl;
    }

    public void setStudyImgUrl(String studyImgUrl) {
        this.studyImgUrl = studyImgUrl;
    }
}
