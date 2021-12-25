package com.zdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zdk
 */
@EnableTransactionManagement
@SpringBootApplication
public class CheckWorkAttendanceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckWorkAttendanceSystemApplication.class, args);
    }

}
