package com.sky.gaindata.dao;

import com.sky.gaindata.pojo.K3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * K3数据访问接口
 *
 * @author Administrator
 */
public interface K3Dao extends JpaRepository<K3, String>, JpaSpecificationExecutor<K3> {
    K3 findTopByGamekeyOrderByCreateTimeDesc(String gameKey);
}
