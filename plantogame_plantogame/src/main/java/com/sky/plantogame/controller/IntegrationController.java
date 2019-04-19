package com.sky.plantogame.controller;

import com.sky.plantogame.service.IntegrationService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

/**
 * 综合分析控制层
 */
@RestController
@CrossOrigin
@RequestMapping("/synthesizes")
@EnableAsync
public class IntegrationController {
    private final IntegrationService integrationService;

    @Autowired
    public IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    /**
     * 展示综合分析数据
     *
     * @param gameKey 游戏名
     * @return Result
     */
    @GetMapping("/{gameKey}")
    @Async
    public Result findGuanYaList(@PathVariable String gameKey) {
        if ("ssc".equals(gameKey) || "1008".equals(gameKey) || "1009".equals(gameKey) || "1407".equals(gameKey)) {
            return new Result(true, StatusCode.OK, "查询成功", integrationService.findTotalList(gameKey));
        } else {
            return new Result(true, StatusCode.OK, "查询成功", integrationService.findGuanYaList(gameKey));
        }
    }
}