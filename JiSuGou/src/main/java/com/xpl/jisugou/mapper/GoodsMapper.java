package com.xpl.jisugou.mapper;

import com.xpl.jisugou.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<Goods> selectAll();          // 对应 XML 中的 id="selectAll"
    int updateStock(Long goodsId, Integer num); // 对应 id="updateStock"
    Goods selectById(Long id);        // 根据 ID 查询商品
}