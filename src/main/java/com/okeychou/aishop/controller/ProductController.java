package com.okeychou.aishop.controller;

import com.okeychou.aishop.common.result.Result;
import com.okeychou.aishop.entity.Product;
import com.okeychou.aishop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // 日志对象：专属于本 Controller 的"案发现场记录员"
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result<List<Product>> listByPrice(
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max
    ) {
        double lo = min == null ? 0.0 : min;
        double hi = max == null ? Double.MAX_VALUE : max;

        log.info("访问商品列表: min={}, max={}", lo, hi);  // 入口日志：记输入

        List<Product> products = productService.listByPriceRange(lo, hi);

        log.info("返回商品数量: {}", products.size());  // 出口日志：记输出

        return Result.success(products);
    }
}
