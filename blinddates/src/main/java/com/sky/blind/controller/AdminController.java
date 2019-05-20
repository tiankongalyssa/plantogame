package com.sky.blind.controller;

import com.sky.blind.pojo.Admin;
import com.sky.blind.service.AdminService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    private final AdminService adminService;

    private final JwtUtil jwtUtil;

    @Autowired
    public AdminController(AdminService adminService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map map) {
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        Admin admin = adminService.login(username, password);
        Map<String, String> map1 = new HashMap<>();
        map1.put("username", username);
        map1.put("adminId", admin.getId().toString());
        String token = jwtUtil.createJWT(admin.getId().toString(), map1, "admin");
        return new Result(true, StatusCode.OK, "登录成功", token);
    }

    @GetMapping("/info")
    public Result info(String token) {
        Claims claims = checkToken(token);
        String username = claims.get("username").toString();
        Admin admin = adminService.findByUsername(username);
        Map<String, String> map = new HashMap<>();
        map.put("name", admin.getUsername());
        map.put("avatar", "https://photo.zastatic.com/images/photo/380745/1522976180/21355758588930234.png");
        return new Result(true, StatusCode.OK, "查询成功", map);
    }

    private Claims checkToken(String token) {
        Claims claims;
        try {
            claims = jwtUtil.parseJWT(token);
        } catch (Exception e) {
            throw new RuntimeException("登录过期");
        }
        return claims;
    }

    @PostMapping("/logout")
    public Result logout() {
        return new Result(true, StatusCode.OK, "退出成功");
    }

    @GetMapping("/list")
    public Result findByAdminId(String token) {
        Claims claims = checkToken(token);
        return new Result(true, StatusCode.OK, "查询成功", adminService.findByAdminId(Integer.valueOf(claims.get("adminId").toString())));
    }
}
