package com.sky.plantogame.utils;

import com.sky.plantogame.vo.Analysis;
import com.sky.plantogame.vo.AnalysisResult;
import info.BaseWork;

import java.util.ArrayList;
import java.util.List;

public class GameUtil extends BaseWork {
    /**
     * 统计某一位数字出现的情况
     *
     * @param ints    ints
     * @param place   place
     * @param size    size
     * @param gameKey gameKey
     * @return List<Analysis>
     */
    public static List<Analysis> disposeOne(int[][] ints, int place, Integer size, String gameKey) {
        List<Analysis> result = new ArrayList<>();
        int num = dfType.contains(gameKey) ? 6 : 10;
        int[][] array = new int[num][4];
        for (int i = 0; i < num; i++) {
            int max = 0;           //最大遗漏
            int maxSuccession = 0; //最大连续数
            int succession = 0;    //当前连续数 (如何一直连续则每次加1,否则重置为1)
            int appear = 0;        //出现次数
            for (int j = 0; j < ints[0].length; j++) {
                int now = ints[i][j]; //当前数字
                int before = 0;     //前一个数字
                if (j > 0) {
                    before = ints[i][j - 1];
                }
                if (now == before) { //如何当前数字和上一次数字相等则加1
                    succession++;
                } else {
                    //否则重置为1
                    maxSuccession = maxSuccession > succession ? maxSuccession : succession;
                    succession = 1;
                }
                max = max < now ? now : max;
                if (now == 0) {
                    appear++;
                }
            }
            maxSuccession = maxSuccession > succession ? maxSuccession : succession;
            array[i][0] = appear; //出现次数
            array[i][1] = maxSuccession == 1 ? 0 : maxSuccession; //连出次数
            array[i][2] = max; //最大遗漏
            int i1 = appear == 0 ? 1 : appear;
            array[i][3] = Integer.valueOf(String.format("%d", size / i1)); //平均遗漏
        }
        //存入集合
        for (int i = 0; i < num; i++) {
            int flag = dfType.contains(gameKey) ? i + 1 : i;
            Analysis analysis = new Analysis();
            analysis.setAppear(array[i][0]);
            analysis.setSuccession(array[i][1]);
            analysis.setMaxMiss(array[i][2]);
            analysis.setMeanMiss(array[i][3]);
            analysis.setNumber(flag);
            analysis.setGameKey(gameKey);
            analysis.setPlace(place);
            result.add(analysis);
        }
        return result;
    }

    /**
     * @param ints
     * @param place
     * @param size
     * @param num     位数大小  之前的方法是10个   此重载方法处理 4和 18的情况
     * @param gameKey
     * @return
     */
    public static List<Analysis> disposeOne(int[][] ints, int place, Integer size, Integer num, String gameKey) {
        List<Analysis> result = new ArrayList<>();
        int[][] array = new int[num][4];
        for (int i = 0; i < num; i++) {
            int max = 0;           //最大遗漏
            int maxSuccession = 0; //最大连续数
            int succession = 0;    //当前连续数 (如何一直连续则每次加1,否则重置为1)
            int appear = 0;        //出现次数
            for (int j = 0; j < ints[0].length; j++) {
                int now = ints[i][j]; //当前数字
                int before = 0;     //前一个数字
                if (j > 0) {
                    before = ints[i][j - 1];
                }
                if (now == before) { //如何当前数字和上一次数字相等则加1
                    succession++;
                } else {
                    //否则重置为1
                    maxSuccession = maxSuccession > succession ? maxSuccession : succession;
                    succession = 1;
                }
                max = max < now ? now : max;
                if (now == 0) {
                    appear++;
                }
            }
            maxSuccession = maxSuccession > succession ? maxSuccession : succession;
            array[i][0] = appear; //出现次数
            array[i][1] = maxSuccession == 1 ? 0 : maxSuccession; //连出次数
            array[i][2] = max; //最大遗漏
            int i1 = appear == 0 ? 1 : appear;
            array[i][3] = Integer.valueOf(String.format("%d", size / i1)); //平均遗漏
        }
        //存入集合
        for (int i = 0; i < num; i++) {
            Analysis analysis = new Analysis();
            analysis.setAppear(array[i][0]);
            analysis.setSuccession(array[i][1]);
            analysis.setMaxMiss(array[i][2]);
            analysis.setMeanMiss(array[i][3]);
            analysis.setNumber(i + 1);
            analysis.setGameKey(gameKey);
            analysis.setPlace(place);
            result.add(analysis);
        }
        return result;
    }

    public static String[] getStrings(String gameKey) {
        String[] msg;
        if ("ssc".equals(gameKey) || "1008".equals(gameKey) || "1009".equals(gameKey)) {
            msg = new String[]{"万位", "千位", "百位", "十位", "个位"};
        } else if ("1407".equals(gameKey)) {
            msg = new String[]{"大小", "单双"};
        } else {
            msg = new String[]{"冠军", "亚军", "季军", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"};
        }
        return msg;
    }

    /**
     * 获取指定位置的字符串
     *
     * @param msg String[]
     * @param i   指定位置
     * @return 对应位置的字符串
     */
    public static String getMsgFromSwitch(String[] msg, int i) {
        String str = "";
        switch (i) {
            case 0:
                str = msg[0];
                break;
            case 1:
                str = msg[1];
                break;
            case 2:
                str = msg[2];
                break;
            case 3:
                str = msg[3];
                break;
            case 4:
                str = msg[4];
                break;
            case 5:
                str = msg[5];
                break;
            case 6:
                str = msg[6];
                break;
            case 7:
                str = msg[7];
                break;
            case 8:
                str = msg[8];
                break;
            case 9:
                str = msg[9];
                break;
        }
        return str;
    }

    /**
     * 将统计结果进行封装
     *
     * @param data    data
     * @param place   place
     * @param gameKey gameKey
     * @return AnalysisResult
     */
    public static AnalysisResult getResult(List<Analysis> data, String place, String gameKey) {
        AnalysisResult analysisResult = new AnalysisResult();
        StringBuilder appear = new StringBuilder();
        StringBuilder maxMiss = new StringBuilder();
        StringBuilder meanMiss = new StringBuilder();
        StringBuilder succession = new StringBuilder();
        for (Analysis x : data) {
            appear.append(x.getAppear().toString()).append(",");
            maxMiss.append(x.getMaxMiss().toString()).append(",");
            meanMiss.append(x.getMeanMiss().toString()).append(",");
            succession.append(x.getSuccession().toString()).append(",");
        }
        analysisResult.setPlace(place);
        analysisResult.setGameKey(gameKey);
        analysisResult.setAppear(appear.substring(0, appear.length() - 1));
        analysisResult.setMaxMiss(maxMiss.substring(0, maxMiss.length() - 1));
        analysisResult.setMeanMiss(meanMiss.substring(0, meanMiss.length() - 1));
        analysisResult.setSuccession(succession.substring(0, succession.length() - 1));
        return analysisResult;
    }
}
