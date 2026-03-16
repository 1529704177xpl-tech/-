package com.xpl.jisugou.service.impl;

import com.xpl.jisugou.entity.Goods;
import com.xpl.jisugou.mapper.GoodsMapper;
import com.xpl.jisugou.service.GoodsService;
import com.xpl.jisugou.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;

    // Lua 脚本：原子判断并扣减库存
    private static final String LUA_SCRIPT =
            "local stock = tonumber(redis.call('GET', KEYS[1]))\n" +
                    "if stock and stock >= tonumber(ARGV[1]) then\n" +
                    "    redis.call('DECRBY', KEYS[1], ARGV[1])\n" +
                    "    return 1\n" +
                    "end\n" +
                    "return 0";

    private final DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LUA_SCRIPT, Long.class);

    /**
     * 初始化商品库存到 Redis（项目启动时调用）
     */
    public void preloadStockToRedis(Long goodsId) {
        Goods goods = goodsService.getGoodsById(goodsId);
        if (goods != null && goods.getStock() > 0) {
            String key = "seckill:stock:" + goodsId;
            redisTemplate.opsForValue().set(key, String.valueOf(goods.getStock()));
            System.out.println("✅ 商品 " + goodsId + " 库存 " + goods.getStock() + " 已加载到 Redis");
        }
    }

    @Override
    public boolean seckill(Long goodsId, Long userId) {
        String stockKey = "seckill:stock:" + goodsId;

        // 1. 执行 Lua 脚本尝试扣减 Redis 库存
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(stockKey), "1");

        if (result != null && result == 1) {
            // 2. 扣减成功 → 异步创建订单（模拟）
            CompletableFuture.runAsync(() -> createOrder(goodsId, userId));
            return true;
        }

        return false; // 库存不足或已抢光
    }

    /**
     * 模拟异步创建订单（实际项目中应写入 MQ 或数据库）
     */
    private void createOrder(Long goodsId, Long userId) {
        try {
            // 这里可以调用 OrderService 创建订单
            System.out.println("📦 异步创建订单: 用户 " + userId + " 秒杀商品 " + goodsId);

            // TODO: 实际开发中，这里应：
            // 1. 检查是否重复下单（幂等性）
            // 2. 插入订单表
            // 3. 记录秒杀日志

        } catch (Exception e) {
            // 异常处理：记录失败，可补偿
            System.err.println("❌ 创建订单失败: " + e.getMessage());
        }
    }
}