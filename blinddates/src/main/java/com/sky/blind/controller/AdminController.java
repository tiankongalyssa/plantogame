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
public class AdminController extends BaseController {
    private final AdminService adminService;

    @Autowired
    public AdminController(JwtUtil jwtUtil, AdminService adminService) {
        super(jwtUtil);
        this.adminService = adminService;
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


    @PostMapping("/logout")
    public Result logout() {
        return new Result(true, StatusCode.OK, "退出成功");
    }

    @GetMapping("/list")
    public Result findByAdminId(String token) {
        Claims claims = checkToken(token);
        return new Result(true, StatusCode.OK, "查询成功", adminService.findByAdminId(Integer.valueOf(claims.get("adminId").toString())));
    }

    @GetMapping("/adminInfo")
    public Result findByAdminByUsername(String token) {
        Claims claims = checkToken(token);
        String username = claims.get("username").toString();
        Admin admin = adminService.findByUsername(username);
        admin.setPassword(null);
        return new Result(true, StatusCode.OK, "查询成功", admin);
    }

    @PutMapping
    public Result update(@RequestBody Map map) {
        adminService.update(map);
        return new Result(true, StatusCode.OK, "修改成功");
    }
    @PostMapping
    public Result addAdmin(@RequestBody Admin admin){
        adminService.save(admin);
        return new Result(true,StatusCode.OK,"添加成功");
    }
}
