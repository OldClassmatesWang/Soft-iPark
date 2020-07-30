package com.yunlong.softpark.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
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
     * 解决异常信息：
     *  java.lang.IllegalArgumentException:
     *      Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("relaxedQueryChars", "|{}[]");
            }
        });
        return factory;
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
