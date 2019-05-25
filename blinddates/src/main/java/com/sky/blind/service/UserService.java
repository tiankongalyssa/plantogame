package com.sky.blind.service;

import com.github.pagehelper.PageHelper;
import com.sky.blind.dao.AdminMapper;
import com.sky.blind.dao.UserMapper;
import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import com.sky.blind.utils.OnLineRunner;
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
        return userMapper.findAll();
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
        userMapper.insert(user);
    }

    public void deleteRecordById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 分页查询
     *
     * @return List<User>
     */
    public List<User> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<User> users = userMapper.selectAll();
        Collections.shuffle(users);
        return users;
    }

    //虚拟在线人数
    public Integer findOnline() {
        return OnLineRunner.onLineNumber;
    }
}
