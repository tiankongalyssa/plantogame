package com.sky.blind.controller;

import com.sky.blind.service.exception.ServiceException;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionHandler {
    private static int count = 0;

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Result errorHandler(Exception e) {
        if (e instanceof ServiceException) {
            return new Result(false, StatusCode.ERROR, e.getMessage());
        } else {
            String message = "服务器繁忙,请稍后再试!";
            if (count++ == 20) {
                message = "操作失败!请联系管理员";
                if (count > 50) {
                    count = 0;
                }
            }
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, message);
        }
    }
}
