package com.sky.plantogame.dao;

import com.sky.plantogame.pojo.LotteryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * lotteryRecord数据访问接口
 * @author Administrator
 *
 */
public interface LotteryRecordDao extends JpaRepository<LotteryRecord,String>,JpaSpecificationExecutor<LotteryRecord>{
    /**
     * 查询最新开奖记录
     * @param gameKey
     * @return
     */
    LotteryRecord findTop1ByGamekeyOrderByCreateTimeDesc(String gameKey);
    LotteryRecord findByGidAndGamekey (String gid,String gameKey);



}
