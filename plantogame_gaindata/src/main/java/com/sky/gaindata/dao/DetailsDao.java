package com.sky.gaindata.dao;

import com.sky.gaindata.pojo.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * details数据访问接口
 * @author Administrator
 *
 */
public interface DetailsDao extends JpaRepository<Details,String>,JpaSpecificationExecutor<Details>{
    Details findByGamekeyAndGid(String gameKey,String gid);


    /**
     * 查询上期结果
     * @param gamekey gamekey
     * @return Details
     */
    @Query(value = "SELECT * FROM tb_details WHERE gamekey = ? ORDER BY create_time DESC LIMIT 0,1",nativeQuery = true)
	Details findByGamekey(String gamekey);
}
