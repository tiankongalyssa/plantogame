package com.sky.blind.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Result errorHandler(Exception e) {
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
