package com.seven.dynamic.datasource.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.dynamic.datasource.dal.seven2.dataobject.Product;
import com.seven.dynamic.datasource.dal.seven2.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 16:31
 **/
@Service("productService")
public class ProductService implements DataTestService<Product> {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void insert(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product);
    }

    @Override
    public Product selectOne() {
        return productMapper.selectById("1");
    }

    @Override
    public List<Product> selectBatch() {
        return productMapper.selectList(new QueryWrapper<>());
    }
}
