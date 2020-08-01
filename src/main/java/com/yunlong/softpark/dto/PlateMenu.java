package com.yunlong.softpark.dto;

import com.yunlong.softpark.entity.SortEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/31
 * @Description:
 */
@Data
public class PlateMenu {

    private String plateName;

    private List<SortMenu> sonList;
}
