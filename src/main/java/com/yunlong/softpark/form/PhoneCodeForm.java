package com.yunlong.softpark.form;

import lombok.Data;

/**
 * @Author 王海澎
 * @Date 2020/7/30 10:44
 * @Version 1.0
 */
@Data
public class PhoneCodeForm {
    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 验证码
     */
    private String code;
}
