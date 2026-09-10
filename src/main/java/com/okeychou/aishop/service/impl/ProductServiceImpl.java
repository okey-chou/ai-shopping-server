package com.okeychou.aishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    @Override
    public List<Product> listByCategory(String category) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getCategory, category)
               .orderByAsc(Product::getPrice);
        return productMapper.selectList(wrapper);
    }
    @Override
    public List<Product> listByNameLike(String keyword) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Product::getName, keyword)
                .orderByAsc(Product::getId);
        return productMapper.selectList(wrapper);
    }

    @Override
    public Product getById(Long id) {
        return productMapper.selectById(id);
    }
    @Override
    public IPage<Product> pageByPriceRange(int pageNum, int pageSize, BigDecimal min, BigDecimal max) {
        Page<Product> page=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Product::getPrice, min, max)
                .orderByAsc(Product::getPrice);
        return productMapper.selectPage(page, wrapper);
    }
}
