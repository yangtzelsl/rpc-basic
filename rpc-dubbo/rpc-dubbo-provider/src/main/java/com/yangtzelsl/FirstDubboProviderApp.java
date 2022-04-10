package com.yangtzelsl;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring-boot-starter-jdbc - 不需要提供EnableJDBC之类的注解。
 *
 * EnableXxx系列注解：
 *  大多数的SpringBoot启动器，想要生效，一般都需要开启。
 *  代表，让SpringBoot环境，扫描对应启动器的自定义注解，并初始化环境。
 *  如：dubbo-spring-boot-starter - EnableDubbo
 *  如：spring-boot-starter-session - EnableHttpRedisSession
 */
@SpringBootApplication
@EnableDubbo
public class FirstDubboProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(FirstDubboProviderApp.class, args);
    }
}
