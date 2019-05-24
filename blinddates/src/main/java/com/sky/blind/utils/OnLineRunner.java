package com.sky.blind.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Random;

@Component
public class OnLineRunner implements CommandLineRunner {
    public static Integer ONLINE = 112031;

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int m = Calendar.getInstance().get(Calendar.MINUTE);
            if (18 == h && m < 6) {
                ONLINE = 112031;
            }
            if (h > 1 && h < 7 && m < 6) {
                ONLINE = 10000;
            }
            Random random = new Random();
            OnLineRunner.ONLINE += random.nextInt(30) + 10;
            Thread.sleep(1000 * 3);
        }
    }
}
