package com.zdk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
//                .allowedOrigins("http://localhost:8080")
                .allowedOrigins(CorsConfiguration.ALL)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedHeaders("Access-Control-Allow-Headers", "X-Requested-With")
                .allowedHeaders("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS")
                .allowedHeaders("Content-Type", "application/json;charset=utf-8")
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600);
        //一小时内不用再预先检测(发送OPTIONS请求)
    }

//    @Autowired
//    private RightInterceptor rightInterceptor;
//
//    @Autowired
//    private LoginInterceptor loginInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(rightInterceptor)
//                .excludePathPatterns("/adminLogin","/primaryLogin","/enterpriseLogin","/sendCode/*","/menus")
//                .excludePathPatterns("/enterpriseRegister","/enterprisePwdChange")
//                .excludePathPatterns("/primaryRegister","/primaryPwdChange","/userInfo/**")
//                .excludePathPatterns("/swagger*/**", "/v2/**", "/webjars/**");
//
////        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
////                .excludePathPatterns("/adminLogin","/primaryLogin","/enterpriseLogin","/sendCode/*","/menus")
////                .excludePathPatterns("/swagger*/**", "/v2/**", "/webjars/**");
//    }
}
