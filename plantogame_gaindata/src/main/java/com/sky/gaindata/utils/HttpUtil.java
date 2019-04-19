package com.sky.gaindata.utils;

import com.sky.gaindata.work.BaseWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HttpUtil extends BaseWork {

    public synchronized static String getForeignData(String lottCode, int rows) throws IOException {
        String uri;
        if (urlList.contains(lottCode)) {
            uri = "http://www.x78cc.com/v1/lottery/openResult?lotteryCode=" + lottCode + "&dataNum=" + rows + "&";
        } else {
            uri = "http://api.caipiaoapi.com/hall/nodeService/api_request?gamekey=" + lottCode + "&count=" + rows + "&uid=330&time=1540179556&md5=f7ab80d949a4e60bcd1590c31060b6d4&api=apiGameInfo&site=api.jiekouapi.com&tdsourcetag=s_pcqq_aiomsg";
        }
        //网络的url地址
        URLConnection conn;
        //输入流
        BufferedReader in = null;
        String str = null;
        rows = rows <= 0 ? 1 : rows;
//        String uri = "http://www.chatapi11.com/api/lottery?gamekey=" + lottCode + "&count=" + rows;
        URL url = new URL(uri);
        try {
            while ((str == null) || !str.matches(".*openTime.*")) {
                url.openConnection();
                conn = url.openConnection();
//                conn.addRequestProperty("api-token","TdPBrjxzpL+IFtIAv8q3Wp+IoROQNdD/wtmyABjbha4="); //添加消息头  使用php接口时添加
//                conn.addRequestProperty("api-key","kuyun@olmail.org");
                try {
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    str = in.readLine();
                    if (!str.matches(".*openTime.*")) { //如果str不包含gid则说明数据返回失败.
                        System.out.println(lottCode + "彩种有问题");
                        int count = 0;

                        while (count < 5) {
                            Thread.sleep(1000);
                            System.out.println("等待" + count + "秒");
                            count++;
                        }
                    }
                } catch (InterruptedException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            str = getForeignData(lottCode, rows);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return str;
    }
}