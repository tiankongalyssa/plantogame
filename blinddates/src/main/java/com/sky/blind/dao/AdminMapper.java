package com.sky.blind.dao;

import com.sky.blind.pojo.Admin;
import com.sky.blind.pojo.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdminMapper extends Mapper<Admin> {
    Admin findByUsername(String username);

    List<User> findByAdminId(Integer admindId);

    Admin findAdminById(Integer id);

}
