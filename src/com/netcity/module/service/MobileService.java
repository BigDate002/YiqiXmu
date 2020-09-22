package com.netcity.module.service;

public interface MobileService {
    /**
     * 发送待办消息
     * @param userCode
     * @param title
     * @param bizId
     */
    void sentMessage(String userCode,String title,String bizId);


    /**
     * 更新待办
     * @param userCode
     * @param bizId
     */
    void updateMessage(String userCode,String bizId);
}
