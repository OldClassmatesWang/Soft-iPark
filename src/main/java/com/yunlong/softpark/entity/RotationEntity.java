package com.yunlong.softpark.entity;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Data
public class RotationEntity {

    /**
     * 轮播id
     */
    private Integer rotationId;
    /**
     *软件id
     */
    private String softwareId;
    /**
     *轮播图地址
     */
    private String rotationAddr;
    /**
     *轮播图排序
     */
    private Integer sort;
    /**
     *是否轮播
     */
    private Integer isRotation;
    /**
     *软件名称
     */
    private String softName;
}
