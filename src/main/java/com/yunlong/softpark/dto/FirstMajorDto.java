package com.yunlong.softpark.dto;

import com.yunlong.softpark.entity.PlatesEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@Data
public class FirstMajorDto {

    List<MajorColumn> columnList;

    List<PlatesEntity> plateList;
}
