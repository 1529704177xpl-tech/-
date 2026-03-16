package com.xpl.jisugou;

import com.xpl.jisugou.service.impl.SeckillServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.xpl.jisugou.mapper")
public class JiSuGouApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JiSuGouApplication.class, args);

        // 启动后预加载商品库存到 Redis
        SeckillServiceImpl seckillService = context.getBean(SeckillServiceImpl.class);
        seckillService.preloadStockToRedis(1L); // 假设商品 ID=1 是秒杀商品
        seckillService.preloadStockToRedis(2L); // 可加多个
    }
}