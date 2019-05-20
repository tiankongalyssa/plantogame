package com.sky.blind.service;

import com.sky.blind.dao.AdminMapper;
import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminMapper adminMapper;


    @Autowired
    public AdminService(AdminMapper adminMapper ) {
        this.adminMapper = adminMapper;
    }

    public Admin login(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        return admin;
    }

    public void logout(String username) {
    }

    public List<User> findByAdminId(Integer admindId) {
        return adminMapper.findByAdminId(admindId);
    }

    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }
}
