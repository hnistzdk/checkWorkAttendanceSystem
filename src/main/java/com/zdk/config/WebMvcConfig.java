package com.zdk.config;

import com.zdk.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zdk
 * @date 2021/12/25 13:50
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedOrigins("http://localhost:8080")
                .allowCredentials(true)
                .allowedMethods(CorsConfiguration.ALL)
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

    private CorsConfiguration addcorsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        corsConfiguration.setAllowedOrigins(list);
    /*
    // 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
    */
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
//        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", addcorsConfig());
        return new CorsFilter(source);
    }

}
