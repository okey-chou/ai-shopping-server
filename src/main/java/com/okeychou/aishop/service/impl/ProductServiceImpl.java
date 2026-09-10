package com.okeychou.aishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.okeychou.aishop.entity.Product;
import com.okeychou.aishop.mapper.ProductMapper;
import com.okeychou.aishop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> listByPriceRange(BigDecimal min, BigDecimal max) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Product::getPrice, min, max)
                .orderByAsc(Product::getPrice);

        // 过滤:留下价格在 [min, max] 区间内的
        return productMapper.selectList(wrapper);
    }
}
