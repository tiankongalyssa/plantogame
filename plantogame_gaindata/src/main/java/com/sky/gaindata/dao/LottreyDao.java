package com.sky.gaindata.dao;

import com.sky.gaindata.pojo.Lottrey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * lottrey数据访问接口
 * @author Administrator
 *
 */
public interface LottreyDao extends JpaRepository<Lottrey,String>,JpaSpecificationExecutor<Lottrey>{
	List<Lottrey> findByState(Integer state);
	Lottrey findByGamekey (String gameKey);
}
