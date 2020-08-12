package com.yunlong.softpark.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author 王海澎
 * @Date 2020/7/28 15:31
 * @Version 1.0
 */
@Data
public class FeedbackEntity {

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈时间
     */
    private Date time;

    /**
     * 反馈用户
     */
    private String userId;

    /**
     * 反馈软件id
     */
    private String softId;

}
