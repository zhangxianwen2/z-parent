package com.seven.dynamic.datasource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.dynamic.datasource.dal.seven1.dataobject.User;
import com.seven.dynamic.datasource.dal.seven1.mapper.UserMapper;
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
@Service("userService")
public class UserService implements DataTestService<User> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User selectOne() {
        return userMapper.selectById("1");
    }

    @Override
    public List<User> selectBatch() {
        return userMapper.selectList(new QueryWrapper<>());
    }
}
