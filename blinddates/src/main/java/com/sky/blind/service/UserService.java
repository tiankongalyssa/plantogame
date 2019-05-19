package com.sky.blind.service;

import com.sky.blind.dao.UserMapper;
import com.sky.blind.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final RedisTemplate redisTemplate;

    @Autowired
    public UserService(UserMapper userMapper, RedisTemplate redisTemplate) {
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }

    public List<User> findAll() {
        List<User> all = userMapper.findAll();
        Collections.shuffle(all);
        return all;
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public void update(User user) {
        userMapper.update(user);
    }
}
