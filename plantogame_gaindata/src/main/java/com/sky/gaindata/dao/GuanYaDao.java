package com.sky.gaindata.dao;

import com.sky.gaindata.vo.GuanYa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuanYaDao extends JpaRepository<GuanYa,String>, JpaSpecificationExecutor<GuanYa> {

}
