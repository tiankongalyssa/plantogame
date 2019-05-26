package com.sky.foregin.controller;

import com.sky.foregin.service.ForeignSMSService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sms")
public class ForeignSMSController {
    @Autowired
    private ForeignSMSService foreignService;

    @PostMapping
    public Result sendSMS(@RequestBody Map data) {
        return new Result(true, StatusCode.OK, foreignService.send(data.get("mobile").toString()));
    }
    @PostMapping("/get_code")
    public Result getCheckCode(@RequestBody Map data) {
        String code = foreignService.getCheckCode(data.get("mobile").toString());
        return new Result(true, StatusCode.OK, "获取成功", code);
    }
}
