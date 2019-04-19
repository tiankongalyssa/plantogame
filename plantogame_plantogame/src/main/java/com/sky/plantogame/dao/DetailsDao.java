package com.sky.plantogame.dao;

import com.sky.plantogame.pojo.Details;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * details数据访问接口
 *
 * @author Administrator
 */
public interface DetailsDao extends JpaRepository<Details, String>, JpaSpecificationExecutor<Details> {
    /**
     * 查询今天所有详情数据
     *
     * @param gameKey  gameKey
     * @param pageable pageable
     * @return Page<Details>
     */
    @Query(value = "SELECT * FROM tb_details WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 0 DAY) AND gamekey = ?1 ORDER BY create_time DESC", nativeQuery = true)
    Page<Details> findToday(String gameKey, Pageable pageable);

    /**
     * 查询昨天所有详情数据
     *
     * @param gameKey  gameKey
     * @param pageable pageable
     * @return Page<Details>
     */                                                         //创建时间格式转换为2019-3-4  DATE_SUB(CURDATE(),INTERVAL 1 DAY)获取昨天日期
    @Query(value = "SELECT * FROM tb_details WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 1 DAY) AND gamekey = ?1 ORDER BY create_time DESC", nativeQuery = true)
    Page<Details> findYesterDay(String gameKey, Pageable pageable);

    /**
     * 查询前天所有详情数据
     *
     * @param gameKey  gameKey
     * @param pageable pageable
     * @return Page<Details>
     */
    @Query(value = "SELECT * FROM tb_details WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 2 DAY)  AND gamekey = ?1 ORDER BY create_time DESC", nativeQuery = true)
    Page<Details> findBeforeYesterDay(String gameKey, Pageable pageable);

    @Query(value = "SELECT * FROM `tb_details` WHERE gamekey=?1 AND DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL ?2 DAY) ORDER BY create_time DESC;", nativeQuery = true)
    List<Details> findByDateAndGamekey(String gameKey, Integer day);

    @Query(value = "SELECT * FROM `tb_details` WHERE gamekey=?1  ORDER BY create_time DESC  LIMIT 0,?2 ;", nativeQuery = true)
    List<Details> findByGamekey(String gameKey, Integer size);

}