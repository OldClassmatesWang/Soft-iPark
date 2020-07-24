package com.yunlong.softpark.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Data
public class SoftwareEntity {
    /**
     * 软件包id
     */
    private String softId;
    /**
     * 软件名
     */
    private String softName;
    /**
     * 父栏目id
     */
    private Integer parentId;
    /**
     * 软件logo
     */
    private String softLogo;
    /**
     * 授权信息
     */
    private String license;
    /**
     * 版本信息
     */
    private String edition;
    /**
     * 软件类型
     */
    private String softType;
    /**
     * 软件官网
     */
    private String softWeb;
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
    /**
     * 软件下载地址
     */
    private String softAddr;
    /**
     * 上传者id
     */
    private String userId;
    /**
     * 软件简介id
     */
    private String introduceId;

    private Date createTime;

    private Date updateTime;
    /**
     * 审核 0未审核 1通过 2未通过
     */
    private Integer verify;
    /**
     * 下载次数
     */
    private Integer downloads;

}
