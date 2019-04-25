package com.sky.plantogame.dao;

import com.sky.plantogame.pojo.Total;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * details数据访问接口
 *
 * @author Administrator
 */
public interface TotalDao extends JpaRepository<Total, String>, JpaSpecificationExecutor<Total> {
    @Query(value = "SELECT * FROM tb_synthesize_total WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 0 DAY) AND gamekey = ?1 ORDER BY create_time DESC LIMIT 0,200", nativeQuery = true)
    List<Total> findTotalList(String gameKey);
}
