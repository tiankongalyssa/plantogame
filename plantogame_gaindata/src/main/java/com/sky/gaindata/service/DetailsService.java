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
     * 查询上期开奖结果
     *
     * @param gameKey 游戏名
     * @return 详细记录
     */
    public Details findBeforeByGameKey(String gameKey) {
        return detailsDao.findByGamekey(gameKey);
    }

    /**
     * 增加详细记录
     *
     * @param details details
     */
    public void add(Details details) {
        Details info = detailsDao.findByGamekeyAndGid(details.getGamekey(), details.getGid());
        // 查询本期记录是否存在不存在则保存记录
        if (info != null) {
            return;
        }
        details.setId(idWorker.nextId() + ""); // 雪花分布式ID生成器
        details.setCreateTime(new Date());
        detailsDao.save(details);
    }
}
