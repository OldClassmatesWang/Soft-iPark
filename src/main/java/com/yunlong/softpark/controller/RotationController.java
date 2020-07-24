package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.UserInfo;
import com.yunlong.softpark.service.RotationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Api(value = "RotationController", tags = {"轮播图API"})
@RestController
@Slf4j
@RequestMapping("/rotation")
public class RotationController extends BaseController<UserInfo> {

    @Autowired
    RotationService rotationService;

    @ApiOperation(value = "获取轮播图")
    @GetMapping("/getRotation")
    public ResultWrapper getRotation(){
        return ResultWrapper.successWithData(rotationService.getRotation());
    }
}
