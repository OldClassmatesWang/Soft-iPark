package com.yunlong.softpark.service;

import com.yunlong.softpark.dto.MessageSuccessDto;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @Author 王海澎
 * @Date 2020/7/30 9:25
 * @Version 1.0
 */
@Component("codeService")
public interface CodeService {

    /**
     * 返回给前端一张图形验证码
     * @return
     */
    BufferedImage getImageCode(HttpServletRequest request, HttpServletResponse response);

    /**
     * 同一session判断图形验证码匹配正确
     * @param imageCode
     * @param request
     */
    MessageSuccessDto judgeImageCode(String imageCode, HttpServletRequest request);

    /**
     * 获取手机号验证码
     * @return
     */
    MessageSuccessDto getPhoneCode(String phoneNumber, HttpServletRequest request);

    /**
     * 判断前端传递的手机号是否匹配
     * @param code
     * @param request
     * @return
     */
    MessageSuccessDto judgePhoneCode(String code, HttpServletRequest request);
}
