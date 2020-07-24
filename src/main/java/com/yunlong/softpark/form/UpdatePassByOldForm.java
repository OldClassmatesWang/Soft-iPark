package com.yunlong.softpark.form;

import lombok.Data;

/**
 * @Author: Cui
 * @Date: 2020/7/23
 * @Description:
 */
@Data
public class UpdatePassByOldForm {

    private String oldPassword;

    private String newPassword;
}
