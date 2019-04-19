package com.sky.plantogame.dao;

import com.sky.plantogame.pojo.K3;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * K3数据访问接口
 *
 * @author Administrator
 */
public interface K3Dao extends JpaRepository<K3, String>, JpaSpecificationExecutor<K3> {

    @Query(value = "SELECT * FROM tb_k3 WHERE gamekey = ?1 ORDER BY create_time DESC", nativeQuery = true)
    Page<K3> findToday(String gameKey, Pageable pageable);

    @Query(value = "SELECT * FROM tb_k3 WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 0 DAY) AND gamekey = ?1 ORDER BY create_time DESC", nativeQuery = true)
    Page<K3> findYesterDay(String gameKey, Pageable pageable);

    @Query(value = "SELECT * FROM tb_k3 WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 1 DAY) AND gamekey = ?1 ORDER BY create_time DESC", nativeQuery = true)
    Page<K3> findYesterDayBefor(String gameKey,  Pageable pageable);

    @Query(value = "SELECT * FROM tb_k3 WHERE gamekey = ?1 ORDER BY create_time DESC limit ?2", nativeQuery = true)
    List<K3> findTodayList(String gameKey,int size);

    @Query(value = "SELECT * FROM tb_k3 WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 0 DAY) AND gamekey = ?1 ORDER BY create_time DESC limit ?2", nativeQuery = true)
    List<K3> findYesterDayList(String gameKey, int size);

    @Query(value = "SELECT * FROM tb_k3 WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 1 DAY) AND gamekey = ?1 ORDER BY create_time DESC limit ?2", nativeQuery = true)
    List<K3> findYesterDayBeforList(String gameKey, int size);
}