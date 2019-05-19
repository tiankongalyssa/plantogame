package com.sky.blind.service;

import com.sky.blind.dao.UserMapper;
import com.sky.blind.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        for (User user : all) {
            user.setPassword(null);
        }
        return all;
    }

    public User login(String username, String password) {
        User user = getByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        redisTemplate.opsForValue().set("username" + username, username, 30, TimeUnit.MINUTES);
        user.setPassword(null);
        return user;
    }

    private User getByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

    public void update(User user) {
        String username;
        try {
            username = redisTemplate.opsForValue().get("username" + user.getUsername()).toString();
        } catch (Exception e) {
            throw new RuntimeException("登录过期,请重新登录");
        }
        user.setModifiedTime(new Date());
        user.setModifiedUser(username);
        updateUser(user);
    }

    private void updateUser(User user) {
        userMapper.update(user);
    }

    public void updatePassword(String username, String password, String newPassword) {
        User user = getByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("原密码错误");
        }
        user.setPassword(newPassword);
        updateUser(user);
    }

    public User findByUsername(String username) {
        User user = getByUsername(username);
        user.setPassword(null);
        return user;
    }

    public void logout(String username) {
        redisTemplate.opsForValue().set("username" + username, username, 1, TimeUnit.SECONDS);
    }
}
