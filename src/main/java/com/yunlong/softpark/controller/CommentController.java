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
@Api(value = "CommentController", tags = {"评论API"})
@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController extends BaseController<UserInfo> {


}
