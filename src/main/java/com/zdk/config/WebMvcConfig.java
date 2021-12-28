package com.zdk.config;

import com.zdk.interceptor.LoginInterceptor;
import com.zdk.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zdk
 * @date 2021/12/25 13:50
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
//                .allowedOrigins("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedHeaders("Access-Control-Allow-Headers", "X-Requested-With")
                .allowedHeaders("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS")
                .allowedHeaders("Content-Type", "application/json;charset=utf-8")
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600);
        //一小时内不用再预先检测(发送OPTIONS请求)
    }

    @Autowired
    private PermissionInterceptor permissionInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor)
                .excludePathPatterns("/user/login","/user/register")
                .excludePathPatterns("/menus","/verificationCode")
                .excludePathPatterns("/swagger*/**", "/v2/**", "/webjars/**");
    }
}
