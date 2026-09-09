package com.okeychou.aishop.service;

import com.okeychou.aishop.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> listByPriceRange(double min, double max);
}
