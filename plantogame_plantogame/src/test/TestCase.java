import com.sky.plantogame.vo.HotCool;
import com.sky.plantogame.vo.Num;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest(classes = TestCase.class)
@RunWith(SpringRunner.class)
public class TestCase {
    @Test
    public void test() {
        try {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);// 分
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);// 秒
            c.add(Calendar.HOUR_OF_DAY, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(c.getTime());
            Date date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(30) + 65);
        }
    }

    @Test
    public void test3() {
        String gid = "731500";
        String front = gid.substring(0, gid.length() - 3);
        String ending = gid.substring(gid.length() - 3);
        int num = Integer.valueOf(ending);
        String result = front + String.format("%03d", num - 1);
        System.out.println(result);
    }

    @Test
    public void test4() {
        System.out.println(Integer.valueOf(String.format("%d", 30 / 2)));
    }

    @Test
    public void test5() {
        int num = 0;
        num = num == 0 ? 1 : num;
        System.out.println(20 / num);
    }

    @Test
    public void test6() {
        for (int j = 0; j < 100; j++) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                stringBuilder.append(new Random().nextInt(20)).append(",");
            }
            String string = stringBuilder.toString();
            String num = string.substring(0, string.length() - 2);
            //拆分
            String[] split = num.split(",");
            //转换
            List<Integer> integers = new ArrayList<>();
            List<Num> nums = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                Integer e = Integer.valueOf(split[i]);
                integers.add(e);
                nums.add(new Num(i, e));
            }
            //获取最大和最小
            Integer max = Collections.max(integers);
            Integer min = Collections.min(integers);

            List<Num> hot = new ArrayList<>();
            List<Num> warmth = new ArrayList<>();
            List<Num> cool = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                Num n = nums.get(i);
                if (n.getValue().equals(max)) {
                    hot.add(n);
                } else if (n.getValue() == min) {
                    cool.add(n);
                } else {
                    warmth.add(n);
                }
            }
            HotCool hotCool = new HotCool();
            hotCool.setHot(hot);
            hotCool.setWarmth(warmth);
            hotCool.setCool(cool);
            System.out.println(hotCool);
//            System.out.println("hot");
//            hot.forEach(System.out::println);
//            System.out.println("warmth");
//            warmth.forEach(System.out::println);
//            System.out.println("cool");
//            cool.forEach(System.out::println);
        }
    }

    @Test
    public void test7() {
//        String s = "2,1,0,4,3,2,1,0,1,0,0,2,1,0,2";
        String s = "200000104321010002102";
        int max = max(s);
        System.out.println(max);
    }
    public static int max(String s) {
        int max = 0, tmp_m = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                tmp_m++;
            } else {
                max = max > tmp_m ? max : tmp_m;
                tmp_m = 1;
            }
        }
        max = max > tmp_m ? max : tmp_m;//最后的连续数与最大连续的比较
        return max;
    }
    @Test
    public void test8(){
        int times = 9;
        System.out.println(8 * (int) Math.pow(3, (times - 3)));
    }
    @Test
    public void tes9(){

    }
}
