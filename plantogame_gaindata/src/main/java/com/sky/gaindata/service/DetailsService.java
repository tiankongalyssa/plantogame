package com.sky.gaindata.service;

import com.sky.gaindata.dao.DetailsDao;
import com.sky.gaindata.pojo.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import java.util.Date;

/**
 * details服务层
 *
 * @author Administrator
 */
@Service
public class DetailsService {

    private final DetailsDao detailsDao;
    private final IdWorker idWorker;

    @Autowired
    public DetailsService(DetailsDao detailsDao, IdWorker idWorker) {
        this.detailsDao = detailsDao;
        this.idWorker = idWorker;
    }

    /**
     * 根据gamekey查询上期开奖结果
     *
     * @param gameKey gameKey
     * @return Details
     */
    public Details findBeforeByGameKey(String gameKey) {
        return detailsDao.findByGamekey(gameKey);
    }

    /**
     * 增加
     *
     * @param details details
     */
    public void add(Details details) {
        Details info = detailsDao.findByGamekeyAndGid(details.getGamekey(), details.getGid());
        if (info == null) {
            details.setId(idWorker.nextId() + ""); // 雪花分布式ID生成器
            details.setCreateTime(new Date());
            detailsDao.save(details);
        }
    }
}
