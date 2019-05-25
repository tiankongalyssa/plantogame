package com.sky.blind.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;

@Component
public class OnLineRunner implements CommandLineRunner {
    public static Integer onLineNumber = 120000;
    private boolean isAdd = true;

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();
        while (true) {
            int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int m = Calendar.getInstance().get(Calendar.MINUTE);
            int s = Calendar.getInstance().get(Calendar.SECOND);
            if (5 == h && m == 1 && s < 5) { //每天5点 重新计算
                onLineNumber = (random.nextInt(7000) + 1000) + 118000;
            }
            if (onLineNumber < 120000) {
                isAdd = true;
            }
            if (onLineNumber > 500000) {
                isAdd = false;
            }
            if (isAdd) { //如果小于50w就增加
                onLineNumber += random.nextInt(10) + 10; //每秒 添加10-20人
            } else {
                onLineNumber -= random.nextInt(50) + 10; //每秒 减小50-60人
            }
            Thread.sleep(1000);
        }
    }
}
