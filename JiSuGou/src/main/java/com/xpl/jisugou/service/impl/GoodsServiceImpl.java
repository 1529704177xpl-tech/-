package com.xpl.jisugou.service.impl;

import com.xpl.jisugou.entity.Goods;
import com.xpl.jisugou.mapper.GoodsMapper;
import com.xpl.jisugou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.selectAll();
    }

    @Override
    public Goods getGoodsById(Long id) {
        return goodsMapper.selectById(id);
    }

    /**
     * 扣减库存（带事务）
     * @return true: 成功；false: 库存不足或失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reduceStock(Long goodsId, Integer num) {
        // 调用 XML 中定义的 updateStock
        int updated = goodsMapper.updateStock(goodsId, num);
        return updated > 0; // 影响行数 > 0 表示成功
    }
}