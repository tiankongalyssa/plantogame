package com.sky.plantogame.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sky.plantogame.pojo.Lottrey;
/**
 * lottrey数据访问接口
 * @author Administrator
 *
 */
public interface LottreyDao extends JpaRepository<Lottrey,String>,JpaSpecificationExecutor<Lottrey>{
	
}
