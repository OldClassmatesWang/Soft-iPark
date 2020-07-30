package com.yunlong.softpark.service.impl;

import com.yunlong.softpark.dto.MessageSuccessDto;
import com.yunlong.softpark.service.CodeService;
import com.yunlong.softpark.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author 王海澎
 * @Date 2020/7/30 9:32
 * @Version 1.0
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    ImageCodeUtil codeUtil;

    /**
     * 返回给前端一张图形验证码
     *
     * @return
     */
    @Override
    public BufferedImage getImageCode(HttpServletRequest request, HttpServletResponse response) {

        BufferedImage image =  codeUtil.getCodeImage(request,response);
        return image;
    }

    /**
     * 同一session判断图形验证码匹配正确
     *
     * @param imageCode
     * @param request
     */
    @Override
    public MessageSuccessDto judgeImageCode(String imageCode, HttpServletRequest request) {
        String sessionAttribute = (String) request.getSession().getAttribute("code");
        System.out.println("SessionAttribute:"+sessionAttribute);
        MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
        if(sessionAttribute.equals(imageCode)){
            messageSuccessDto.setMessage("success");
            return messageSuccessDto;
        }else {
            messageSuccessDto.setMessage("failure");
            return messageSuccessDto;
        }
    }

    /**
     * 获取手机号验证码
     *
     * @return
     */
    @Override
    public MessageSuccessDto getPhoneCode(String phoneNumber,HttpServletRequest request) {

        String postUrl = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

        int mobile_code = (int) ((Math.random() * 9 + 1) * 100000); //获取随机数

        request.getSession().setAttribute("valiCode", mobile_code);

        String mobile = phoneNumber;
        String account = "C39319016"; //查看用户名是登录用户中心->验证码短信->产品总览->APIID
        String password = "cd8500cc549fd016ae72a7e6377fafa4";  //查看密码请登录用户中心->验证码短信->产品总览->APIKEY
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");//发送给手机的信息

        try {
            System.out.println("成功执行");

            URL url = new URL(postUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);//允许连接提交信息
            connection.setRequestMethod("POST");//网页提交方式“GET”、“POST”
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "Keep-Alive");
            StringBuffer sb = new StringBuffer();
            sb.append("account=" + account);
            sb.append("&password=" + password);
            sb.append("&mobile=" + mobile);
            sb.append("&content=" + content);
            OutputStream os = connection.getOutputStream();
            os.write(sb.toString().getBytes());
            os.close();

            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
        messageSuccessDto.setMessage("success");
        return messageSuccessDto;
    }

    /**
     * 判断前端传递的手机号是否匹配
     *
     * @param code
     * @param request
     * @return
     */
    @Override
    public MessageSuccessDto judgePhoneCode(String code, HttpServletRequest request) {
        String inCode = code;
        //Valicode是随机出验证码
        String valiCode = request.getSession().getAttribute("valiCode").toString().toLowerCase();
        //2.判断验证是否正确
        if (inCode.equals(valiCode)) {
            /**
             *
             *
             * 缺少用户是否存在的逻辑判断
             * 以及向表中添加用户的逻辑
             */



            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("验证码正确，注册成功！");
            return messageSuccessDto;
        } else {
            MessageSuccessDto messageSuccessDto = new MessageSuccessDto();
            messageSuccessDto.setMessage("验证码错误，注册失败！");
            return messageSuccessDto;
        }

    }
}
