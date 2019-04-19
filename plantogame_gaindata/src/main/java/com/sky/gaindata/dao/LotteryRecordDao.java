package com.sky.gaindata.dao;

import com.sky.gaindata.pojo.LotteryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * lotteryRecord数据访问接口
 *
 * @author Administrator
 */
public interface LotteryRecordDao extends JpaRepository<LotteryRecord, String>, JpaSpecificationExecutor<LotteryRecord> {
    LotteryRecord findByGamekeyAndGid(String gamekey,String gid);
    LotteryRecord findTopByGamekeyOrderByCreateTimeDesc(String gameKey);
}
