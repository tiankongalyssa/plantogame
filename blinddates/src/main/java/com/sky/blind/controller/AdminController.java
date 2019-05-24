package com.sky.blind.controller;

import com.github.pagehelper.Page;
import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import com.sky.blind.service.AdminService;
import entity.PageResult;
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

    /**
     * 管理员登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map map) {
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        Admin admin = adminService.login(username, password);
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("adminId", admin.getId().toString());
        String token = jwtUtil.createJWT(admin.getId().toString(), data, "admin");
        return new Result(true, StatusCode.OK, "登录成功", token);
    }

    /**
     * elementUI 后台登录回调获取用户信息
     */
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

    /**
     * 根据管理员用户名所有记录
     */
    @GetMapping("/list")
    public Result findByAdminId(String token) {
//        System.out.println(token);
        Claims claims = checkToken(token);
        return new Result(true, StatusCode.OK, "查询成功", adminService.findByAdminId(Integer.valueOf(claims.get("adminId").toString())));
    }

    @GetMapping("/search/{page}/{size}")
    public Result searchByAdminID(@PathVariable int page, @PathVariable int size, String token) {
        Claims claims = checkToken(token);
        Page<User> pageList = (Page<User>) adminService.searchByAdminId(Integer.valueOf(claims.get("adminId").toString()), page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotal(), pageList.getResult()));
    }

    /**
     * 根据管理员用户用查询管理员信息
     */
    @GetMapping("/adminInfo")
    public Result findByAdminByUsername(String token) {
        Claims claims = checkToken(token);
        String username = claims.get("username").toString();
        Admin admin = adminService.findByUsername(username);
        admin.setPassword(null);
        return new Result(true, StatusCode.OK, "查询成功", admin);
    }

    /**
     * 修改管理员信息
     */
    @PutMapping
    public Result update(@RequestBody Map map) {
        adminService.update(map);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 添加管理员
     */
    @PostMapping
    public Result addAdmin(@RequestBody Admin admin) {
        adminService.save(admin);
        return new Result(true, StatusCode.OK, "添加成功");
    }
}
