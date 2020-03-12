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
 * 2020/02/23 17:12
 **/
@Service
public class AServiceImpl implements AService<Product> {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public void insert(Product o) {

    }

    @Override
    public void update(Product o) {

    }

    @Override
    public Product selectOne() {
        return null;
    }

    @Override
    public List<Product> selectBatch() {
        return productMapper.selectList(new QueryWrapper<>());
        // return productMapper.selectPro();
    }
}
