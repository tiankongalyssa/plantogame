package com.sky.gaindata.service;

import com.sky.gaindata.dao.K3Dao;
import com.sky.gaindata.pojo.K3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdWorker;

@Service
public class K3Service {
    private final K3Dao k3Dao;

    private final IdWorker idWorker;

    @Autowired
    public K3Service(K3Dao k3Dao, IdWorker idWorker) {
        this.k3Dao = k3Dao;
        this.idWorker = idWorker;
    }
    public void save(K3 k3) {
        k3.setId(idWorker.nextId() + "");
        k3Dao.save(k3);
    }
    public K3 findByGameKey(String gameKey){
        return k3Dao.findTopByGamekeyOrderByCreateTimeDesc(gameKey);
    }
}
