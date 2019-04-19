package com.sky.plantogame.service;

import com.sky.plantogame.dao.GuanYaDao;
import com.sky.plantogame.dao.TotalDao;
import com.sky.plantogame.pojo.GuanYa;
import com.sky.plantogame.pojo.Total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 综合分析服务层
 */
@Service
public class IntegrationService {
    private final GuanYaDao guanYaDao;
    private final TotalDao totalDao;

    @Autowired
    public IntegrationService(GuanYaDao guanYaDao, TotalDao totalDao) {
        this.guanYaDao = guanYaDao;
        this.totalDao = totalDao;
    }



    public List<GuanYa> findGuanYaList(String gameKey) {
        return guanYaDao.findGuanYaList(gameKey);
    }

    public List<Total> findTotalList(String gameKey) {

        return totalDao.findTotalList(gameKey);
    }
}
