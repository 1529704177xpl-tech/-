package com.xpl.jisugou.service;

public interface SeckillService {
    /**
     * 执行秒杀
     * @param goodsId 商品ID
     * @param userId 用户ID（模拟）
     * @return true: 秒杀成功；false: 失败（库存不足等）
     */
    boolean seckill(Long goodsId, Long userId);
}