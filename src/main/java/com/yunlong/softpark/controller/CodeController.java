package com.yunlong.softpark.controller;

import com.yunlong.softpark.dto.MessageSuccessDto;
import com.yunlong.softpark.form.ImageCodeForm;
import com.yunlong.softpark.form.PhoneCodeForm;
import com.yunlong.softpark.service.CodeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author 王海澎
 * @Date 2020/7/30 9:21
 * @Version 1.0
 */

/**
 * 实现验证码相关功能接口
 * 1）图形验证码
 * 2）手机号验证码
 */
@Api(value = "ColumnController", tags = {"栏目API"})
@Controller
@Slf4j
@RequestMapping("/code")
public class CodeController {

    @Autowired
    CodeService codeService;

    /**
     * 获取图形验证码图片返回给前端
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(path = "/image", produces = MediaType.IMAGE_PNG_VALUE,method = RequestMethod.GET)
    public void getImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = codeService.getImageCode(request, response);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"png",out);
    }

    /**
     * 比对前端传递的图形验证码信息
     * @param imageCodeForm
     * @param request
     * @return
     */
    @RequestMapping(path = "/judgeImage",method = RequestMethod.POST)
    public MessageSuccessDto judgeImageCode(@RequestBody ImageCodeForm imageCodeForm,HttpServletRequest request){
        MessageSuccessDto messageSuccessDto=codeService.judgeImageCode(imageCodeForm.getImageCode(),request);
        return messageSuccessDto;
    }

    /**
     * 发送手机号验证码
     * @param phoneCodeForm
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/phone",method = RequestMethod.GET)
    public MessageSuccessDto getPhoneCode(@RequestBody PhoneCodeForm phoneCodeForm,HttpServletRequest request){
        MessageSuccessDto messageSuccessDto =codeService.getPhoneCode(phoneCodeForm.getPhoneNumber(),request);
        return messageSuccessDto;
    }

    @RequestMapping(path = "/judgePhone",method = RequestMethod.POST)
    public MessageSuccessDto juedgePhoneCode(@RequestBody PhoneCodeForm phoneCodeForm,HttpServletRequest request){
        MessageSuccessDto messageSuccessDto = codeService.judgePhoneCode(phoneCodeForm.getCode(),request);
        return messageSuccessDto;
    }


}
