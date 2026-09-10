package com.okeychou.aishop.service;

import com.okeychou.aishop.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> listByPriceRange(BigDecimal min, BigDecimal max);
}
