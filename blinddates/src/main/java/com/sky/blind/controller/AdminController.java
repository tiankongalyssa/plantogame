package com.sky.blind.controller;

import com.sky.blind.service.AdminService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody Map map) {
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        adminService.login(username, password);
        return new Result(true, StatusCode.OK, "登录成功");
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody Map map) {
        String username = map.get("username").toString();
        adminService.logout(username);
        return new Result(true, StatusCode.OK, "退出成功");
    }

    @GetMapping("/{adminId}")
    public Result findByAdminId(@PathVariable Integer adminId) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findByAdminId(adminId));
    }
}
