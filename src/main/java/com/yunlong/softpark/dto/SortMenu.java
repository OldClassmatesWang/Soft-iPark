package com.yunlong.softpark.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/31
 * @Description:
 */
@Data
public class SortMenu {

    private String sortName;

    private List<ColumnMenu> columnMenus;
}
