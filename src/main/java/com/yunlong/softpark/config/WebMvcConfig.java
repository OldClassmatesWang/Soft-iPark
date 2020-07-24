package com.yunlong.softpark.config;

import com.yunlong.softpark.core.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author 段启岩
 */

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/error",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html",
                        "/user/register",
                        "/user/login/**",
                        "/user/**",
                        "/file/image",
                        "/message/**",
                        "/rank/showRankingByArea",
                        "/rank/showRankingByGrade",
                        "/rank/showRankingBySchool",
                        "/practice/quick",
                        "/practice/ask",
                        "/login/**",
                        "/register",
                        "/integral/goods",
                        "/integral/title",
                        "/integral/goods/info",
                        "/oss/policy"
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }
}
