package com.yunlong.softpark.entity;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@Data
public class ColumnEntity {

    private String columnId;

    private String columnName;

    private Integer parentId;

    private String columnLogo;

    private String columnWeb;

    private String webIntroduce;

    private String columnType;

    private String license;

    private String introduceId;

    private Integer downloads;

    private String showPic;

    private Integer recommend;

    private Integer verify;

}
