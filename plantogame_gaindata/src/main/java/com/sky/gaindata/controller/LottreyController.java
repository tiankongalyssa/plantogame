package com.sky.gaindata.controller;

import com.sky.gaindata.service.LottreyService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * lottrey控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/lottrey")
public class LottreyController {


    private final LottreyService lottreyService;

    @Autowired
    public LottreyController(LottreyService lottreyService) {
        this.lottreyService = lottreyService;
    }

    @GetMapping("/test")
    public Result test(){
        return new Result(true,StatusCode.OK,"hello");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", lottreyService.findAll());
    }
}
