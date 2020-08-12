package com.yunlong.softpark.dto;

import lombok.Data;

/**
 * @Author 王海澎
 * @Date 2020/7/31 21:56
 * @Version 1.0
 */
@Data
public class SoftwareBaseDataDto {

    /**
     * 软件生产方
     */
    private String username;

    /**
     * 软件语言
     */
    private String language;

    /**
     * 下载地址
     */
    private String softAddr;

    /**
     * 官方地址
     */
    private String columnWeb;

    /**
     * 版本号
     */
    private String edition;
}

