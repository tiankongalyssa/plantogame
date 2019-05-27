package com.sky.blind.controller;

import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import com.sky.blind.service.UserService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/login")
    public Result login(@RequestBody Map map) {
        Map checkMap = userService.login(map);
        Admin admin = (Admin) checkMap.get("admin");
        User user = (User) checkMap.get("user");
        Map data = new HashMap();
        data.put("username", admin.getUsername());
        data.put("id", admin.getId());
        data.put("userFace", user.getUserFace());
        String token = jwtUtil.createJWT(admin.getId().toString(), data, "user");
        data.put("token",token);
        data.remove("roles");
        data.remove("id");
        data.remove("exp");
        data.remove("iat");
        return new Result(true, StatusCode.OK, "success", data);
    }
    /**
     * check username
     */
    @GetMapping("/check_username/{username}")
    public Result checkUsername(@PathVariable String username) {
        userService.checkUsername(username);
        return new Result(true, StatusCode.OK, "可以使用的用户名");
    }

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/register/{code}")
    public Result register(@RequestBody Admin admin, @PathVariable String code) {
        userService.register(admin, code);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @return
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 虚拟在线人数
     */
    @GetMapping("/online")
    public Result online() {
        Integer online = userService.findOnline();
        return new Result(true, StatusCode.OK, "查询成功", online);
    }

    /**
     * 分页查询记录
     */
    @GetMapping("/search/{page}/{size}")
    public Result fingPage(@PathVariable int page, @PathVariable int size) {
        List<User> list = userService.findPage(page, size);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 查询所有记录
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询记录
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }

    /**
     * 修改记录
     */
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 添加记录
     */
    @PutMapping
    public Result save(@RequestBody User user, String token) {
        Claims claims = checkToken(token);
        user.setAdminId(Integer.valueOf(claims.get("adminId").toString()));
        userService.save(user);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 删除记录
     */
    @DeleteMapping
    public Result deleteRecord(Integer id) {
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
