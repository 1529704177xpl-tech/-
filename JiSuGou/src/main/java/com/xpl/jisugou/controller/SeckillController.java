package com.xpl.jisugou.controller;

import com.xpl.jisugou.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 模拟秒杀接口
     * @param goodsId 商品ID
     * @param userId 用户ID（前端传，这里简化为参数）
     */
    @PostMapping("/{goodsId}")
    public Map<String, Object> doSeckill(@PathVariable Long goodsId,
                                         @RequestParam(defaultValue = "1001") Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = seckillService.seckill(goodsId, userId);
            if (success) {
                result.put("code", 200);
                result.put("msg", "恭喜！秒杀成功！");
            } else {
                result.put("code", 400);
                result.put("msg", "手慢了，库存不足或已抢光");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "系统繁忙，请重试");
            e.printStackTrace();
        }
        return result;
    }
}