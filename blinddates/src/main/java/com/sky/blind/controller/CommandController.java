package com.sky.blind.controller;

import com.sky.blind.utils.Command;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/command")
public class CommandController {
    @Autowired
    private Command command;

    @GetMapping("/clearImg")
    public Result clearImg(){
       command.clearImg();
       return new Result(true, StatusCode.OK,"操作成功");
    }
}
