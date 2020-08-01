package com.yunlong.softpark.service;

import com.yunlong.softpark.dto.ColumnDetailDto;
import com.yunlong.softpark.dto.ColumnSimDto;
import com.yunlong.softpark.dto.MessageSuccessDto;
import com.yunlong.softpark.dto.RankDto;
import com.yunlong.softpark.form.CreateColumnForm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@Component("columnService")
public interface ColumnService {

    MessageSuccessDto createColumn(CreateColumnForm createColumnForm,String userId);
    /**
     * 返回排行榜下载次数的前十位
     * @return
     */
    List<RankDto> getRankData();

    /**
     * 返回栏目的简单介绍
     * @return
     */
    ColumnSimDto getSimpleIntroduce(String columnId);

    /**
     * 返回栏目的详细介绍
     * @param columnId
     * @return
     */
    ColumnDetailDto getDetailIntroduce(String columnId);
}
