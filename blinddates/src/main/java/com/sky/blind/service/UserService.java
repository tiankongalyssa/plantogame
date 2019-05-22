package com.sky.blind.service;

import com.sky.blind.dao.AdminMapper;
import com.sky.blind.dao.UserMapper;
import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;

    @Autowired
    public UserService(UserMapper userMapper, AdminMapper adminMapper) {
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
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

    public void addRecord(User user) {
        Admin admin = adminMapper.findAdminById(user.getAdminId());
        Date date = new Date();
        String username = admin.getUsername();
        user.setWeixin(admin.getWeixin());
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        user.setCreatedUser(username);
        user.setModifiedUser(username);
        System.out.println(user);
        userMapper.insert(user);
    }

    public void deleteRecordById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
