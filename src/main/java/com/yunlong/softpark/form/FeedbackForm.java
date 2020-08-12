package com.yunlong.softpark.form;

import lombok.Data;

/**
 * @Author 王海澎
 * @Date 2020/7/28 15:27
 * @Version 1.0
 */
@Data
public class FeedbackForm {



    /**
     * 反馈软件id
     */
    private String softId;

    /**
     * 反馈内容
     */
    private String content;

}