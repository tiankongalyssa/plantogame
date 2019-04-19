package com.sky.gaindata.service;

import com.sky.gaindata.dao.PlanCreateDao;
import com.sky.gaindata.pojo.PlanCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdWorker;

/**
 * planCreate服务层
 *
 * @author Administrator
 */
@Service
public class PlanCreateService {

    private final PlanCreateDao planCreateDao;

    private final IdWorker idWorker;

    @Autowired
    public PlanCreateService(PlanCreateDao planCreateDao, IdWorker idWorker) {
        this.planCreateDao = planCreateDao;
        this.idWorker = idWorker;
    }

    /**
     * 查找计划
     *
     * @param type    type
     * @param gameKey gameKey
     * @param present present
     * @return PlanCreate
     */
    public PlanCreate findPlanByGameKeyAndPresent(String type, String gameKey, String present) {
        return planCreateDao.findByTypeAndGamekeyAndPresent(type, gameKey, present);
    }

    public void update(PlanCreate planCreate) {
        planCreateDao.save(planCreate);
    }

    public void add(PlanCreate planCreate) {
        planCreate.setId(idWorker.nextId() + "");
        planCreateDao.save(planCreate);
    }

    /**
     * 查询当天所有预测记录
     *
     * @param gameKey gameKey
     * @param type    type
     * @return Double
     */
    public Double totalPlanNumber(String gameKey, String type) {
        return planCreateDao.countPlanToDay(gameKey, type);
    }

    /**
     * 查询猜中条数
     *
     * @param gameKey gameKey
     * @param type    type
     * @return Double
     */
    public Double winPlanNumber(String gameKey, String type) {
        return planCreateDao.countWinPlanToDay(gameKey, type);
    }
}
