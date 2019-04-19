package com.sky.gaindata.dao;

import com.sky.gaindata.pojo.PlanCreate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * planCreate数据访问接口
 *
 * @author Administrator
 */
public interface PlanCreateDao extends JpaRepository<PlanCreate, String>, JpaSpecificationExecutor<PlanCreate> {

    /**
     * 根据正在进行的预测查找预测结果
     *
     * @return
     */
    PlanCreate findByTypeAndGamekeyAndPresent(String type, String gameKey, String present);

    /**
     * 记数所有
     * @param gameKey
     * @param type
     * @return
     */
    @Query(value = "SELECT count(*) FROM tb_plan_create WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 0 DAY)  AND gamekey = ?1 AND type = ?2",nativeQuery = true)
    Double countPlanToDay(String gameKey, String type);

    @Query(value = "SELECT COUNT(*) FROM tb_plan_create WHERE DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_SUB(CURDATE(),INTERVAL 0 DAY)  AND gamekey = ?1 AND type = ?2 AND win IN ('1','2','3') ",nativeQuery = true)
    Double countWinPlanToDay(String gameKey, String type);

}
