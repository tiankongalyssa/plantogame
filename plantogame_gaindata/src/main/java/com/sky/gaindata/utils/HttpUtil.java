package com.sky.gaindata.utils;

import info.BaseWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil extends BaseWork {
    private static Map<String, Integer> flagmap = new HashMap<>();

    /**
     * 请求接口数据
     * 如果游戏名在urlList里面则请求x78cc的接口,否则请求之前的接口
     *
     * @param lottCode 游戏名
     * @param rows     请求行数
     * @return json数据
     * @throws IOException IOException
     */
    public synchronized static String getForeignData(String lottCode, int rows) throws IOException {
        //如果是大发接口  睡眠3.2秒再请求
        if (dfInterfaceList.contains(lottCode)) {
            try {
                Thread.sleep(3200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String uri;
        if (x78InterfaceList.contains(lottCode)) {
            uri = "http://www.x78cc.com/v1/lottery/openResult?lotteryCode=" + lottCode + "&dataNum=" + rows + "&";
        } else if (dfInterfaceList.contains(lottCode)) {
            //测试
            // uri = "http://abawardopen.com/newly.do?token=7xEW2FXNQaMddAAmqocV&rows=" + rows + "&format=json&code=" + lottCode;
            //线上
            uri = "http://abawardopen.com/newly.do?token=jLYGmFykCSDn8tTYU7CW&rows=" + rows + "&format=json&code=" + lottCode;
        } else {
            uri = "http://api.caipiaoapi.com/hall/nodeService/api_request?gamekey=" + lottCode + "&count=" + rows + "&uid=330&time=1540179556&md5=f7ab80d949a4e60bcd1590c31060b6d4&api=apiGameInfo&site=api.jiekouapi.com&tdsourcetag=s_pcqq_aiomsg";
        }
        //网络的url地址
        URLConnection conn;
        //输入流
        BufferedReader in = null;
        String str = null;
        URL url = new URL(uri);
        try {
            while ((str == null) || str.matches(".*openTime.*") & str.matches(".*opentime.*")) {
                url.openConnection();
                conn = url.openConnection();
//                conn.addRequestProperty("api-token","TdPBrjxzpL+IFtIAv8q3Wp+IoROQNdD/wtmyABjbha4="); //添加消息头  使用php接口时添加
//                conn.addRequestProperty("api-key","kuyun@olmail.org");
                try {
                    //计数错误次数:
                    Integer integer = flagmap.get(lottCode);
                    flagmap.put(lottCode, (flagmap.get(lottCode) == null ? 0 : integer) + 1);
                    Integer flagCount = flagmap.get(lottCode);
                    if (flagCount > 3) {
                        flagmap.put(lottCode, 0);
                        return null;
                    }
                    System.out.println(uri);
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    str = in.readLine();
                    if (str == null) {
                        System.out.println("读取数据为空");
                        return null;
                    }
                    if (!str.matches(".*opentime.*") & !str.matches(".*openTime.*")) { //如果str不包含gid则说明数据返回失败.
                        System.out.println(lottCode + " has error");
                        int count = 0;
                        while (count < 5) {
                            Thread.sleep(1000);
                            System.out.println("Error wait" + count + "s");
                            count++;
                        }
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                    System.out.println("连接异常");
                    return null;
                }
            }
        } catch (IOException e) {
            System.out.println("连接异常或返回数据不错误");
            e.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        flagmap.put(lottCode, 0);
        return str;
    }
}
