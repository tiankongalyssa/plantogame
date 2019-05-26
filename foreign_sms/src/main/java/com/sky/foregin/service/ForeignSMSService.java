package com.sky.foregin.service;

import com.sky.foregin.service.exception.CodeTimeOutException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import utils.SMSUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ForeignSMSService {
    @Autowired
    private RedisTemplate redisTemplate;

    public String send(String mobile) {
        System.out.println("mobile = " + mobile);
        Map<String, Object> data = (Map<String, Object>) redisTemplate.opsForValue().get("smsCode_" + mobile);
        if (data != null && data.get("code") != null && System.currentTimeMillis() < (long) data.get("time")) {
            throw new CodeTimeOutException("请1分钟后再试");
        }
        String checkCode = RandomStringUtils.randomNumeric(6);
        data = new HashMap();
        data.put("code", checkCode);
        data.put("time", System.currentTimeMillis() + 1000 * 60);
        SMSUtil.mobileQuery(mobile, checkCode, "160984");
        redisTemplate.opsForValue().set("smsCode_" + mobile, data, 2, TimeUnit.MINUTES);
        return "发送成功";
    }

    public String getCheckCode(String mobile) {
        Map<String, Object> data = (Map<String, Object>) redisTemplate.opsForValue().get("smsCode_" + mobile);
        if (data == null) {
            throw new CodeTimeOutException("请重新发送验证码");
        }
        return data.get("code").toString();
    }
}
