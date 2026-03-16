package com.xpl.jisugou.service;

import com.xpl.jisugou.entity.Goods;
import java.util.List;

public interface GoodsService {
    List<Goods> getAllGoods();
    Goods getGoodsById(Long id);
    boolean reduceStock(Long goodsId, Integer num);
}