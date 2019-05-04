package com.sky.gaindata.work;

import com.alibaba.fastjson.JSONObject;
import com.sky.gaindata.dao.GuanYaDao;
import com.sky.gaindata.dao.TotalDao;
import com.sky.gaindata.pojo.*;
import com.sky.gaindata.service.*;
import com.sky.gaindata.utils.HttpUtil;
import com.sky.gaindata.vo.GuanYa;
import com.sky.gaindata.vo.Lottery;
import com.sky.gaindata.vo.LotteryX78;
import com.sky.gaindata.vo.Total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import utils.CommonUtils;
import utils.IdWorker;

import java.io.IOException;
import java.util.*;

import static com.sky.gaindata.work.GameHandler.getThreeIssue;

/**
 * ApplicationRunner ：启动程序则执行run方法
 */
@Component
public class GameRunner extends BaseWork implements ApplicationRunner {
    private static Map<String, Long> nextOpenTimes = new HashMap<>(); //记录下次开奖时间
    private final LottreyService lottreyService;
    private final LotteryRecordService lotteryRecordService;
    private final DetailsService detailsService;
    private final PlanCreateService planService;
    private final GuanYaDao guanYaDao;
    private final IdWorker idWorker;
    private final TotalDao totalDao;
    private final K3Service k3Service;

    @Autowired
    public GameRunner(LottreyService lottreyService, LotteryRecordService lotteryRecordService, DetailsService detailsService, PlanCreateService planCreateService, TotalDao totalDao, IdWorker idWorker, GuanYaDao guanYaDao, K3Service k3Service) {
        this.lottreyService = lottreyService;
        this.lotteryRecordService = lotteryRecordService;
        this.detailsService = detailsService;
        this.planService = planCreateService;
        this.idWorker = idWorker;
        this.totalDao = totalDao;
        this.guanYaDao = guanYaDao;
        this.k3Service = k3Service;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Hello Boss!  I'm running!");
        init();//初始化激活彩种
        working();
    }

    private void init() {
        List<Lottrey> lottreys = lottreyService.findByState(1);
        for (Lottrey lottrey : lottreys) {
            handlerGame(lottrey.getGamekey());
            LotteryRecord topByGamekey = lotteryRecordService.findTopByGamekey(lottrey.getGamekey());
            if (null != topByGamekey) {
                putTime(topByGamekey);
            }
        }
    }

    /**
     * 监听下次开奖时间
     * 遍历等待时间map，添加等待时间到了list，排序list
     */
    private void working() {
        List<Long> waitList = new ArrayList<>();
        while (true) {
            //遍历开奖时间已经到了的则请求数据
            for (Map.Entry<String, Long> entry : nextOpenTimes.entrySet()) {
                Long value = entry.getValue();
                waitList.add(value);
                if (value <= System.currentTimeMillis()) {
                    handlerGame(entry.getKey());
                    putTime(lotteryRecordService.findTopByGamekey(entry.getKey()));
                }
            }
            try {
                Thread.sleep(getWait(waitList));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    //排序等待时间为最近的一个
    private long getWait(List<Long> waitList) {
        if (waitList.size() <= 0) {
            return 10000L;
        }
        Collections.sort(waitList);
        long wait = waitList.get(0) - System.currentTimeMillis();
        waitList.clear();
        wait = wait <= 0 ? 5000 : wait;
        System.out.println("WAIT:" + wait / 1000 + "s");
        return wait + 2000L;
    }

    /**
     * 流程处理
     *
     * @param gameKey gameKey
     */
    private void handlerGame(String gameKey) {
        try {
            //请求接口转换为java对象
            String foreignData = HttpUtil.getForeignData(gameKey, 1);
            LotteryRecord lotteryRecord = jsonToLotteryRecord(gameKey, foreignData);
            //判断数据是否存在
            if (null != lotteryRecordService.findByGamekeyAndNextOpenIssue(gameKey, lotteryRecord.getGid())) {
                System.out.println("TYPE:" + lotteryRecord.getGamekey() + " GID:" + lotteryRecord.getGid() + "is already exist! next open time:" + lotteryRecord.getNextOpenTime());
                return;
            }
            //保存到数据库
            saveData(lotteryRecord);
            //获取下次开奖时间
            putTime(lotteryRecord);
            System.out.println("TYPE:" + lotteryRecord.getGamekey() + " Next Open Time:" + lotteryRecord.getNextOpenTime());
            //安排计划数据
            createPlan(lotteryRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData(LotteryRecord lotteryRecord) {
        lotteryRecordService.add(lotteryRecord); //记录
        if ("1407".equals(lotteryRecord.getGamekey())) {
            K3 byGameKey = k3Service.findByGameKey(lotteryRecord.getGamekey());
            K3 k3 = new K3(lotteryRecord);
            k3.setNumTrend(getNumTrend(byGameKey, lotteryRecord));
            k3.setSummation(getSummation(byGameKey, lotteryRecord));
            k3.setSumForm(getSumForm(byGameKey, lotteryRecord)); //修改为大小单双
            k3.setNumForm(getNumForm(byGameKey, lotteryRecord));
            saveToDetails(lotteryRecord); //冷热号码要用
            k3Service.save(k3);
        } else {
            saveToDetails(lotteryRecord); //详细记录
        }
        //龙虎 冠亚
        saveGuanYaLongHu(lotteryRecord);
    }

    private void createPlan(LotteryRecord lotteryRecord) {
        savePlan(lotteryRecord, "create");
        savePlan(lotteryRecord, "dream");
        savePlan(lotteryRecord, "volcano");
    }

    private LotteryRecord jsonToLotteryRecord(String gameKey, String data) {
        LotteryRecord lotteryRecord;
        if (urlList.contains(gameKey)) { //如果是x78接口
            LotteryX78 lotteryX78 = JSONObject.parseObject(data).getJSONArray("data").getJSONObject(0).toJavaObject(LotteryX78.class);
            lotteryRecord = new LotteryRecord(lotteryX78);
            //下期期号和开奖时间
            String nextOpenIssue = getThreeIssue(lotteryX78.getIssue())[1];
            lotteryRecord.setNextOpenIssue(nextOpenIssue);
            Date nextOpenTime = getNextOpenTime(lotteryX78.getOpenTime(), lotteryX78.getLotteryCode());
            lotteryRecord.setNextOpenTime(nextOpenTime);
        } else {
            Lottery lottery = JSONObject.parseObject(data).getJSONObject("result").getJSONObject("data").toJavaObject(Lottery.class);
            lotteryRecord = new LotteryRecord(lottery);
            lotteryRecord.setGamekey(gameKey);
        }
        return lotteryRecord;
    }

    private Date getNextOpenTime(Date openTime, String lotteryCode) {
        //如果彩种多可以声明为静态 这里只有1009是5分钟所有直接判断
//        List<String> every5minutes = new ArrayList<>();
//        every5minutes.add("1009");
//        List<String> every1minutes = new ArrayList<>();
//        every1minutes.add("1008");
//        every1minutes.add("1304");
//        every1minutes.add("1407");
        int num = "1306".equals(lotteryCode) ? 5 : 1;
        return new Date(openTime.getTime() + 1000 * 60 * num);
    }

    private void saveGuanYaLongHu(LotteryRecord lottery) {
        //保存龙虎  否则保存冠亚
        if ("ssc".equals(lottery.getGamekey()) || "1008".equals(lottery.getGamekey()) || "1009".equals(lottery.getGamekey()) || "1407".equals(lottery.getGamekey())) {
            Total total = new Total();
            Date createDate = new Date();
            StringBuilder builder = new StringBuilder();
            total.setId(idWorker.nextId() + "");
            total.setGid(lottery.getGid());
            total.setAward(lottery.getAward());
            total.setCreateTime(createDate);
            total.setGamekey(lottery.getGamekey());
            String[] info = GameHandler.getGuanYa(lottery);
            if ("1407".equals(lottery.getGamekey())) {
                //总和
                total.setTotal(builder.append(info[0]).append(",").append(info[1]).append(",").append(info[2]).toString());
                builder.setLength(0);
                //大小
                builder.append(info[3]).append(",").append(info[4]).append(",").append(info[5]);
                total.setSize(builder.toString());
                builder.setLength(0);
                //单双
                builder.append(info[6]).append(",").append(info[7]).append(",").append(info[8]);
                total.setOddEven(builder.toString());
                builder.setLength(0);
                //前
                total.setFistMidBack(info[9]);
            } else {
                //总和
                total.setTotal(builder.append(info[0]).append(",").append(info[1]).append(",").append(info[2]).toString());
                builder.setLength(0);
                //龙虎
                total.setDragonTiger(builder.append(info[3]).toString());
                builder.setLength(0);
                //大小
                builder.append(info[4]).append(",").append(info[5]).append(",").append(info[6]).append(",").append(info[7]).append(",").append(info[8]);
                total.setSize(builder.toString());
                builder.setLength(0);
                //单双
                builder.append(info[9]).append(",").append(info[10]).append(",").append(info[11]).append(",").append(info[12]).append(",").append(info[13]);
                total.setOddEven(builder.toString());
                builder.setLength(0);
                //前中后
                builder.append(info[14]).append(",").append(info[15]).append(",").append(info[16]);
                total.setFistMidBack(builder.toString());
            }
            totalDao.save(total);
        } else {
            GuanYa guanYa = new GuanYa();
            guanYa.setId(idWorker.nextId() + "");
            guanYa.setGid(lottery.getGid());
            guanYa.setAward(lottery.getAward());
            guanYa.setGamekey(lottery.getGamekey());
            String[] info = GameHandler.getGuanYa(lottery);
            guanYa.setGuanYa(info[0]);
            guanYa.setSize(info[1]);
            guanYa.setOddEven(info[2]);
            guanYa.setDragonTigerOne(info[3]);
            guanYa.setDragonTigerTow(info[4]);
            guanYa.setDragonTigerThree(info[5]);
            guanYa.setDragonTigerFour(info[6]);
            guanYa.setDragonTigerFive(info[7]);
            guanYa.setCreateTime(new Date());
            guanYaDao.save(guanYa);
        }
    }

    /**
     * 创建万位计划
     *
     * @param lotteryRecord lotteryRecord
     * @author sky
     * 2019/3/30
     */
    private void savePlan(LotteryRecord lotteryRecord, String type) {
        Date date = new Date();
        //查看计划是否存在
        PlanCreate plan = planService.findPlanByGameKeyAndPresent(type, lotteryRecord.getGamekey(), "1");
        if (plan == null) {  //如果计划不存在则创建一个
            plan = getNewPlan(lotteryRecord, type);
        }
        // 检查是否猜中
        Map<String, Object> map;
        int totalNum = 0;
        if ("1407".equals(lotteryRecord.getGamekey())) {
            //获取本期开奖总和
            int total = CommonUtils.getTotal(CommonUtils.getIntOfAward(lotteryRecord.getAward()));
            totalNum = total;
            map = GameHandler.checkWin(lotteryRecord.getGamekey(), lotteryRecord.getGid(), total, plan);
        } else {
            map = GameHandler.checkWin(lotteryRecord.getGamekey(), lotteryRecord.getGid(), Integer.parseInt(lotteryRecord.getAward().split(",")[0]), plan);
        }
        //获取猜中机率
        Integer probatilityNum = GameHandler.getProbatilityNum(planService.totalPlanNumber(plan.getGamekey(), plan.getType()), planService.winPlanNumber(plan.getGamekey(), plan.getType()));
        boolean checkWinFlag = (boolean) map.get("flag"); //是否猜中
        int winCode = (int) map.get("win");  //获取期数
        Integer times = plan.getTimes(); //获取倍数
        times = times == null ? 1 : times;
        if (checkWinFlag) { //中
            plan.setWin(winCode); //修改win
            updatePlan(lotteryRecord, date, plan, totalNum, probatilityNum);
            //创建新的一条
            lotteryRecord.setGid(getThreeIssue(lotteryRecord.getGid())[1]); //设置期号为下期,期号
            plan = getNewPlan(lotteryRecord, type);
            plan.setProbability(probatilityNum);
            planService.add(plan);
        } else if (winCode == 3) { //3期内都没有猜中
            plan.setWin(0); //修改win
            setMyriaAward(lotteryRecord, plan, totalNum);
            updatePlan(lotteryRecord, date, plan, totalNum, probatilityNum);
            lotteryRecord.setGid(getThreeIssue(lotteryRecord.getGid())[1]); //设置期号为下期,期号
            //创建一条预测
            plan = getNewPlan(lotteryRecord, type);
            plan.setTimes(times + 1); //将新计划的倍数设置为上期倍数
            plan.setProbability(probatilityNum);
            planService.add(plan);
        } else { //本期内没有猜中 修改更新时间,修改开奖号码
            plan.setUpdateTime(date);
            plan.setTimes(times + 1);
            setMyriaAward(lotteryRecord, plan, totalNum);
            planService.update(plan);
        }
    }

    private void updatePlan(LotteryRecord lotteryRecord, Date date, PlanCreate plan, int totalNum, Integer probatilityNum) {
        plan.setUpdateTime(date);
        plan.setPresent("0"); //修改present为0(表示没有进行)
        plan.setProbability(probatilityNum);
        setMyriaAward(lotteryRecord, plan, totalNum);
        planService.update(plan);
    }

    private void setMyriaAward(LotteryRecord lotteryRecord, PlanCreate plan, int totalNum) {
        if ("1407".equals(lotteryRecord.getGamekey())) {
            plan.setMyriaAward(totalNum > 10 ? "大" : "小");
        } else {
            plan.setMyriaAward(GameHandler.getMyriaAward(lotteryRecord.getAward()));
        }
    }


    private void putTime(LotteryRecord lotteryRecord) {
        nextOpenTimes.put(lotteryRecord.getGamekey(), lotteryRecord.getNextOpenTime().getTime());
    }

    private void saveToDetails(LotteryRecord lotteryRecord) {
        //获取上期彩种开奖详情
        Details beforeData = detailsService.findBeforeByGameKey(lotteryRecord.getGamekey());
        String award = lotteryRecord.getAward();
        String[] split = award.split(",");
        Details details = new Details();
        details.setGid(lotteryRecord.getGid());
        details.setGamekey(lotteryRecord.getGamekey());
        details.setAward(lotteryRecord.getAward());
        details.setTime(lotteryRecord.getCreateTime());
        setterDetails(beforeData, details, split);  //设置详细表信息
        detailsService.add(details);
    }

    /**
     * 设置详细开奖记录
     *
     * @param beforeData 上期详情
     * @param details    基本详情
     * @param split      本期开奖结果
     */
    private void setterDetails(Details beforeData, Details details, String[] split) {
        if (beforeData == null) {
            beforeData = details;
        }
        details.setZero(GameHandler.changMaxMiss(beforeData.getZero(), beforeData.getBeforeZero(), split[0], beforeData.getGamekey()));
        details.setOne(GameHandler.changMaxMiss(beforeData.getOne(), beforeData.getBeforeOne(), split[1], beforeData.getGamekey()));
        details.setTow(GameHandler.changMaxMiss(beforeData.getTow(), beforeData.getBeforeTow(), split[2], beforeData.getGamekey()));
        details.setBeforeZero(split[0]);
        details.setBeforeOne(split[1]);
        details.setBeforeTow(split[2]);
        if (split.length > 3) {
            details.setThree(GameHandler.changMaxMiss(beforeData.getThree(), beforeData.getBeforeThree(), split[3], beforeData.getGamekey()));
            details.setFour(GameHandler.changMaxMiss(beforeData.getFour(), beforeData.getBeforeFour(), split[4], beforeData.getGamekey()));
            details.setBeforeThree(split[3]);
            details.setBeforeFour(split[4]);
        }
        if (split.length > 5) {
            details.setFive(GameHandler.changMaxMiss(beforeData.getFive(), beforeData.getBeforeFive(), split[5], beforeData.getGamekey()));
            details.setSix(GameHandler.changMaxMiss(beforeData.getSix(), beforeData.getBeforeSix(), split[6], beforeData.getGamekey()));
            details.setSeven(GameHandler.changMaxMiss(beforeData.getSeven(), beforeData.getBeforeSeven(), split[7], beforeData.getGamekey()));
            details.setEight(GameHandler.changMaxMiss(beforeData.getEight(), beforeData.getBeforeEight(), split[8], beforeData.getGamekey()));
            details.setNine(GameHandler.changMaxMiss(beforeData.getNine(), beforeData.getBeforeNine(), split[9], beforeData.getGamekey()));
            details.setBeforeFive(split[5]);
            details.setBeforeSix(split[6]);
            details.setBeforeSeven(split[7]);
            details.setBeforeEight(split[8]);
            details.setBeforeNine(split[9]);
        }
    }

    private PlanCreate getNewPlan(LotteryRecord record, String type) {
        PlanCreate plan = new PlanCreate();
        Date date = new Date();
        plan.setId(idWorker.nextId() + "");
        plan.setGamekey(record.getGamekey());
        String[] threeIssue = getThreeIssue(record.getGid());
        plan.setGidFirst(threeIssue[0]);
        plan.setGidSecond(threeIssue[1]);
        plan.setGidThird(threeIssue[2]);
        plan.setMyriaPlane(GameHandler.getPlane(record.getGamekey()));
        plan.setWin(-1);
        plan.setTimes(1);
        plan.setPresent("1");
        plan.setType(type);
        plan.setCreateTime(date);
        plan.setUpdateTime(date);
        return plan;
    }

    private String getNumTrend(K3 beforK3, LotteryRecord lotteryRecord) {
        if (beforK3 == null) {
            beforK3 = new K3();
        }
        return getForm(beforK3.getNumTrend(), beforK3.getAward(), lotteryRecord.getAward(), 6);
    }

    private String getSummation(K3 beforK3, LotteryRecord lotteryRecord) {
        if (beforK3 == null) {
            beforK3 = new K3();
            beforK3.setAward("3"); //默认设置为3
        }

        int beforTotal = getTotal(CommonUtils.getIntOfAward(beforK3.getAward()));
        int nowTotal = getTotal(CommonUtils.getIntOfAward(lotteryRecord.getAward()));
        return getForm(beforK3.getSummation(), beforTotal - 2 + ",", nowTotal - 2 + ",", 16);
    }

    private int getTotal(int[] ints) {
        int total = 0;
        for (int n : ints) {
            total += n;
        }
        return total;
    }

    private String getSumForm(K3 beforK3, LotteryRecord lotteryRecord) {
        if (beforK3 == null) {
            beforK3 = new K3();
            beforK3.setAward("1,2,3"); //默认设置为3
        }
        //上期的组合形态 1:小奇 2:小偶 3:大奇 4:大偶
        //修改为        大         小    单     双
        String beforValue = getSumValue(beforK3.getAward());
        //本期组合形态  大小单双
        String nowValue = getSumValue(lotteryRecord.getAward());
        return getForm(beforK3.getSumForm(), beforValue, nowValue, 4);
    }

    /**
     * 获取和值组合形态
     *
     * @param award 传入开奖数字
     * @return int 1:小奇 2:小偶 3:大奇 4:大偶
     */
    private String getSumValue(String award) {
        StringBuilder builder = new StringBuilder();
        int total = getTotal(CommonUtils.getIntOfAward(award));
        if (total > 10) {
            builder.append(1).append(",");
        } else {
            builder.append(2).append(",");
        }
        if (!(total % 2 == 0)) {
            builder.append(3).append(",");
        } else {
            builder.append(4).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    private String getNumForm(K3 beforK3, LotteryRecord lotteryRecord) {
        if (beforK3 == null) {
            beforK3 = new K3();
            beforK3.setAward("1,2,3"); //默认设置为3
            beforK3.setNumForm("0,0,0,0,0,0");
        }
        String beforPlane = getPlace(beforK3.getAward());
        String nowPlane = getPlace(lotteryRecord.getAward());
        return getForm(beforK3.getNumForm(), beforPlane, nowPlane, 6);
    }

    /**
     * 获取 号码形态位置
     *
     * @param award 开奖号
     * @return int[]  三同号,三不同号,三连号,二同号(复),二同号(单),二不同号  1,2,3,4,5,6
     */
    private String getPlace(String award) {
        int[] awardInt = CommonUtils.getIntOfAward(award);
        Arrays.sort(awardInt);
        List<Integer> list = new ArrayList<>();
        //三同
        boolean b1 = awardInt[0] == awardInt[1] && awardInt[1] == awardInt[2];
        //三不同
        boolean b2 = awardInt[0] != awardInt[1] && awardInt[0] != awardInt[2] && awardInt[1] != awardInt[2];
        //三连
        boolean b3 = awardInt[0] + 1 == awardInt[1] && awardInt[0] + 2 == awardInt[2];
        //二同
        boolean b4 = awardInt[0] == awardInt[1] || awardInt[1] == awardInt[2] || awardInt[2] == awardInt[0];
        //二不同
        boolean b5 = awardInt[0] != awardInt[1] || awardInt[0] != awardInt[2];
        if (b1) {
            list.add(1);
        }
        if (b2) {
            list.add(2);
        }
        if (b3) {
            list.add(3);
        }
        if (b4) {
            list.add(4);
            list.add(5);
        }
        if (b5) {
            list.add(6);
        }
        StringBuilder builder = new StringBuilder();
        for (Integer aList : list) {
            builder.append(aList).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    /**
     * 记录号码走势
     *
     * @param beforRecord 上期记录 每位加1
     * @param beforAward  上期开奖号码 重置上期为1
     * @param award       本期开奖号码 重置为0
     * @param num         记录多少位数字
     * @return String
     */
    private String getForm(String beforRecord, String beforAward, String award, int num) {
        //返回   3,6@2   1@2,5   3,4,5
        //        1,1,3,1,1,6@2

        int[] beforArray;
        if ("".equals(beforRecord) || beforRecord == null) {
            beforArray = new int[num];
        } else {
            beforArray = CommonUtils.getIntOfAward(beforRecord);
        }
        //每位数字加1
//        System.out.println("每位数字加1");
        for (int i = 0; i < beforArray.length; i++) {
            beforArray[i]++;
        }
        //上期开奖位置重置为1
        int[] bPlaceNum = CommonUtils.getIntOfAward(beforAward == null || "".equals(beforAward) ? award : beforAward);
        for (int aPlaceNum : bPlaceNum) {
            beforArray[aPlaceNum - 1] = 1;
        }
        //本期开奖数字赋值
        int[] awardNum = CommonUtils.getIntOfAward(award);
        for (int n : awardNum) {
            beforArray[n - 1] = 0;
        }
        //判断本期开奖数字有无重复
        int repetition = -1;
        int count = 1;
        for (int i = 0; i < awardNum.length - 1; i++) {
            for (int j = i + 1; j < awardNum.length; j++) {
                if (awardNum[i] == awardNum[j] && (i != j)) {
                    repetition = awardNum[i];
                    count++;
                }
            }
        }
        //封装成str
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < beforArray.length; i++) {
            if (repetition != -1 && repetition - 1 == i) {
                builder.append(beforArray[i]).append("@").append(awardNum[0] == awardNum[1] && awardNum[1] == awardNum[2] ? 3 : count).append(",");
            } else {
                builder.append(beforArray[i]).append(",");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }
}
