package com.yunlong.softpark.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Data
public class HistorySoft {

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
    /**
     * 更新时间
     */
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
