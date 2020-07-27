package com.yunlong.softpark.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

/**
 * @Author: Cui
 * @Date: 2020/5/10
 * @Description:
 */
@Configuration
public class CorsConfigration {

//    @Autowired
//    HikariDataSource hikariDataSource;

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //配置跨域
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }

    /**
     * hikariDataSource使用后关闭，防止超时
     * @throws SQLException
     */
//    @Bean
//    public void getHikariDataSource() throws SQLException {
//        hikariDataSource.setIdleTimeout(60000);
//        hikariDataSource.setConnectionTimeout(60000);
//        hikariDataSource.setValidationTimeout(3000);
//        hikariDataSource.setLoginTimeout(5);
//        hikariDataSource.setMaxLifetime(60000);
//    }

}
