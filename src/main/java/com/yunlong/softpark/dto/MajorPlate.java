package com.yunlong.softpark.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/8/1
 * @Description:
 */
@Data
public class MajorPlate {
    private String plateName;

    private List<MajorSort> majorSorts;
}
