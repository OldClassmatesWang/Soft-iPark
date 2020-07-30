package com.yunlong.softpark.entity;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@Data
public class SortEntity {

    private Integer sortId;

    private String sortName;

    private Integer parentId;

    private String sortLogo;
}
