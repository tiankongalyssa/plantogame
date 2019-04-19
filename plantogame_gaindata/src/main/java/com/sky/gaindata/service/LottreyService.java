package com.sky.gaindata.service;

import com.sky.gaindata.dao.LottreyDao;
import com.sky.gaindata.pojo.Lottrey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * lottrey服务层
 *
 * @author Administrator
 */
@Service
public class LottreyService {

    private final LottreyDao lottreyDao;

    @Autowired
    public LottreyService(LottreyDao lottreyDao) {
        this.lottreyDao = lottreyDao;

    }

    public List<Lottrey> findByState(Integer state) {
        return lottreyDao.findByState(state);
    }

    /**
     * 查询全部列表
     *
     * @return List<Lottrey>
     */
    public List<Lottrey> findAll() {
        return lottreyDao.findAll();
    }
}
