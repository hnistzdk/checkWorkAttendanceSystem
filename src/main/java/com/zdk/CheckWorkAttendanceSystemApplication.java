package com.zdk;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zdk
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
public class CheckWorkAttendanceSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckWorkAttendanceSystemApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // IP白名单 (没有配置或者为空，则允许所有访问)
        registrationBean.addInitParameter("allow", "");
        // IP黑名单 (存在共同时，deny优先于allow)
        registrationBean.addInitParameter("deny", "");

        registrationBean.addInitParameter("loginUsername", "root");

        registrationBean.addInitParameter("loginPassword", "root");

        registrationBean.addInitParameter("resetEnable", "false");

        return registrationBean;

    }
}
