package com.sky.plantogame.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sky.plantogame.pojo.PlanCreate;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * planCreate数据访问接口
 * @author Administrator
 *
 */
public interface PlanCreateDao extends JpaRepository<PlanCreate,String>,JpaSpecificationExecutor<PlanCreate>{


    @Query(value = "SELECT * from tb_plan_create WHERE type=?1 AND gamekey=?2 AND present=?3",nativeQuery = true)
    PlanCreate findNewPlant(String type, String gameKey, String present);

    @Query(value = "SELECT * FROM tb_plan_create WHERE  type =?1  AND gamekey = ?2 AND create_time BETWEEN DATE(NOW())  AND gamekey = '' ORDER BY create_time DESC limit 0,50 ",nativeQuery = true)
    List<PlanCreate> findTodayList(String type,String gameKey);
    @Query(value = "SELECT * FROM tb_plan_create WHERE gamekey=?1 AND type=?2 ORDER BY create_time DESC LIMIT 1,1",nativeQuery = true)
    PlanCreate findBefore(String gameKey,String type);
}
