package com.sky.gaindata.work;

import java.util.ArrayList;
import java.util.List;

public class BaseWork {
    protected static List<String> urlList = new ArrayList<>();

    // 如果是x78的彩种都在这里添加一个信息
    {
        urlList.add("1008");
        urlList.add("1009");
        urlList.add("1304");
        urlList.add("1306");
        urlList.add("1407");
    }
}
