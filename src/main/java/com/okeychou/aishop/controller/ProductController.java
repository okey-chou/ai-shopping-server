package com.okeychou.aishop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.okeychou.aishop.common.result.Result;
import com.okeychou.aishop.common.result.ResultCode;
import com.okeychou.aishop.entity.Product;
import com.okeychou.aishop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // 日志对象：专属于本 Controller 的"案发现场记录员"
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);


    private static final BigDecimal PRICE_MAX=new BigDecimal("99999999.99");
    private static final int MAX_PAGE_SIZE=100;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result<List<Product>> listByPrice(
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max) {
        BigDecimal lo = min == null ? BigDecimal.ZERO : min;
        BigDecimal hi = max == null ? PRICE_MAX : max;
        log.info("访问商品列表: min={}, max={}", lo, hi);
        return Result.success(productService.listByPriceRange(lo, hi));
    }

    // 按分类
    @GetMapping("/category")
    public Result<List<Product>> listByCategory(@RequestParam String category) {
        if(!StringUtils.hasText(category)){
            log.warn("分类参数为空, 直接拒绝");
            return Result.fail(ResultCode.PARAM_ERROR);
        }
        log.info("按分类查询: category={}", category);
        return Result.success(productService.listByCategory(category.trim()));
    }

    //关键字模糊搜索
    @GetMapping("/search")
    public Result<List<Product>> search(@RequestParam String keyword) {
        if (!StringUtils.hasText(keyword)) {
            log.warn("搜索参数为空, 直接拒绝");
            return Result.fail(ResultCode.PARAM_ERROR);
        }
        log.info("按关键字查询: keyword={}", keyword);
        return Result.success(productService.listByNameLike(keyword.trim()));
    }


    //商品详情（查不到是 404，不是 500）
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        log.info("查询商品详情: id={}", id);
        Product product = productService.getById(id);
        if (product == null) {
            return Result.fail(ResultCode.NOT_FOUND);
        }
        return Result.success(product);
    }


    //分页（默认值 + 双层钳制）
    @GetMapping("/page")
    public Result<IPage<Product>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max) {

        int safeNum = Math.max(pageNum, 1);
        int safeSize = Math.min(Math.max(pageSize, 1), MAX_PAGE_SIZE);
        BigDecimal lo = min == null ? BigDecimal.ZERO : min;
        BigDecimal hi = max == null ? PRICE_MAX : max;

        log.info("分页查询: pageNum={}, pageSize={}, min={}, max={}", safeNum, safeSize, lo, hi);
        return Result.success(productService.pageByPriceRange(safeNum, safeSize, lo, hi));
    }
}
