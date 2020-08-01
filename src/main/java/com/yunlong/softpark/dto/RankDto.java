package com.yunlong.softpark.dto;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/8/1
 * @Description:
 */
@Data
public class RankDto {
    /**
     * 栏目名
     */
    private String columnName;

    /**
     * 栏目logo
     */
    private String columnLogo;

    /**
     * 栏目下载量
     */
    private int downloads;

    /**
     * 栏目id
     */
    private String columnId;
}
