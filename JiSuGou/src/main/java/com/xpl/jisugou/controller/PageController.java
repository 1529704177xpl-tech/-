package com.xpl.jisugou.controller;

import com.xpl.jisugou.entity.Goods;
import com.xpl.jisugou.service.GoodsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // 检查登录状态
        Object currentUser = session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // 获取商品列表（明确泛型，避免推断问题）
        List<Goods> goodsList = goodsService.getAllGoods();
        model.addAttribute("goodsList", goodsList);
        return "index";
    }
}