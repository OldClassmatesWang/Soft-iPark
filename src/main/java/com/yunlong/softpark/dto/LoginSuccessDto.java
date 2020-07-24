package com.yunlong.softpark.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cui
 * @Date: 2020/7/23
 * @Description:
 */
@Data
public class LoginSuccessDto {

    private String accessToken;

    private String username;

    private String phone;

    private String email;

    private String createTime;
}
