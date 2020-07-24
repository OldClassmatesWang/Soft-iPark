package com.yunlong.softpark.config;

import com.aliyun.oss.OSSClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Cui
 * @Date: 2020/7/22
 * @Description:
 */
@Configuration
public class OssConfig {

    @Bean
    public OSSClient getOSSClient() {
        //使用你的对应的endpoint地址
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G6gy9GpoKc4W7aQqA1y";
        String accessKeySecret = "xR4PbuRVhZb4CuRU5wdvvBC03DlGty";
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }
}
