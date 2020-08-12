package com.yunlong.softpark.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author 王海澎
 * @Date 2020/8/1 15:13
 * @Version 1.0
 */
@Data
public class FeedbackDto {

    /**
     * 软件名
     */
    private String softName;

    /**
     * 软件版本
     */
    private String edition;


    /**
     * 软件反馈
     */
    private List<String> commentList;

}