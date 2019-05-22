package com.sky.blind.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utils.JwtUtil;

@Controller
public class BaseController {
    protected final JwtUtil jwtUtil;

    @Autowired
    public BaseController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    protected Claims checkToken(String token) {
        Claims claims;
        try {
            claims = jwtUtil.parseJWT(token);
        } catch (Exception e) {
            throw new RuntimeException("登录过期");
        }
        return claims;
    }
}
