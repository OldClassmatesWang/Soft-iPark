package com.yunlong.softpark.dto;

import lombok.Data;

/**
 * @Author 王海澎
 * @Date 2020/7/29 10:44
 * @Version 1.0
 */
@Data
public class SoftwareSimpIntroDto {
//    * 软件名
//     * 软件logo
//     * 栏目官网
//     * 简短介绍
//     * 版本信息
//     * 下载次数
//     * 页面展示
    //   软件大小
    //   软件平台
    /**
     * 软件名
     */
    private String softName;

    /**
     * 软件logo
     */
    private String softLogo;

    /**
     * 栏目官网
     */
    private String columnWeb;

    /**
     * 简短介绍
     */
    private String briefIntro;

    /**
     * 版本信息
     */
    private String edition;

    /**
     * 下载次数
     */
    private int downloads;

    /**
     * 页面展示
     */
    private String showPic;

    /**
     * 软件大小
     */
    private String softSize;

    /**
     * 软件平台
     */
    private String platform;
}
