package com.sky.gaindata.dao;

import com.sky.gaindata.vo.Total;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TotalDao extends JpaRepository<Total,String>, JpaSpecificationExecutor<Total> {

}
