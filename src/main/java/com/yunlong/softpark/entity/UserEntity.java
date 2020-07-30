package com.yunlong.softpark.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cui
 * @Date: 2020/7/23
 * @Description:
 */
@Data
public class UserEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱号
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
