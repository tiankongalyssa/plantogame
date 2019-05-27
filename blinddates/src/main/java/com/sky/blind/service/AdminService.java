package com.sky.blind.service;

import com.github.pagehelper.PageHelper;
import com.sky.blind.dao.AdminMapper;
import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import com.sky.blind.service.exception.PasswordNotMathchException;
import com.sky.blind.service.exception.PermissionDeniedException;
import com.sky.blind.service.exception.UserNotFoundException;
import com.sky.blind.service.exception.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    private final AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public Admin login(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        System.out.println(admin);
        if (admin == null) {
            throw new UserNotFoundException("用户不存在");
        }
        if (admin.getIsAdmin() == 0) {
            throw new PermissionDeniedException("权限不足");
        }
        if (!admin.getPassword().equals(password)) {
            throw new PasswordNotMathchException("用户名或密码错误");
        }
        return admin;
    }


    public List<User> findByAdminId(Integer admindId) {
        return adminMapper.findByAdminId(admindId);
    }

    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    public void update(Map map) {
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        String newPassword = null;
        try {
            newPassword = map.get("newPassword").toString();
        } catch (Exception e) {

        }
        String weixin = map.get("weixin").toString();
        Admin data = findByUsername(username);
        if (data == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        if (!data.getPassword().equals(password)) {
            throw new PasswordNotMathchException("原密码错误");
        }
        if (newPassword != null) {
            data.setPassword(newPassword);
        }
        data.setWeixin(weixin);
        adminMapper.updateByPrimaryKey(data);
    }

    public void save(Admin admin) {
        Admin data = adminMapper.findByUsername(admin.getUsername());
        if (data != null) {
            throw new UsernameAlreadyExistsException("用户名已存在");
        }
        admin.setCreatedUser("admin");
        Date time = new Date();
        admin.setCreatedTime(time);
        admin.setModifiedUser("admin");
        admin.setModifiedTime(time);
        adminMapper.insert(admin);
    }

    public List<User> searchByAdminId(Integer adminId, int page, int size) {
        PageHelper.startPage(page, size);
        return adminMapper.findByAdminId(adminId);
    }
}
