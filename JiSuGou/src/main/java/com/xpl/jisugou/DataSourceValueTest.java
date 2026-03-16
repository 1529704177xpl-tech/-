package com.xpl.jisugou;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourceValueTest implements CommandLineRunner {

    @Value("${spring.datasource.url:未找到}")
    private String url;

    @Value("${spring.datasource.username:未找到}")
    private String username;

    @Value("${spring.datasource.password:未找到}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========== Spring Boot 读取到的数据库配置 ==========");
        System.out.println("URL: " + url);
        System.out.println("用户名：" + username);
        System.out.println("密码：" + password);
        System.out.println("==================================================");
    }
}
