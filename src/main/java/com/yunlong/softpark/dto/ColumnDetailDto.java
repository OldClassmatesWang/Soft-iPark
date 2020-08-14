package com.yunlong.softpark.dto;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/8/1
 * @Description:
 */
@Data
public class ColumnDetailDto {
    /**
     * 栏目简介
     */
    private String briefIntro;

    /**
     * 栏目功能
     */
    private String function;

    /**
     * 栏目特色
     */
    private String character;

    /**
     * 下载地址
     */
    private String installAddr;

    /**
     * 更新日志
     */
    private String updateLog;
}
