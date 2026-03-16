package com.xpl.jisugou.service;

import com.xpl.jisugou.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    // 模拟用户数据库（内存）
    private static final Map<String, User> USER_DB = new HashMap<>();

    static {
        // 预设一个测试账号：admin / 123456
        User admin = new User();
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setPassword("123456");
        USER_DB.put("admin", admin);
    }

    public User findByUsername(String username) {
        return USER_DB.get(username);
    }
}