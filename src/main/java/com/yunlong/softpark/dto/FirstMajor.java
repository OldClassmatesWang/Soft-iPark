package com.yunlong.softpark.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/30
 * @Description:
 */
@Data
public class FirstMajor {

    private String plateName;

    private List<MajorColumn> sonList;
}
