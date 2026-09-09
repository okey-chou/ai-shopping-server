package com.okeychou.aishop.service.impl;

import com.okeychou.aishop.entity.Product;
import com.okeychou.aishop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> listByPriceRange(double min, double max) {
        List<Product> all = List.of(
                new Product(1L, "小米14", "手机", 4299.0),
                new Product(2L, "华为Mate60", "手机", 6499.0),
                new Product(3L, "苹果AirPods Pro", "耳机", 1899.0),
                new Product(4L, "联想拯救者", "笔记本", 7999.0),
                new Product(5L, "罗技鼠标", "外设", 199.0)
        );

        // 过滤:留下价格在 [min, max] 区间内的
        return all.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .toList();
    }
}
