package com.seven.dynamic.datasource.service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @Author zhangxianwen
 * 2020/01/09 16:34
 **/
public interface DataTestService<T> {

    void insert(T t);

    void update(T t);

    T selectOne();

    List<T> selectBatch();
}
