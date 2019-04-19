package com.sky.plantogame.service;

import com.sky.plantogame.dao.LotteryRecordDao;
import com.sky.plantogame.pojo.LotteryRecord;
import com.sky.plantogame.pojo.PlanCreate;
import com.sky.plantogame.pojo.SonOfLotteryRecord;
import com.sky.plantogame.utils.GameUtil;
import com.sky.plantogame.vo.ChangLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import utils.CommonUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * lotteryRecord服务层
 *
 * @author Administrator
 */
@Service
public class LotteryRecordService {

    private final LotteryRecordDao lotteryRecordDao;
    private static Map<String, ChangLong> changLongMap;

    private final PlanCreateService planCreateService;

    private final RedisTemplate redisTemplate;

    @Autowired
    public LotteryRecordService(LotteryRecordDao lotteryRecordDao, PlanCreateService planCreateService, RedisTemplate redisTemplate) {
        this.lotteryRecordDao = lotteryRecordDao;
        this.planCreateService = planCreateService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 用当前的开奖数字 获取大小,与上期进行对比.记录连续次数
     *
     * @param gameKey 游戏名
     * @return List<ChangLong>
     */
    public List<ChangLong> getChangLong(String gameKey) {
        //获取最新开奖记录
        LotteryRecord lotteryRecord = lotteryRecordDao.findTop1ByGamekeyOrderByCreateTimeDesc(gameKey);

        ArrayList<ChangLong> list = null;
        if (lotteryRecord != null) {
            list = new ArrayList<>();
            String award = lotteryRecord.getAward();
            int[] awardNum = CommonUtils.getIntOfAward(award); //当前开奖数字
            String[] msg = GameUtil.getStrings(gameKey);
            changLongMap = awardToMap(gameKey, awardNum, "n", msg);
            recursiveComputationChangLong(lotteryRecord); //递归计算长龙
            for (Map.Entry<String, ChangLong> next : changLongMap.entrySet()) {
                ChangLong value = next.getValue();
                list.add(value);
            }
        }
        if ("1407".equals(gameKey)) {
            for (ChangLong c : list) {
                c.setSite("和值");
            }
        }
        return list;
    }


    /**
     * 查询最新开奖信息
     *
     * @param gameKey gameKey
     * @return LotteryRecord
     */
    public SonOfLotteryRecord findNewRecord(String gameKey, String type) {
        LotteryRecord lotteryRecord = lotteryRecordDao.findTop1ByGamekeyOrderByCreateTimeDesc(gameKey);
        SonOfLotteryRecord son = (SonOfLotteryRecord) redisTemplate.opsForValue().get(gameKey + type + lotteryRecord.getGid());
        if (son == null) {
            PlanCreate create = planCreateService.findNewPlan(type, gameKey);
            son = new SonOfLotteryRecord(lotteryRecord);
            son.setServerTime(new Date());
            //判断下次开奖时间是否大于当前时间,否则表示还没有开奖
            son.setOpened(lotteryRecord.getNextOpenTime().getTime() > System.currentTimeMillis());
            son.setNowPlan(create.getMyriaPlane());
            son.setNowTimes(Timeshandler(create.getTimes()));
            if (create.getTimes() == 1) {
                PlanCreate before = planCreateService.findBefore(type, gameKey);
                son.setOldPlan(before.getMyriaPlane());
                son.setOldTimes(Timeshandler(before.getTimes()));
            }
            System.out.println(gameKey + type + "加入缓存");
            redisTemplate.opsForValue().set(gameKey + type + lotteryRecord.getGid(), son, 40, TimeUnit.SECONDS);
        }
        return son;
    }

    private String Timeshandler(Integer times) {
        String result;
        if (times == 1) {
            result = "1";
        } else if (times == 2) {
            result = "3";
        } else if (times == 3) {
            result = "8";
        } else if (times == 4) {
            result = "24";
        } else {
            result = "72";
        }
        return result;
    }

    /**
     * 递归计算长龙
     *
     * @param lotteryRecord lotteryRecord Recursive computation
     */
    private void recursiveComputationChangLong(LotteryRecord lotteryRecord) {
        LotteryRecord beforeRecord = beforeRecord(lotteryRecord.getGid(), lotteryRecord.getGamekey()); //获取上期记录
        if (beforeRecord == null) {
            //如果没有查询到上期数据则停止
            return;
        }
        String award = beforeRecord.getAward(); //获取上期开奖号码
        //上期转换为map
        Map<String, ChangLong> beforeMap = awardToMap(lotteryRecord.getGamekey(), CommonUtils.getIntOfAward(award), "b", GameUtil.getStrings(lotteryRecord.getGamekey()));
        for (int i = 0; i < changLongMap.size(); i++) {
            ChangLong changLong = changLongMap.get("n" + i);
            ChangLong beforeChangLong = beforeMap.get("b" + i);
            if (changLong.getNameFlag()) {  //计算单双次数
                if (beforeChangLong.getName().equals(changLong.getName())) {
                    Integer nameCount = changLong.getNameCount();
                    changLong.setNameCount(++nameCount);
                } else {
                    changLong.setNameFlag(false);
                }
            }
            if (changLong.getTypeFlag()) { //计算大小次数
                if (changLong.getType().equals(beforeChangLong.getType())) {
                    Integer typeCount = changLong.getTypeCount();
                    changLong.setTypeCount(++typeCount);
                } else {
                    changLong.setTypeFlag(false);
                }
            }
            changLongMap.put("n" + i, changLong);
        }
        for (int i = 0; i < changLongMap.size(); i++) { //查询是否还有长龙出现   有则递归查询
            ChangLong changLong = changLongMap.get("n" + i);
            if (changLong.getTypeFlag() || changLong.getNameFlag()) {
                recursiveComputationChangLong(beforeRecord);
                break;
            }
        }
    }

    /**
     * 初始化 为Map对象
     *
     * @param awardNum 开奖数字
     * @param type     指定key
     * @return Map
     */
    private Map<String, ChangLong> awardToMap(String gameKey, int[] awardNum, String type, String[] msg) {
        Map<String, ChangLong> map = new HashMap<>();
        if ("1407".equals(gameKey)) {
            int total = CommonUtils.getTotal(awardNum);
            ChangLong value = initChangLong(gameKey, total);
            map.put(type + 0, value);
        } else {
            for (int i = 0; i < awardNum.length; i++) {
                ChangLong value = initChangLong(gameKey, awardNum[i]);
                value.setSite(GameUtil.getMsgFromSwitch(msg, i));
                map.put(type + i, value);
            }
        }
        return map;
    }

    private ChangLong initChangLong(String gameKey, int i) {
        ChangLong changLong = new ChangLong();
        changLong.setNameCount(1);
        changLong.setTypeCount(1);
        changLong.setNameFlag(true);
        changLong.setTypeFlag(true);
        changLong.setName(i % 2 == 0 ? "双" : "单");
        int num = "1407".equals(gameKey) ? 10 : 5;
        changLong.setType(i > num ? "大" : "小");
        return changLong;
    }

    /**
     * 获取上期记录
     *
     * @param gid gid
     * @return LotteryRecord
     */
    private LotteryRecord beforeRecord(String gid, String gameKey) {
        return lotteryRecordDao.findByGidAndGamekey(beforeGid(gid), gameKey);
    }

    /**
     * 获取上期号
     *
     * @param gid 当前期号
     * @return String
     */
    private String beforeGid(String gid) {
        String front = gid.substring(0, gid.length() - 3);
        String ending = gid.substring(gid.length() - 3);
        int num = Integer.valueOf(ending);
        return front + String.format("%03d", num - 1);
    }
}
