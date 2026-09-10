package com.okeychou.aishop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.okeychou.aishop.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> listByPriceRange(BigDecimal min, BigDecimal max);

    // ① 按分类精确筛选
    List<Product> listByCategory(String category);
    // ② 名字模糊匹配
    List<Product> listByNameLike(String keyword);
    // ③ 按 ID 查单条商品
    Product getById(Long id);
    // ④ 分页查询
    IPage<Product> pageByPriceRange(int pageNum, int pageSize, BigDecimal min, BigDecimal max);
}
