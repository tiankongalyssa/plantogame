package com.sky.plantogame.service;

import com.sky.plantogame.dao.K3Dao;
import com.sky.plantogame.pojo.K3;
import com.sky.plantogame.utils.GameUtil;
import com.sky.plantogame.vo.Analysis;
import com.sky.plantogame.vo.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class K3Service {
    private final K3Dao k3Dao;

    @Autowired
    public K3Service(K3Dao k3Dao) {
        this.k3Dao = k3Dao;
    }

    private Page<K3> findToday(String gameKey, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return k3Dao.findToday(gameKey, pageRequest);
    }

    private Page<K3> findYester(String gameKey, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return k3Dao.findYesterDay(gameKey, pageRequest);
    }

    private Page<K3> findYesterB(String gameKey, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<K3> yesterDayBefor = k3Dao.findYesterDayBefor(gameKey, pageRequest);
        return yesterDayBefor;
    }

    public Page<K3> findK3List(String gameKey, String dateType, int page, int size) {
        if ("today".equals(dateType)) {
            return findToday(gameKey, page, size);
        } else if ("yesterday".equals(dateType)) {
            return findYester(gameKey, page, size);
        } else if ("yesterdayBefore".equals(dateType)) {
            return findYesterB(gameKey, page, size);
        }
        return null;
    }

    /**
     * 统计:总出现次数,平均遗漏,最大遗漏,最大连出
     * 1. 根据dataType类型查询size期数据放到List集合
     * 2. 将size期需要统计的数据切分到int[][]二维数组
     * 3. 对二维数组的数据进行分析比较找出总出现次数,平均遗漏,最大遗漏,最大连出
     * 4. 对本次统计进行封装返回
     *
     * @param gameKey
     * @param dateType
     * @param size
     */
    List<AnalysisResult> processK3(String gameKey, String dateType, Integer size) {
        List<AnalysisResult> result = new ArrayList<>();
        List<K3> k3List;
        if ("near".equals(dateType)) {
            k3List = k3Dao.findTodayList(gameKey, size);
        } else if ("yesterday".equals(dateType)) {
            k3List = k3Dao.findYesterDayList(gameKey, size);
        } else {
            k3List = k3Dao.findYesterDayBeforList(gameKey, size);
        }
        int[][] numTrendArray = new int[6][size];
        int[][] summationArray = new int[16][size];
        int[][] sumFormArray = new int[4][size];
        int[][] numFormArray = new int[6][size];
        //初始化 号码走势和号码形态
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < k3List.size(); j++) {
                K3 k3 = k3List.get(j);
                int[] numTrend = CommonUtils.getIntOfAward(k3.getNumTrend());
                numTrendArray[i][j] = numTrend[i];
            }
        }

//      号码走势统计结果
        result.add(GameUtil.getResult(GameUtil.disposeOne(numTrendArray, 1, size, gameKey), 1 + "", gameKey));
        //和值形态
        result.add(GameUtil.getResult(summationHandler(gameKey, size, k3List, summationArray, 2), 2 + "", gameKey));

        //初始化处理和值组合形态
        result.add(GameUtil.getResult(sumFormHandler(gameKey, size, k3List, sumFormArray, 3), 3 + "", gameKey));
//      号码形态统计结果
//        List<Analysis> analyses4 = GameUtil.disposeOne(numFormArray, 4, size, gameKey);
        return result;
    }

    //和值处理
    private List<Analysis> summationHandler(String gameKey, Integer size, List<K3> k3List, int[][] formarray, int place) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < k3List.size(); j++) {
                K3 k3 = k3List.get(j);
                int[] sumForm = CommonUtils.getIntOfAward(k3.getSummation());
                formarray[i][j] = sumForm[i];
            }
        }
        // 处理和值形态
        return GameUtil.disposeOne(formarray, place, size, 16, gameKey);
    }

    private List<Analysis> sumFormHandler(String gameKey, Integer size, List<K3> k3List, int[][] formarray, int place) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < k3List.size(); j++) {
                K3 k3 = k3List.get(j);
                int[] sumForm = CommonUtils.getIntOfAward(k3.getSumForm());
                formarray[i][j] = sumForm[i];
            }
        }

        // 处理和值形态
        List<Analysis> analyses2 = GameUtil.disposeOne(formarray, place, size, 4, gameKey);

        return analyses2;
    }
}
