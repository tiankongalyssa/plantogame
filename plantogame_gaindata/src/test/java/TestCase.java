//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import utils.CommonUtils;
//
//import java.text.DecimalFormat;
//import java.util.*;
//
//@SpringBootTest(classes = TestCase.class)
//@RunWith(SpringRunner.class)
//public class TestCase {
//    private static Map<String, Integer> beforMap = new HashMap();  //记录上期开奖位数
//
//    public static void main(String[] args) throws InterruptedException {
//        String str = "0,0,0,0,0,0,0,0,0,0";
//        System.out.println(str.length());
//        beforMap.put("bjpk10", 2);
//        while (true) {
//            int num = new Random().nextInt(11);
//            System.out.println("num = " + num);
//            str = TestCase.numAddOne("bjpk10", str, num + "");
//            System.out.println("str = " + str);
//            Thread.sleep(100);
//        }
//    }
//
//    /**
//     * 开奖数字出现记录
//     *
//     * @param beforNumber 传入上期记录
//     * @param strNum      传入本期开奖数字
//     * @return 返回 1,1,1,1,1,1,1,1,1,1 格式的开奖记录
//     * <p>
//     * 说明出现当前开奖数字了对应位数就重置为1 连续多少期没开该数字则会一直往上加。
//     */
//    private static String numAddOne(String gamekey, String beforNumber, String strNum) {
//        if (beforNumber == null || beforNumber.length() < 19) {  //判断前期记录格式
//            beforNumber = "0,0,0,0,0,0,0,0,0,0";
//        }
//        String result = "";
//
//        //转换为int
//
//        String[] split = beforNumber.split(","); //前期数据拆分
//        int[] data = new int[split.length]; // 前期数据转换为int数组
//        for (int i = 0; i < data.length; i++) {
//            data[i] = Integer.valueOf(split[i]); //将string类型的数字转换为int
//            if ("10".equals(split[i])) { //如果是10就放到data的最后一位
//                data[data.length - 1] = Integer.valueOf(split[i]);
//            }
//            if ("01".equals(split[i])) { //如果是01 就放到data的第一位
//                data[0] = Integer.valueOf(split[i]);
//            }
//        }
//
//        //每位加1 并将当前期开奖数字存入对应位子
//
//        int num = 0;
//        for (int i = 0; i < data.length; i++) {
//            data[i] += 1; //将data的每位数字加1
//            num = Integer.valueOf(strNum); //将开奖数字转换为int
//            try {
//                data[num] = num;
//            } catch (Exception e) {
//                if ("10".equals(strNum)) {
//                    data[data.length - 1] = 10; //如果是“10”将10放到data最后一位
//                    num = 9;
//                    data[num] = num; //将当前期开奖数字存入对应位子
//                    data[num] = 10;
//                } else if ("01".equals(strNum)) {
//                    num = 0;
//                    data[num] = num;//将当前期开奖数字存入对应位子
//                    data[0] = 1;    //如果是"01"，将1放到data第一位
//                }
//            }
//        }
//        data[beforMap.get(gamekey)] = 1; //将上期的num位重置为1
//        beforMap.put(gamekey, num); //将本期gamekey彩种的开奖号保存，用于一次该位数字重置为1
//
//        for (int i = 0; i < data.length; i++) {
//            result += data[i] + ","; //拼接为"1,1,1,1,1,1,1,1,1,1,"格式
//        }
//        return result.substring(0, result.length() - 1); //将最后一个","去掉
//    }
//
//
//    @Test
//    public void test() {
//        System.out.println(System.currentTimeMillis());
//        System.out.println(new Date().getTime());
//    }
//
//    @Test
//    public void test2() {
//        List<Long> longs = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            longs.add(new Random().nextLong());
//        }
//        for (Long l : longs) {
//            System.out.println(l);
//        }
//        Collections.sort(longs);
//        System.out.println("--------------");
//        for (Long l : longs) {
//            System.out.println(l);
//        }
//    }
//
//    @Test
//    public void test3() {
//        HashSet<Integer> integers = new HashSet<>();
//        while (integers.size() < 5) {
//            integers.add(new Random().nextInt(10));
//        }
//        String str = "";
//        Iterator<Integer> iterator = integers.iterator();
//        while (iterator.hasNext()) {
//            str += iterator.next() + ",";
//        }
//        System.out.println(str.substring(0, str.length() - 1));
//    }
//
//    @Test
//    public void test4() {
//        Integer result = 0;
//        String plane = "1,4,2,3,6";
//        String award = "3";
//        String[] split = plane.split(",");
//        int[] numSplit = new int[split.length];
//        for (int i = 0; i < split.length; i++) {
//            numSplit[i] = Integer.valueOf(split[i]);
//        }
//        for (int i = 0; i < split.length; i++) {
//            if (Integer.valueOf(award) == numSplit[i]) {
//                result = 1;
//            }
//        }
//        System.out.println(result);
//    }
//
//    @Test
//    public void test5() {
//        String[] result = new String[3];
//        String str = "731472";
//        String front = str.substring(0, str.length() - 3);
//        System.out.println("front = " + front);
//        String ending = str.substring(str.length() - 3);
//        System.out.println("ending = " + ending);
//        int num = Integer.valueOf(ending);
//        for (int i = 0; i < 2; i++) {
//            result[i] = front + String.format("%03d", ++num);
//
//        }
//    }
//
//    @Test
//    public void test6() {
//        String gid = "731476";
//        String[] result = new String[3];
//        String front = gid.substring(0, gid.length() - 3);
//        String ending = gid.substring(gid.length() - 3);
//        int num = Integer.valueOf(ending);
//        for (int i = 0; i < 3; i++) {
//            result[i] = front + String.format("%03d", num++);
//        }
//
//        for (String str : result) {
//            System.out.println(str);
//        }
//    }
//
//    @Test
//    public void test7() {
//        double a = 1.0;
//        double b = 3.0;
//        double v = (a / b) * 100;
//        DecimalFormat format = new DecimalFormat("#00");
//        System.out.println(Double.valueOf(format.format(v)));
//    }
//
//    @Test
//    public void test8() {
//        String[] result = new String[8];
//        int[] award = getIntOfAward("09,09,01,08,05,07,10,02,06,04");
//        result[0] = award[0] + award[1] + "";
//        result[1] = award[0] + award[1] > 12 ? "大" : award[0] + award[1] < 12 ? "小" : "合";
//        result[2] = (award[0] + award[1]) % 2 == 0 ? "双" : "单";
//        result[3] = getLongHumsg(award[0], award[9]);
//        result[4] = getLongHumsg(award[1], award[8]);
//        result[5] = getLongHumsg(award[2], award[7]);
//        result[6] = getLongHumsg(award[3], award[6]);
//        result[7] = getLongHumsg(award[4], award[5]);
//        for (String string : result) {
//            System.out.println(string);
//        }
//    }
//
//
//    private String getLongHumsg(int num1, int num2) {
//        return num1 > num2 ? "龙" : num1 < num2 ? "虎" : "合";
//    }
//
//    @Test
//    public void test9() {
//        String msg = 21231 % 2 == 0 ? "双" : "单";
//        System.out.println(msg);
//    }
//
//    private int[] getIntOfAward(String data) {
//        String[] split = data.split(",");
//        int[] result = new int[split.length];
//        for (int i = 0; i < split.length; i++) {
//            result[i] = Integer.valueOf(split[i]);
//        }
//        return result;
//    }
//
//    @Test
//    public void test10() {
//        for (int i = 0; i < 1; i++) {
//            HashSet<String> integers = new HashSet<>();
//            int e = 1;
//            while (integers.size() < 5) {
//                e = new Random().nextInt(11);
//                if (e == 0) {
//                    continue;
//                }
//                integers.add(String.format("%02d", e));
//            }
//            String str = "";
//            Iterator<String> iterator = integers.iterator();
//            while (iterator.hasNext()) {
//                str += iterator.next() + ",";
//            }
//            System.out.println(str.substring(0, str.length() - 1));
//        }
//    }
//
//    @Test
//    public void test11() {
//        Runnable runnable = () -> {
//            for (int i = 0; i < 10; i++) {
//                System.out.println(i);
//            }
//        };
//        new Thread(runnable).start();
//
//    }
//
//    @Test
//    public void test12() {
//        for (int j = 0; j < 100; j++) {
//            int[] ints = new int[3];
//            for (int i = 0; i < ints.length; i++) {
//                ints[i] = new Random().nextInt(10);
//            }
//
//            for (int i : ints) {
//                System.out.print(i + " ");
//            }
//            System.out.println(getStraight(ints[0], ints[1], ints[2]));
//        }
//
//    }
//
//    public String getStraight(int n1, int n2, int n3) {
//        List<Integer> list = new ArrayList<>();
//        list.add(n1);
//        list.add(n2);
//        list.add(n3);
//        Collections.sort(list);
//        boolean b1, b2, b3, b4;
//        n1 = list.get(0);
//        n2 = list.get(1);
//        n3 = list.get(2);
//        b1 = n1 == n2 && n1 == n3; //豹子
//        b2 = n1 == n2 || n2 == n3 || n3 == n1; //对子
//        b3 = n1 + 1 == n2 && n2 + 1 == n3 || n2 + 1 == n3 && n3 + 1 == n2 || n3 + 1 == n1 && n1 + 1 == n3; //顺子
//        b4 = n1 + 1 == n2 || n2 + 1 == n3 || n3 + 1 == n1; //半顺
//        return b1 ? "豹子" : b2 ? "对子" : b3 ? "顺子" : b4 ? "半顺" : "杂六";
//    }
//
//    @Test
//    public void test13() {
//        Date openTime = new Date();
//        System.out.println("openTime = " + openTime);
//        Date nextOpenTime = getNextOpenTime(openTime, 1009 + "");
//        System.out.println("nextOpenTime = " + nextOpenTime);
//    }
//
//    private Date getNextOpenTime(Date openTime, String lotteryCode) {
//        List<String> every5minutes = new ArrayList<>();
//        every5minutes.add("1009");
////        List<String> every1minutes = new ArrayList<>();
////        every1minutes.add("1008");
////        every1minutes.add("1304");
////        every1minutes.add("1407");
//        int num = every5minutes.contains(lotteryCode) ? 5 : 1;
//        return new Date(openTime.getTime() + 1000 * 60 * num);
//    }
//
//    String before = "";
//
//    @Test
//    public void test14() {
//        for (int j=0;j<1000;j++) {
//            StringBuilder builder = new StringBuilder();
//            while (builder.length() < 6) {
//                int i = new Random().nextInt(7);
//                if (i == 0) {
//                    continue;
//                }
//                builder.append(i).append(",");
//            }
//            String x = builder.toString();
//            String substring = x.substring(0, x.length() - 1);
//            System.out.println("本期开奖数字:"+substring);
//            String numTrend = getNumTrend(TestCase.B_record, TestCase.BEFORAWARD, substring, 6);
//            System.out.println("本期记录更新:"+ numTrend);
//            System.out.println("----------");
//        }
//    }
//    private static String B_record;
//    private static  String BEFORAWARD = "1,2,3";
//    private String getNumTrend(String beforRecord, String beforAward, String award, int num) {
//
//        //返回   3,6@2   1@2,5   3,4,5
//        //        1,1,3,1,1,6@2
//
//        int[] beforArray;
//        if ("".equals(beforRecord) || beforRecord == null) {
//            beforArray = new int[num];
//        }else{
//            beforArray = CommonUtils.getIntOfAward(beforRecord);
//        }
//        //每位数字加1
////        System.out.println("每位数字加1");
//        for (int i = 0; i < beforArray.length; i++) {
//            beforArray[i]++;
//        }
////        System.out.println(Arrays.toString(beforArray));
//        //上期开奖位置重置为1
////        System.out.println("上期开奖位置重置为1   上期开奖号码为:"+TestCase.BEFORAWARD);
//        int[] bPlaceNum = CommonUtils.getIntOfAward(beforAward);
//        for (int aPlaceNum : bPlaceNum) {
//            beforArray[aPlaceNum-1] = 1;
//        }
////        System.out.println(Arrays.toString(beforArray));
//        //本期开奖数字赋值
////        System.out.println("本期开奖数字赋值:"+award);
//        int[] awardNum = CommonUtils.getIntOfAward(award);
//        for (int n : awardNum) {
//            beforArray[n - 1] = n;
//        }
////        System.out.println(Arrays.toString(beforArray));
//        //判断本期开奖数字有无重复
////        System.out.println("判断本期开奖数字有无重复");
//        int repetition = -1;
//        int count = 1;
//        for (int i = 0; i < awardNum.length - 1; i++) {
//            for (int j = i + 1; j < awardNum.length; j++) {
//                if (awardNum[i] == awardNum[j] && (i != j)) {
//                    repetition = awardNum[i];
//                    count++;
//                }
//            }
//        }
//        //封装成str
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < beforArray.length; i++) {
//            if (repetition != -1 && repetition - 1 == i) {
//                builder.append(beforArray[i]).append("@").append(awardNum[0]==awardNum[1] &&awardNum[1]==awardNum[2]?3:count).append(",");
//            }else{
//                builder.append(beforArray[i]).append(",");
//            }
//        }
//        String result = builder.substring(0, builder.length() - 1);
//        TestCase.B_record  = result;
//        TestCase.BEFORAWARD =award;
//        return result;
//    }
//
//    @Test
//    public void test15() {
//        String str = "1,1@3,3,1,1,6@2";
//        String[] split = str.split(",");
//        for (int i = 0; i < split.length; i++) {
//            String s = split[i];
//            if (s.contains("@")) {
//                split[i] = s.substring(0, 1);
//                break;
//            }
//        }
//        System.out.println("----------");
//        for (String s2 : split) {
//            System.out.println(s2);
//        }
//    }
//}
