package com.xpl.jisugou.controller;

import com.xpl.jisugou.entity.Goods;
import com.xpl.jisugou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public Map<String, Object> listGoods() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Goods> goodsList = goodsService.getAllGoods();
            result.put("data", goodsList);
            result.put("code", 200);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("data", null);
            result.put("code", 500);
            result.put("msg", "查询商品列表失败: " + e.getMessage());
        }
        return result;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getGoods(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Goods goods = goodsService.getGoodsById(id);
            if (goods == null) {
                result.put("code", 404);
                result.put("msg", "商品不存在");
                result.put("data", null);
            } else {
                result.put("data", goods);
                result.put("code", 200);
                result.put("msg", "success");
            }
        } catch (Exception e) {
            result.put("data", null);
            result.put("code", 500);
            result.put("msg", "查询商品失败: " + e.getMessage());
        }
        return result;
    }

    // 新增：测试扣库存接口（后续秒杀会用）
    @PostMapping("/{id}/reduce")
    public Map<String, Object> reduceStock(@PathVariable Long id, @RequestParam Integer num) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = goodsService.reduceStock(id, num);
            result.put("code", success ? 200 : 400);
            result.put("msg", success ? "扣库存成功" : "库存不足或商品不存在");
            result.put("data", null);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "扣库存失败: " + e.getMessage());
            result.put("data", null);
        }
        return result;
    }
}