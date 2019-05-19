package com.sky.blind.controller;

import com.sky.blind.pojo.User;
import com.sky.blind.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map data) {
        return new Result(true, StatusCode.OK, "登录成功", userService.login(data.get("username").toString(), data.get("password").toString()));
    }

    @PostMapping
    public Result update(@RequestBody User user) {
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }
    @PostMapping("/pwd")
    public Result updatePasswrd(@RequestBody Map map) {
        String username = map.get("username").toString();
        String password = map.get("password").toString();
        String newPassword = map.get("newPassword").toString();
        userService.updatePassword(username, password, newPassword);
        return new Result(true, StatusCode.OK, "修改成功");
    }
}
