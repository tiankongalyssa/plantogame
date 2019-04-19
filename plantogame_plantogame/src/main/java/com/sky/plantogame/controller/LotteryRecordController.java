package com.sky.plantogame.controller;

import com.sky.plantogame.service.LotteryRecordService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

/**
 * 基本走势控制层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/lotteryRecord")
@EnableAsync
public class LotteryRecordController {

    private final LotteryRecordService lotteryRecordService;

    @Autowired
    public LotteryRecordController(LotteryRecordService lotteryRecordService) {
        this.lotteryRecordService = lotteryRecordService;
    }

    /**
     * 长龙提醒
     *
     * @param gameKey gameKey
     * @return Result
     */
    @GetMapping("/changLong/{gameKey}")
    public Result getChangLong(@PathVariable String gameKey) {
        return new Result(true, StatusCode.OK, "查询成功", lotteryRecordService.getChangLong(gameKey));
    }

    /**
     * 查询最新开奖信息和最新计划
     *
     * @param gameKey gameKey
     * @return Result
     */
    @RequestMapping(value = "/{gameKey}/{type}", method = RequestMethod.GET)
    public Result findNewGameInfo(@PathVariable String gameKey,@PathVariable String type) {
        return new Result(true, StatusCode.OK, "查询成功", lotteryRecordService.findNewRecord(gameKey,type));
    }
}