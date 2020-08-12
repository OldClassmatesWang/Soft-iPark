package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.ColumnDetailDto;
import com.yunlong.softpark.dto.ColumnSimDto;
import com.yunlong.softpark.dto.RankDto;
import com.yunlong.softpark.dto.UserInfo;
import com.yunlong.softpark.form.ColumnSimpForm;
import com.yunlong.softpark.form.CreateColumnForm;
import com.yunlong.softpark.service.ColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Cui
 * @Date: 2020/7/27
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/column")
public class ColumnController extends BaseController<UserInfo> {


    @Autowired
    ColumnService columnService;


    /**
     * 创建column
     * @param createColumnForm
     */
    @PostMapping("/createColumn")
    public ResultWrapper createColumn(@RequestBody CreateColumnForm createColumnForm){
        return ResultWrapper.success();
    }

    /**
     * 根据栏目的下载量返回前十的栏目
     * @return
     */
    @RequestMapping(path = "/rank",method = RequestMethod.GET)
    public ResultWrapper getRankData(){
        List<RankDto> list= columnService.getRankData();
        return ResultWrapper.successWithData(list);
    }

    /**
     * 根据columnId 搜索出栏目的简单介绍
     * @param columnSimpForm
     * @return
     */
    @RequestMapping(path = "/simpleIntro" , method = RequestMethod.POST)
    public ResultWrapper getSimpleIntroduce(@RequestBody ColumnSimpForm columnSimpForm){
        if (columnSimpForm.getColumnId()!=null){
            ColumnSimDto columnSimDto =  columnService.getSimpleIntroduce(columnSimpForm.getColumnId());
            return ResultWrapper.successWithData(columnSimDto);
        }else {
            return ResultWrapper.failure();
        }

    }

    /**
     * 根据columnId 搜出出栏目的详细介绍
     */
    @RequestMapping(path = "/detailIntro" , method = RequestMethod.POST)
    public ResultWrapper getDetailIntroduce(@RequestBody ColumnSimpForm columnSimpForm){
        ColumnDetailDto columnDetailDto = columnService.getDetailIntroduce(columnSimpForm.getColumnId());
        return ResultWrapper.successWithData(columnDetailDto);
    }
}
