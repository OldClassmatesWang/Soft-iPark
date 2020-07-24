package com.yunlong.softpark.controller;

import com.yunlong.softpark.core.exception.SysException;
import com.yunlong.softpark.core.support.web.controller.BaseController;
import com.yunlong.softpark.core.wrapper.ResultWrapper;
import com.yunlong.softpark.dto.LoginSuccessDto;
import com.yunlong.softpark.dto.MessageSuccessDto;
import com.yunlong.softpark.dto.TokenInfo;
import com.yunlong.softpark.dto.UserInfo;
import com.yunlong.softpark.form.*;
import com.yunlong.softpark.redis.RedisRepository;
import com.yunlong.softpark.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Cui
 * @Date: 2020/7/23
 * @Description:
 */
@Api(value = "UserController", tags = {"用户API"})
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController extends BaseController<UserInfo> {

    @Autowired
    UserService userService;

    @Autowired
    RedisRepository redisRepository;

    /**
     * 注册
     * @param registerForm
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    // TODO 添加JSR303验证
    public ResultWrapper register(/*@Valid*/ @RequestBody RegisterForm registerForm){
        try {
//            redisRepository.saveMessageCode("15698614402","123456");
            MessageSuccessDto messageSuccessDto = userService.register(registerForm);
            return ResultWrapper.successWithData(messageSuccessDto);
        }catch (SysException e){
            log.info("UserController.register");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 登录
     * @param loginForm
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResultWrapper login(@RequestBody LoginForm loginForm){
        try {
            LoginSuccessDto loginSuccessDto = userService.login(loginForm);
            return ResultWrapper.successWithData(loginSuccessDto);
        }catch (SysException e){
            log.info("UserController.login");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 忘记密码
     * @param forgetPasswordForm
     * @return
     */
    @ApiOperation(value = "忘记密码")
    @PostMapping("/forgetPassword")
    public ResultWrapper forgetPassword(@RequestBody ForgetPasswordForm forgetPasswordForm){
        try {
            return ResultWrapper.successWithData(userService.forgetPassword(forgetPasswordForm));
        }catch (Exception e){
            log.info("UserController.forgetPassword");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 修改个人中心
     * @param updateInfoForm
     * @return
     */
    @ApiOperation(value = "修改个人中心")
    @PostMapping("/updateInfo")
    public ResultWrapper updateInfo(@RequestBody UpdateInfoForm updateInfoForm){
        return ResultWrapper.successWithData(userService.updateInfo(updateInfoForm,
                getCurrentUserInfo().getUserId()));
    }

    /**
     * 通过旧密码修改新密码
     * @param updatePassByOldForm
     * @return
     */
    @ApiOperation(value = "通过旧密码修改密码")
    @PostMapping("/updatePasswordByOld")
    public ResultWrapper updatePasswordByOld(@RequestBody UpdatePassByOldForm updatePassByOldForm){
        try {
            return ResultWrapper.successWithData(userService.updatePasswordByOld(updatePassByOldForm,getCurrentUserInfo().getUserId()));
        }catch (SysException e){
            log.info("UserController.updatePasswordByOld");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 通过验证码修改密码
     * @param updatePassByCodeForm
     * @return
     */
    @ApiOperation(value = "通过验证码修改密码")
    @PostMapping("/updatePasswordByCode")
    public ResultWrapper updatePasswordByCode(@RequestBody UpdatePassByCodeForm updatePassByCodeForm){
        try {
            String userId="fa7370b1feee4528ab4a84d7b1c11c4b";
            redisRepository.saveMessageCode("15698614402","123456");
            return ResultWrapper.successWithData(userService.
                    updatePasswordByCode(updatePassByCodeForm,
                            getCurrentUserInfo().getUserId()));
        }catch (SysException e){
            log.info("UserController.updatePasswordByCode");
            return ResultWrapper.failure(e.getMessage());
        }
    }

    /**
     * 获取过去发布的软件
     * @param page
     * @return
     */
    @ApiOperation(value = "获取历史发布的软件")
    @GetMapping("/getHistorySoft/{page}")
    public ResultWrapper getHistorySoft(@PathVariable("page") Integer page){
        return ResultWrapper.successWithData(userService.getHistoryPublished(/*getCurrentUserInfo().getUserId()*/"xxx",page));
    }
}
