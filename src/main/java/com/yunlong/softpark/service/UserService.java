package com.yunlong.softpark.service;

import com.yunlong.softpark.dto.HistorySoftDto;
import com.yunlong.softpark.dto.LoginSuccessDto;
import com.yunlong.softpark.dto.MessageSuccessDto;
import com.yunlong.softpark.dto.UserInfoDto;
import com.yunlong.softpark.form.*;
import org.springframework.stereotype.Component;

@Component("userService")
public interface UserService {


    MessageSuccessDto register(RegisterForm registerForm);

    LoginSuccessDto login(LoginForm loginForm);

    MessageSuccessDto forgetPassword(ForgetPasswordForm forgetPasswordForm);

    MessageSuccessDto updateInfo(UpdateInfoForm updateInfoForm,String userId);

    MessageSuccessDto updatePasswordByOld(UpdatePassByOldForm updatePassByOldForm,String userId);

    MessageSuccessDto updatePasswordByCode(UpdatePassByCodeForm updatePassByCodeForm,String userId);

    HistorySoftDto getHistoryPublished(String userId,Integer page);

    UserInfoDto getUserInfo(String userId);

    void logout(String userId);

    LoginSuccessDto login(LoginCodeForm loginCodeForm);
}
