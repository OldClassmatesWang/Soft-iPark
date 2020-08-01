package com.yunlong.softpark.dto;

import com.yunlong.softpark.entity.PlatesEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/8/1
 * @Description:
 */
@Data
public class ColumnSortDto {

    private List<MajorColumn> columnEntities;

    private List<PlatesEntity> platesEntities;

    private List<MajorSort> sortEntities;

}
