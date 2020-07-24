package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.dto.UserInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Cui
 * @Date: 2020/7/24
 * @Description:
 */
@Api(value = "SoftwareController", tags = {"软件API"})
@RestController
@Slf4j
@RequestMapping("/software")
public class SoftwareController extends BaseController<UserInfo> {
}
