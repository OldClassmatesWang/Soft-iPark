package com.yunlong.softpark.util;

import java.util.Random;
import java.util.UUID;

/**
 * @Author: Cui
 * @Date: 2020/7/29
 * @Description:
 */
public class FtpFileUtils {
    /**
     * 生成随机图片名
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }

    public static void main(String[] args) {
        for(int i=0;i<20;i++){
            System.out.println(i+":"+UUID.randomUUID().toString().replace("-", ""));
            //1.数据库添加数据
            //6.移植龙芯电脑
            //7.根据数据库生成后台管理系统的代码
            //9.添加JSR303验证
            //10.添加token过期和验证码过期时间
        }
    }
}
