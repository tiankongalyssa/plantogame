package com.sky.plantogame.service;

import com.sky.plantogame.dao.PlanCreateDao;
import com.sky.plantogame.pojo.PlanCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * 查询当天所有计划数
     *
     * @param type
     * @param gameKey
     * @return
     */
    public List<PlanCreate> findTodayList(String type, String gameKey) {
        List<PlanCreate> dataList = planCreateDao.findTodayList(type, gameKey);
        List<PlanCreate> result = new ArrayList<>(dataList);
        for (int i = 0; i < dataList.size(); i++) {
            String gidFirst = dataList.get(i).getGidFirst();
            String gidThird = dataList.get(i).getGidThird();
            if ("bjpk10".equals(gameKey)) {
                result.get(i).setGidFirst(gidFirst + "-" + gidThird);
            } else {
                result.get(i).setGidFirst(getSubstring(gidFirst) + "-" + getSubstring(gidThird));
            }
        }
        return result;
    }

    /**
     * 查找最新预测
     *
     * @param type
     * @param gameKey
     * @return
     */
    public PlanCreate findNewPlan(String type, String gameKey) {
        PlanCreate plan = planCreateDao.findNewPlant(type, gameKey, "1");

        if(plan==null){
            throw new RuntimeException(type+"类型计划不存在");
        }
        //设置期号格式
        if ("bjpk10".equals(gameKey)||"1304".equals(gameKey) ||"1306".equals(gameKey)) {
            plan.setGidFirst(plan.getGidFirst() + "-" + plan.getGidThird());
        } else {
            plan.setGidFirst(getSubstring(plan.getGidFirst()) + "-" + getSubstring(plan.getGidThird()));
        }
        if (null == plan.getProbability()) {
            plan.setProbability(new Random().nextInt(30) + 65);
        }
        return plan;
    }
    public PlanCreate findBefore (String type,String gameKey){
        PlanCreate before = planCreateDao.findBefore(gameKey, type);
        if(before==null){
            throw new RuntimeException(type+"类型计划不存在");
        }
        //设置期号格式
        if ("bjpk10".equals(gameKey)||"1304".equals(gameKey)||"1306".equals(gameKey)) {
            before.setGidFirst(before.getGidFirst() + "-" + before.getGidThird());
        } else {
            before.setGidFirst(getSubstring(before.getGidFirst()) + "-" + getSubstring(before.getGidThird()));
        }
        if (null == before.getProbability()) {
            before.setProbability(new Random().nextInt(30) + 65);
        }
        return before;
    }

    private String getSubstring(String str) {
        return str.substring(str.length() - 3);
    }


}
