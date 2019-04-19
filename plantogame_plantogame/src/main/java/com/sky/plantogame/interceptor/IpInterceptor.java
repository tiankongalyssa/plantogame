package com.sky.plantogame.interceptor;

import com.sky.plantogame.utils.IPUtils;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.HttpServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class IpInterceptor implements HandlerInterceptor {
    @Value("${ip}")
    private String[] ipList;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<String> list = Arrays.asList(ipList);
        String realIP = IPUtils.getRealIP(request);
        System.out.println("realIP = " + realIP);
        if (!list.contains(realIP)) {
            HttpServletUtil.sendJsonInfo(response, new Result(false, StatusCode.ERROR, "access denied"));
            return false;
        }
        return true;
    }
}