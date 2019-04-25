package com.sky.plantogame.dao;

import com.sky.plantogame.pojo.GuanYa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * analysis数据访问接口
 *
 * @author Administrator
 */
public interface GuanYaDao extends JpaRepository<GuanYa, String>, JpaSpecificationExecutor<GuanYa> {
    // WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 1 DAY) 查询昨天
    @Query(value = "SELECT * FROM tb_synthesize_guan_ya WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 0 DAY) AND gamekey = ?1 ORDER BY create_time DESC limit 0,200", nativeQuery = true)
    List<GuanYa> findGuanYaList(String gameKey);
}
