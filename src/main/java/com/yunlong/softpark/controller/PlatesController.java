package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.dto.UserInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author Cui
 * @email ${email}
 * @date 2020-07-21 16:54:16
 */
@Api(value = "PlatesController", tags = {"板块API"})
@RestController
@Slf4j
@RequestMapping("/plates")
public class PlatesController extends BaseController<UserInfo> {


}
