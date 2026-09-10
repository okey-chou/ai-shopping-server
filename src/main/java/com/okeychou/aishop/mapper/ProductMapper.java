package com.okeychou.aishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.okeychou.aishop.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
