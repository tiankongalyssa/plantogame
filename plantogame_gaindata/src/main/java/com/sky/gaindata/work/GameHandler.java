package com.sky.gaindata.work;

import com.sky.gaindata.pojo.LotteryRecord;
import com.sky.gaindata.pojo.PlanCreate;
import utils.CommonUtils;

import java.text.DecimalFormat;
import java.util.*;

public class GameHandler extends BaseWork {

    /**
     * 记录最大遗漏
     * <p>
     * 将strNum的第goalNum位数字修改为goalNum，beforeGoalNum位数字修改为1
     *
     * @param strNum        0,0,0,0,0,0,0,0,0,0格式字符串
     * @param beforeGoalNum 上次目标数字
     * @param goalNum       目标数字
     * @return String
     */
    static String changMaxMiss(String strNum, String beforeGoalNum, String goalNum, String gameKey) {
        StringBuilder result = new StringBuilder();
        if (null == goalNum) {
            return null;
        }
        if (strNum == null) {  //判断前期记录格式
            if ("1407".equals(gameKey)) { //大发快3 只开6个数字
                strNum = "0,0,0,0,0,0";
            } else {
                strNum = "0,0,0,0,0,0,0,0,0,0";
            }
        }
        //转换为int   09,10,03,07,04,02,05,06,08,01
        String[] split = strNum.split(","); //前期数据拆分
        int[] data = new int[split.length]; // 前期数据转换为int数组
        for (int i = 0; i < data.length; i++) {
            data[i] = Integer.valueOf(split[i]);
        }
        //每位加1 并将当前期开奖数字存入对应位子
        int num;
        for (int i = 0; i < data.length; i++) {
            data[i] += 1; //将data的每位数字加1
        }
        num = Integer.valueOf(goalNum); //将开奖数字转换为int
        //不会出现0的彩种
        boolean b = "bjpk10".equals(gameKey) || "metpk10".equals(gameKey) || "1304".equals(gameKey) || "1407".equals(gameKey);
        //将上期数字重置为1
        if (beforeGoalNum != null) {
            Integer beforePlace = Integer.valueOf(beforeGoalNum);
            if (b) {
                data[beforePlace - 1] = 1;
            } else {
                data[beforePlace] = 1;
            }
        }
        //设置本期开奖数字
        try {
            if (b) {
                data[num - 1] = 0;
            } else {
                data[num] = 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (int aData : data) {
            result.append(aData).append(","); //拼接为"1,1,1,1,1,1,1,1,1,1,"格式
        }
        return result.substring(0, result.length() - 1); //将最后一个","去掉
    }

    /**
     * 获取3期开奖期号
     *
     * @param gid 当前期号
     * @return String[]
     */
    static String[] getThreeIssue(String gid) {
        String[] result = new String[3];
        String front = gid.substring(0, gid.length() - 3);
        String ending = gid.substring(gid.length() - 3);
        int num = Integer.valueOf(ending);
        for (int i = 0; i < 3; i++) {
            result[i] = front + String.format("%03d", num++);
        }
        return result;
    }

    /**
     * 获取万位好数字
     *
     * @param str str
     * @return String
     */
    static String getMyriaAward(String str) {
        return str.substring(0, str.indexOf(","));
    }

    /**
     * 获取5位随机数计划
     *
     * @return String
     */
    static String getPlane(String gameKey) {
        HashSet<String> integers = new HashSet<>();
        Random random = new Random();
        int e;
        //这3个彩种是0-9
        if ("ssc".equals(gameKey) || "1008".equals(gameKey) || "1009".equals(gameKey)) {
            while (integers.size() < 5) {
                integers.add(random.nextInt(10) + "");
            }
            //如果是大发快3 则只计划大小
        } else if ("1407".equals(gameKey)) {
            boolean b = random.nextInt(100) % 2 == 0;
            integers.add(b ? "大" : "小");
        } else {
            while (integers.size() < 5) {
                e = random.nextInt(11);
                if (e == 0) {
                    continue;
                }
                integers.add(String.format("%02d", e));
            }
        }
        String result;
        StringBuilder str = new StringBuilder();
        if (!"1407".equals(gameKey)) {
            for (String integer : integers) {
                str.append(integer).append(",");
            }
            result = str.substring(0, str.length() - 1);
        } else {
            result = integers.iterator().next();
        }
        return result;
    }


    /**
     * 计划猜中机率
     *
     * @param countToday 当天总计划数
     * @param countWin   当前猜对计划数
     * @return Integer
     */
    static Integer getProbatilityNum(Double countToday, Double countWin) {
        DecimalFormat format = new DecimalFormat("#00");
        Integer integer = null;
        if (countToday != 0.0 && countWin != 0.0) {
            integer = Integer.valueOf(format.format((countWin / countToday) * 100));
        }
        return integer;
    }

    /**
     * 检查本期万位号码是否猜中
     * 传入本期期号,万位号码,正在进行的计划.
     * 1. 检查本期属于计划中的期号. win:1,2,3 表示3期排序  0表示 3期均未中
     * 2. 将计划中的数字转换成int数组类型
     * 3. 将int数组元素与本期开奖号码比较,检查是否猜中.
     *
     * @param gid   本期期号
     * @param award 本期万位号码
     * @param plan  进行中的计划
     */
    static Map<String, Object> checkWin(String gameKey, String gid, int award, PlanCreate plan) {
        Map<String, Object> map = new HashMap<>();
        //检查当前期是否在三期内，如果是分别为1，2，3如果不是 则为0
        int win = gid.equals(plan.getGidFirst()) ? 1 : gid.equals(plan.getGidSecond()) ? 2 : 3;
        boolean flag = false;
        if ("1407".equals(gameKey)) {
            String str = award > 10 ? "大" : "小";
            flag = plan.getMyriaPlane().equals(str);
        } else {
            //万位计划数组
            int[] myriaPlans = CommonUtils.getIntOfAward(plan.getMyriaPlane());
            for (int myriaPlan : myriaPlans) {
                //如果计划中的数字与开奖万位数字相同则猜中
                if (myriaPlan == award) {
                    flag = true;
                    break;
                }
            }
        }
        map.put("flag", flag);
        map.put("win", win);
        return map;
    }

    static String[] getGuanYa(LotteryRecord lottery) {
        String[] result;
        if ("ssc".equals(lottery.getGamekey()) || "1008".equals(lottery.getGamekey()) || "1009".equals(lottery.getGamekey()) || "1407".equals(lottery.getGamekey())) {
            result = new String[17];
            int[] award = CommonUtils.getIntOfAward(lottery.getAward());
            int total = CommonUtils.getTotal(award);
            //总和
            result[0] = total + "";
            result[2] = getOdd(total); //单双
            //大发快3 没有只位开奖数字
            if ("1407".equals(lottery.getGamekey())) {
                result[1] = getSize(total, 10); //大小    大发快3 总和与10相比
                result[3] = getSize(award[0], 5); //大小
                result[4] = getSize(award[1], 5); //大小
                result[5] = getSize(award[2], 5); //大小
                result[6] = getOdd(award[0]); //单双
                result[7] = getOdd(award[1]); //单双
                result[8] = getOdd(award[2]); //单双
                result[9] = getStraight(award[0], award[1], award[2]); //前三
            } else {
                result[1] = getSize(total, 5); //大小  其余的都是与5相比
                result[3] = getLongHumsg(award[0], award[4]); //龙虎
                //大小,单双
                for (int i = 0; i < award.length; i++) {
                    result[4 + i] = getSize(award[i], 5); //大小  4-8
                    result[9 + i] = getOdd(award[i]);// 单双 9-13
                }
                //豹子、对子、顺子  14-16
                int length = result.length;
                for (int i = 0; i < 3; i++) {
                    result[length - 3 + i] = getStraight(award[i], award[i + 1], award[i + 2]);
                }
            }
        } else {
            result = new String[8];
            int[] award = CommonUtils.getIntOfAward(lottery.getAward());
            //总和
            result[0] = award[0] + award[1] + "";
            //大小
            result[1] = getSize(award[0] + award[1], 12);
            //单双
            result[2] = (award[0] + award[1]) % 2 == 0 ? "双" : "单";
            //龙虎
            for (int i = 0; i < 5; i++) {
                result[3 + i] = getLongHumsg(award[i], award[9 - i]);
            }
        }
        return result;
    }

    private static String getStraight(int n1, int n2, int n3) {
        List<Integer> list = new ArrayList<>();
        list.add(n1);
        list.add(n2);
        list.add(n3);
        Collections.sort(list);
        boolean b1, b2, b3, b4;
        n1 = list.get(0);
        n2 = list.get(1);
        n3 = list.get(2);
        b1 = n1 == n2 && n1 == n3; //豹子
        b2 = n1 == n2 || n2 == n3 || n3 == n1; //对子
        b3 = n1 + 1 == n2 && n2 + 1 == n3 || n2 + 1 == n3 && n3 + 1 == n2 || n3 + 1 == n1 && n1 + 1 == n3; //顺子
        b4 = n1 + 1 == n2 || n2 + 1 == n3 || n3 + 1 == n1; //半顺
        return b1 ? "豹子" : b2 ? "对子" : b3 ? "顺子" : b4 ? "半顺" : "杂六";
    }

    private static String getOdd(int total) {
        return total % 2 == 0 ? "双" : "单";
    }

    private static String getSize(int total, int num) {
        return total > num ? "大" : "小";
    }


    private static String getLongHumsg(int n1, int n2) {
        return n2 == n1 ? "和" : n1 > n2 ? "龙" : "虎";
    }
}