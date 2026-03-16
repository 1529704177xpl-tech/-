package com.xpl.jisugou.controller;

import com.xpl.jisugou.entity.User;
import com.xpl.jisugou.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // 跳转登录页
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "用户名或密码错误");
        }
        return "login";
    }

    // 处理登录
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功，保存到 Session
            session.setAttribute("currentUser", user);
            return "redirect:/"; // 跳回首页
        } else {
            return "redirect:/login?error=1";
        }
    }

    // 退出登录
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}