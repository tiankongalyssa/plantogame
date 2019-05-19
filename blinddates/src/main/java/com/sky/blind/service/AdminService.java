package com.sky.blind.service;

import com.sky.blind.dao.AdminMapper;
import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminService {
    private final AdminMapper adminMapper;

    private final RedisTemplate<String, String> redisTemplate;
    @Autowired
    public AdminService(AdminMapper adminMapper, RedisTemplate<String, String> redisTemplate) {
        this.adminMapper = adminMapper;
        this.redisTemplate = redisTemplate;
    }

    public void login(String username, String password) {
        Admin admin = adminMapper.findByusername(username);
        if(admin==null){
            throw new RuntimeException("用户不存在");
        }
        if(!admin.getPassword().equals(password)){
            throw new RuntimeException("用户名或密码错误");
        }
        redisTemplate.opsForValue().set("admin"+username,username,30, TimeUnit.MINUTES);
    }

    public void logout(String username) {
        redisTemplate.opsForValue().set("admin"+username,username,1, TimeUnit.SECONDS);
    }

    public List<User> findByAdminId(Integer admindId) {
      return adminMapper.findByAdminId(admindId) ;
    }
}
