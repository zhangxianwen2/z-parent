package com.seven.dynamic.datasource.dal.seven2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.dynamic.datasource.dal.seven2.dataobject.Product;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author seven
 * @since 2020-01-09
 */
public interface ProductMapper extends BaseMapper<Product> {
    List<Product> selectPro();
}
