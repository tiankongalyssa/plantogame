package com.sky.foregin.controller;

import com.sky.foregin.service.exception.ServiceException;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Result errorHandler(Exception e) {
        if (e instanceof ServiceException) {
            return new Result(false, StatusCode.ERROR, e.getMessage());
        } else {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "操作失败请联系管理员");
        }
    }
}
