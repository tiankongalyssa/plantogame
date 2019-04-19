package com.sky.plantogame.controller;

import com.sky.plantogame.pojo.Details;
import com.sky.plantogame.pojo.K3;
import com.sky.plantogame.service.DetailsService;
import com.sky.plantogame.service.K3Service;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

/**
 * details控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/details")
@EnableAsync
public class DetailsController {

    private final DetailsService detailsService;
    private final K3Service k3Service;

    @Autowired
    public DetailsController(DetailsService detailsService, K3Service k3Service) {
        this.detailsService = detailsService;
        this.k3Service = k3Service;
    }

    /**
     * 冷热号码
     *
     * @param gameKey gameKey
     * @param size    size
     * @return Result
     */
    @GetMapping("/hotCool/{gameKey}/{size}")
    public Result getAnalysis(@PathVariable String gameKey, @PathVariable Integer size) {
        return new Result(true, StatusCode.OK, "查询成功", detailsService.getHotCool(gameKey, size));
    }

    /**
     * 数据统计
     *
     * @param gameKey  gameKey
     * @param dataType near/yesterday/yesterdaybefore/
     * @param size     size
     * @return Result
     */
    @GetMapping("/{dataType}/{gameKey}/{size}")
    public Result getAnalysis(@PathVariable String dataType, @PathVariable String gameKey, @PathVariable Integer size) {
        return new Result(true, StatusCode.OK, "查询成功", detailsService.getAnalysis(gameKey, dataType, size));
    }

    /**
     * 查询开奖详情列表
     *
     * @param dateType today/yesterday/yesterdayBefore
     * @param gameKey  gameKey
     * @param page     page
     * @param size     size
     * @return Result
     */
    @GetMapping("/{gameKey}/{dateType}/{page}/{size}")
    public Result findDetailsList(@PathVariable String dateType, @PathVariable String gameKey, @PathVariable int page, @PathVariable int size) {
        if ("1407".equals(gameKey)) {
            Page<K3> pageList = k3Service.findK3List(gameKey, dateType, page, size);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
        } else {
            Page<Details> pageList = detailsService.findDetailsList(gameKey, dateType, page, size);
            return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
        }
    }
}
