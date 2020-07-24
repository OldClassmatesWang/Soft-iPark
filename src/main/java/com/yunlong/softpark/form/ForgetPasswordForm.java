package com.yunlong.softpark.form;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/7/23
 * @Description:
 */
@Data
public class ForgetPasswordForm {
    private String phone;

    private String password;

    private String code;

}
