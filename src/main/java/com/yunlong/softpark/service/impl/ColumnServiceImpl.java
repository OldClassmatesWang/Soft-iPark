package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.ColumnDetailDto;
import com.yunlong.softpark.dto.ColumnSimDto;
import com.yunlong.softpark.dto.MessageSuccessDto;
import com.yunlong.softpark.dto.RankDto;
import com.yunlong.softpark.form.CreateColumnForm;
import com.yunlong.softpark.mapper.ColumnMapper;
import com.yunlong.softpark.mapper.IntroduceMapper;
import com.yunlong.softpark.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    ColumnMapper columnMapper;

    @Autowired
    IntroduceMapper introduceMapper;


    /**
     * 返回排行榜下载次数的前十位
     *
     * @return
     */
    @Override
    public List<RankDto> getRankData() {
        List<RankDto> list = columnMapper.selectDataForRank();
        list.sort(Comparator.comparing(RankDto::getDownloads).reversed());
        List<RankDto> rankDtos = new ArrayList<>();
        if (list.size()>10){
            for(int i = 0 ; i < 10 ; i ++){
                rankDtos.add(list.get(i));
            }
        }else {
            System.out.println("COLUMN栏目数据不足");
            for(int i = 0 ; i < list.size() ; i ++){
                rankDtos.add(list.get(i));
            }
        }
        return rankDtos;
    }

    /**
     * 返回栏目的简单介绍
     *
     * @return
     */
    @Override
    public ColumnSimDto getSimpleIntroduce(String columnId) {
        ColumnSimDto columnSimDto = columnMapper.selectDataForSimpleColumn(columnId);

        return columnSimDto;
    }

    /**
     * 返回栏目的详细介绍
     *
     * @param columnId
     * @return
     */
    @Override
    public ColumnDetailDto getDetailIntroduce(String columnId) {
        String introduceId = columnMapper.selectIntroducIdBycolumnId(columnId);
        ColumnDetailDto columnDetailDto = introduceMapper.selectDataForDetailColumn(introduceId);
        return columnDetailDto;
    }
    /**
     * 创建栏目
     * @param createColumnForm
     * @param userId
     * @return
     */
    @Override
    public MessageSuccessDto createColumn(CreateColumnForm createColumnForm,String userId) {

        String columnId = UUID.randomUUID().toString().replace("-","");
        String introduceId = UUID.randomUUID().toString().replace("-","");
        //创建栏目
        columnMapper.insertColumn(columnId,createColumnForm.getColumnName(),createColumnForm.getParentId(),
                createColumnForm.getColumnType(),createColumnForm.getLicense(),createColumnForm.getColumnLogo(),
                createColumnForm.getColumnWeb(),createColumnForm.getWebIntroduce(),
                introduceId,createColumnForm.getShowPic(),0,0,0);
        //添加介绍
        introduceMapper.insertIntroduce(introduceId,createColumnForm.getBriefIntro(),createColumnForm.getFunction(),
                createColumnForm.getCharacter(),createColumnForm.getUpdateLog());
        MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
        messageSuccessDto.setMessage("添加成功！");
        return messageSuccessDto;
    }
}
