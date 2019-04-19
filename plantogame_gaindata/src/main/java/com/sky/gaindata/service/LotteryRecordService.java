package com.sky.gaindata.service;

import com.sky.gaindata.dao.LotteryRecordDao;
import com.sky.gaindata.pojo.LotteryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.Date;

/**
 * lotteryRecord服务层
 *
 * @author Administrator
 */
@Service
public class LotteryRecordService {

    private final LotteryRecordDao lotteryRecordDao;
    private final IdWorker idWorker;

    @Autowired
    public LotteryRecordService(LotteryRecordDao lotteryRecordDao, IdWorker idWorker) {
        this.lotteryRecordDao = lotteryRecordDao;
        this.idWorker = idWorker;
    }

    /**
     * 增加
     *
     * @param lotteryRecord lotteryRecord
     */
    public void add(LotteryRecord lotteryRecord) {
        lotteryRecord.setId(idWorker.nextId() + "");// 雪花分布式ID生成器
        lotteryRecord.setCreateTime(new Date());
        lotteryRecordDao.save(lotteryRecord);
        System.out.println("TYPE:"+lotteryRecord.getGamekey() + " GID:" + lotteryRecord.getGid() + " save to db!");
    }

    public LotteryRecord findTopByGamekey(String gamekey) {
        return lotteryRecordDao.findTopByGamekeyOrderByCreateTimeDesc(gamekey);
    }

    public LotteryRecord findByGamekeyAndNextOpenIssue(String gameKey, String gid) {
        return lotteryRecordDao.findByGamekeyAndGid(gameKey, gid);
    }
}