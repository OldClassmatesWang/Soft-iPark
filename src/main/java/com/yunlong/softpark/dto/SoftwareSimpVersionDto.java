package com.yunlong.softpark.dto;

/**
 * @Author 王海澎
 * @Date 2020/7/29 9:59
 * @Version 1.0
 */

import lombok.Data;

/**
 * 用于返回显示下载版本栏的相关软件信息
 */
@Data
public class SoftwareSimpVersionDto {

    /**
     * 软件Id
     * 软件名
     * 软件logo
     * 版本号
     * 支持软件的平台
     * 软件大小
     * 软件下载地址
     * 更新时间
     * 下载次数
     * 简短介绍
     */


    /**
     * softId
     */
    private String softId;
    /**
     * 软件名
     */
    private String softName;

    /**
     * 软件logo
     */
    private String softLogo;

    /**
     * 版本号
     */
    private String edition;

    /**
     * 支持软件的平台
     */
    private String platform;

    /**
     *  软件大小
     */
    private String softSize;

    /**
     *  软件下载地址
     */
    private String softAddr;


    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 下载次数
     */
    private int downloads;

    /**
     * 简短介绍
     */
    private String briefIntro;
}
