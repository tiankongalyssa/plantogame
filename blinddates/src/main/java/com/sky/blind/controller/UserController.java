package com.sky.blind.controller;

import com.sky.blind.pojo.User;
import com.sky.blind.service.UserService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(JwtUtil jwtUtil, UserService userService) {
        super(jwtUtil);
        this.userService = userService;
    }

    @GetMapping("/search/{page}/{size}")
    public Result fingPage(@PathVariable int page,@PathVariable int size){
        List<User> list = userService.findPage(page,size);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @PutMapping
    public Result addRecord(@RequestBody User user, String token) {
        Claims claims = checkToken(token);
        user.setAdminId(Integer.valueOf(claims.get("adminId").toString()));
        userService.addRecord(user);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @DeleteMapping
    public Result deleteRecord(Integer id) {
        userService.deleteRecordById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
