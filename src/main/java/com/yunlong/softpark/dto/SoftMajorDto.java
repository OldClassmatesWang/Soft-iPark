package com.yunlong.softpark.dto;

import com.yunlong.softpark.entity.ColumnEntity;
import com.yunlong.softpark.entity.PlatesEntity;
import com.yunlong.softpark.entity.SortEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@Data
public class SoftMajorDto {

    List<PlatesEntity> platesEntities;

    List<MajorSort> sortEntities;

    List<MajorColumn> columnEntities;
}
