package com.sky.blind.dao;

import com.sky.blind.pojo.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    User findByUsername(String username);
    void update(User user);
    List<User>findAll();
}
