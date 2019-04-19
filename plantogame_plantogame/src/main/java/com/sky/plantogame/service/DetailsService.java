package com.sky.plantogame.service;

import com.sky.plantogame.dao.DetailsDao;
import com.sky.plantogame.pojo.Details;
import com.sky.plantogame.utils.GameUtil;
import com.sky.plantogame.vo.AnalysisResult;
import com.sky.plantogame.vo.HotCool;
import com.sky.plantogame.vo.Num;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * details服务层
 *
 * @author Administrator
 */
@Service
public class DetailsService {

    private final DetailsDao detailsDao;

    private final K3Service k3Service;

    @Autowired
    public DetailsService(DetailsDao detailsDao, K3Service k3Service) {
        this.detailsDao = detailsDao;
        this.k3Service = k3Service;
    }

    public List<HotCool> getHotCool(String gameKey, Integer size) {
        List<HotCool> list = new ArrayList<>();
        //获取指定游戏的统计数据
        List<AnalysisResult> analysis = getAnalysis(gameKey, "near", size);
        if ("1407".equals(gameKey)) {
            HotCool e = oneHotCool(analysis.get(0).getAppear(), 1, gameKey, GameUtil.getStrings(gameKey));
            e.setPlace("冷热数字");
            list.add(e);
        } else {
            for (int i = 0; i < analysis.size(); i++) {
                list.add(oneHotCool(analysis.get(i).getAppear(), i, gameKey, GameUtil.getStrings(gameKey)));
            }
        }
        return list;
    }

    /**
     * 获取一条HotCool
     *
     * @param appear  num 位 各数字的出现次数
     * @param num     位置
     * @param gameKey 游戏名
     * @param msg     位置名称
     * @return HotCool
     */
    private HotCool oneHotCool(String appear, int num, String gameKey, String[] msg) {
        //出现次数
        String[] split = appear.split(",");
        //转换
        List<Integer> integers = new ArrayList<>();
        List<Num> numb = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            Integer e = Integer.valueOf(split[i]);
            integers.add(e);
            if ("ssc".equals(gameKey) || "1008".equals(gameKey) || "1009".equals(gameKey)) {
                numb.add(new Num(i, e));
            } else {
                numb.add(new Num(i + 1, e));
            }
        }
        //获取最大和最小
        Integer max = Collections.max(integers);
        Integer min = Collections.min(integers);

        List<Num> hot = new ArrayList<>();
        List<Num> warmth = new ArrayList<>();
        List<Num> cool = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            Num n = numb.get(i);
            if (n.getValue().equals(max)) {
                hot.add(n);
            } else if (n.getValue().equals(min)) {
                cool.add(n);
            } else {
                warmth.add(n);
            }
        }
        HotCool hotCool = new HotCool();
        hotCool.setHot(hot);
        hotCool.setWarmth(warmth);
        hotCool.setCool(cool);
        hotCool.setGameKey(gameKey);
        String msgFromSwitch = GameUtil.getMsgFromSwitch(msg, num);
        hotCool.setPlace(msgFromSwitch);
        return hotCool;
    }

    /**
     * 获取数据统计
     *
     * @param gameKey  gameKey
     * @param dataType dataType
     * @param size     size
     * @return List<AnalysisResult>
     * @throws RuntimeException size参数错误
     */
    public List<AnalysisResult> getAnalysis(String gameKey, String dataType, Integer size) throws RuntimeException {
        if (size <= 10) {
            throw new RuntimeException("size参数错误");
        }
        List<AnalysisResult> result;
        if ("1407".equals(gameKey)) {
            result = k3Service.processK3(gameKey, dataType, size);
        } else {
            result = process(gameKey, dataType, size);
        }
        return result;
    }


    private List<AnalysisResult> process(String gameKey, String dataType, Integer size) {
        List<AnalysisResult> result = null;
        List<Details> lotteryList = new ArrayList<>(); //存储需要统计的数据 (从数据库中获取)
        if ("near".equals(dataType)) {
            lotteryList = detailsDao.findByGamekey(gameKey, size);
        } else if ("yesterday".equals(dataType)) {
            lotteryList = detailsDao.findByDateAndGamekey(gameKey, -1);
        } else if ("yesterdaybefore".equals(dataType)) {
            lotteryList = detailsDao.findByDateAndGamekey(gameKey, -2);
        }
        if ("bjpk10".equals(gameKey) || "metpk10".equals(gameKey) || "1304".equals(gameKey)) {
            int[][] zero = new int[10][size]; // 存放本次查询冠军位纵向的0到9位出现的所有数字。
            int[][] one = new int[10][size];
            int[][] tow = new int[10][size];
            int[][] three = new int[10][size];
            int[][] four = new int[10][size];
            int[][] five = new int[10][size];
            int[][] six = new int[10][size];
            int[][] seven = new int[10][size];
            int[][] eight = new int[10][size];
            int[][] nine = new int[10][size];
            //数组初始化
            arrayInit(lotteryList, zero, one, tow, three, four, five, six, seven, eight, nine);
            //获取统计结果
            result = resultAdd(gameKey, size, zero, one, tow, three, four, five, six, seven, eight, nine);
        } else if ("ssc".equals(gameKey) || "1009".equals(gameKey) || "1008".equals(gameKey)) {
            int[][] zero = new int[10][size]; // 存放本次查询冠军位纵向的0到9位出现的所有数字。
            int[][] one = new int[10][size];
            int[][] tow = new int[10][size];
            int[][] three = new int[10][size];
            int[][] four = new int[10][size];
            //数组初始化
            arrayInit(lotteryList, zero, one, tow, three, four);
            //获取统计结果
            result = resultAdd(gameKey, size, zero, one, tow, three, four);
        }
        return result;
    }

    private List<AnalysisResult> resultAdd(String gameKey, Integer size, int[][] zero, int[][] one, int[][] tow, int[][] three, int[][] four, int[][] five, int[][] six, int[][] seven, int[][] eight, int[][] nine) {
        List<AnalysisResult> result = new ArrayList<>();
        result.add(GameUtil.getResult(GameUtil.disposeOne(zero, 0, size, gameKey), 0 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(one, 1, size, gameKey), 1 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(tow, 2, size, gameKey), 2 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(three, 3, size, gameKey), 3 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(four, 4, size, gameKey), 4 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(five, 5, size, gameKey), 5 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(six, 6, size, gameKey), 6 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(seven, 7, size, gameKey), 7 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(eight, 8, size, gameKey), 8 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(nine, 9, size, gameKey), 9 + "", gameKey));
        return result;
    }

    private List<AnalysisResult> resultAdd(String gameKey, Integer size, int[][] zero, int[][] one, int[][] tow, int[][] three, int[][] four) {
        List<AnalysisResult> result = new ArrayList<>();
        result.add(GameUtil.getResult(GameUtil.disposeOne(zero, 0, size, gameKey), 0 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(one, 1, size, gameKey), 1 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(tow, 2, size, gameKey), 2 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(three, 3, size, gameKey), 3 + "", gameKey));
        result.add(GameUtil.getResult(GameUtil.disposeOne(four, 4, size, gameKey), 4 + "", gameKey));
        return result;
    }

    private void arrayInit(List<Details> lotteryList, int[][] zero, int[][] one, int[][] tow, int[][] three, int[][] four, int[][] five, int[][] six, int[][] seven, int[][] eight, int[][] nine) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < lotteryList.size(); j++) {
                Details details = lotteryList.get(j);
                int[] zeros = CommonUtils.getIntOfAward(details.getZero());
                int[] ones = CommonUtils.getIntOfAward(details.getOne());
                int[] tows = CommonUtils.getIntOfAward(details.getTow());
                zero[i][j] = zeros[i];
                one[i][j] = ones[i];
                tow[i][j] = tows[i];
                if (details.getThree() != null) {
                    int[] threes = CommonUtils.getIntOfAward(details.getThree());
                    int[] fours = CommonUtils.getIntOfAward(details.getFour());
                    three[i][j] = threes[i];
                    four[i][j] = fours[i];
                }
                if (details.getFive() != null) {
                    int[] fives = CommonUtils.getIntOfAward(details.getFive());
                    int[] sixs = CommonUtils.getIntOfAward(details.getSix());
                    int[] sevens = CommonUtils.getIntOfAward(details.getSeven());
                    int[] eights = CommonUtils.getIntOfAward(details.getEight());
                    int[] nines = CommonUtils.getIntOfAward(details.getNine());
                    five[i][j] = fives[i];
                    six[i][j] = sixs[i];
                    seven[i][j] = sevens[i];
                    eight[i][j] = eights[i];
                    nine[i][j] = nines[i];
                }
            }
        }
    }

    private void arrayInit(List<Details> lotteryList, int[][] zero, int[][] one, int[][] tow, int[][] three, int[][] four) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < lotteryList.size(); j++) {
                Details details = lotteryList.get(j);
                int[] zeros = CommonUtils.getIntOfAward(details.getZero());
                int[] ones = CommonUtils.getIntOfAward(details.getOne());
                int[] tows = CommonUtils.getIntOfAward(details.getTow());
                int[] threes = CommonUtils.getIntOfAward(details.getThree());
                int[] fours = CommonUtils.getIntOfAward(details.getFour());
                zero[i][j] = zeros[i];
                one[i][j] = ones[i];
                tow[i][j] = tows[i];
                three[i][j] = threes[i];
                four[i][j] = fours[i];
            }
        }
    }


    public Page<Details> findDetailsList(String gameKey, String dateType, int page, int size) {
        if ("today".equals(dateType)) {
            return findToday(gameKey, page, size);
        } else if ("yesterday".equals(dateType)) {
            return findYester(gameKey, page, size);
        } else if ("yesterdayBefore".equals(dateType)) {
            return findYesterB(gameKey, page, size);
        }
        return null;
    }

    private Page<Details> findToday(String gameKey, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return detailsDao.findToday(gameKey, pageRequest);
    }

    private Page<Details> findYester(String gameKey, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return detailsDao.findYesterDay(gameKey, pageRequest);
    }

    private Page<Details> findYesterB(String gameKey, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return detailsDao.findBeforeYesterDay(gameKey, pageRequest);
    }
}
