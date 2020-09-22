package com.netcity.module.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.dingtalk.api.request.OapiWorkrecordUpdateRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiWorkrecordAddResponse;
import com.dingtalk.api.response.OapiWorkrecordUpdateResponse;
import com.netcity.module.service.MobileService;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @outhor sunshaojun
 * @Create 2020-09-3:44 下午
 */
@Service("mobileService")
public class MobileServiceImpl implements MobileService {
    private final static Logger log = LoggerFactory.getLogger(MobileServiceImpl.class);


    /**
     * 发送待办消息
     * @param userCode
     * @param title
     * @param bizId
     */
    public void sentMessage(String userCode,String title,String bizId) {
        log.info("发送待办消息 userCode = {} title={} bizId={}",userCode,title,bizId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/add");
        OapiWorkrecordAddRequest req = new OapiWorkrecordAddRequest();
        req.setUserid(userCode);
        req.setCreateTime(System.currentTimeMillis());
        req.setTitle("特种工证件复审");
        req.setUrl("https://oa.dingtalk.com");
        req.setBizId(bizId);
        List<OapiWorkrecordAddRequest.FormItemVo> list2 = new ArrayList<>();
        OapiWorkrecordAddRequest.FormItemVo obj3 = new OapiWorkrecordAddRequest.FormItemVo();
        list2.add(obj3);
        obj3.setTitle(title);
        obj3.setContent("内容");
        req.setFormItemList(list2);
        OapiWorkrecordAddResponse rsp = null;
        try {
            rsp = client.execute(req, getAccessToken());
            System.out.println(rsp.getBody());
            log.info("钉钉发送待办消息返回msg {}",rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新待办
     * @param userCode
     * @param bizId
     */
    public void updateMessage(String userCode,String bizId){
        log.info("更新待办消息 userCode = {} bizId={}",userCode,bizId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/workrecord/update");
        OapiWorkrecordUpdateRequest req = new OapiWorkrecordUpdateRequest();
        req.setUserid(userCode);
        req.setRecordId(bizId);
        OapiWorkrecordUpdateResponse rsp = null;
        try {
            rsp = client.execute(req, getAccessToken());
            log.info("钉钉更新待办消息返回msg {}",rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    private String getAccessToken() {
        String accessToken = null;
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dingazgtxl0vk2p1bafj");
        request.setAppsecret("1prZT0KWp2MlClK3ajZTvvmOyxuJXkaH-7a4WyBDYaaljDXw8UwWjUhLMfHhAJKU");
        request.setHttpMethod("GET");

        try {
            OapiGettokenResponse response = client.execute(request);
            accessToken = response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

}
