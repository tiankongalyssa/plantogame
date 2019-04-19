package com.sky.plantogame.controller;

import com.sky.plantogame.service.PlanCreateService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

/**
 * planCreate控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/planCreate")
@EnableAsync
public class PlanCreateController {

    private final PlanCreateService planCreateService;

    @Autowired
    public PlanCreateController(PlanCreateService planCreateService) {
        this.planCreateService = planCreateService;
    }

    /**
     * 获取最新计划
     *
     * @param type    type
     * @param gameKey gameKey
     * @return Result
     */
    @GetMapping("/{type}/{gameKey}")
    @Async
    public Result findNewPlan(@PathVariable String type, @PathVariable String gameKey) {
        return new Result(true, StatusCode.OK, "查询成功", planCreateService.findNewPlan(type, gameKey));
    }

    /**
     * 获取当天所有预测计划
     *
     * @param type    type
     * @param gameKey gameKey
     * @return Result
     */
    @GetMapping("/list/{type}/{gameKey}")
    @Async
    public Result findTodayList(@PathVariable String type, @PathVariable String gameKey) {
        return new Result(true, StatusCode.OK, "查询成功", planCreateService.findTodayList(type, gameKey));
    }
}
