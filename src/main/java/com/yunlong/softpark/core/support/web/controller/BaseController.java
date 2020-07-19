package com.yunlong.softpark.core.support.web.controller;

import com.yunlong.softpark.common.constant.SysConstant;
import com.yunlong.softpark.common.enums.BaseErrorCodeEnum;
import com.yunlong.softpark.common.util.ThreadLocalMap;
import com.yunlong.softpark.core.exception.SysException;
import org.springframework.validation.BindingResult;

/**
 * @author 通用Controller
 * @param <T> 当前登录用户的Java类型
 */
public class BaseController<T> {

    protected T getCurrentUserInfo() {
        T currentUser = (T) ThreadLocalMap.get(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER);

        if (null == currentUser) {
            throw new SysException(BaseErrorCodeEnum.NO_USER_INFO_FOUND);
        }

        return currentUser;
    }

    protected void validateParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SysException(BaseErrorCodeEnum.PARAM_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
