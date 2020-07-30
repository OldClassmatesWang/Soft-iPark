package com.yunlong.softpark.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@Data
public class SearchSoft {

    /**
     * 软件名
     */
    private String softName;
    /**
     * 软件logo
     */
    private String softLogo;
    /**
     * 版本信息
     */
    private String edition;
    /**
     * 软件语言
     */
    private String language;
    /**
     * 支持软件的平台
     */
    private String platform;
    /**
     * 软件大小
     */
    private String softSize;

    private String createTime;
    /**
     * 下载次数
     */
    private Integer downloads;

    private String briefIntro;
}
