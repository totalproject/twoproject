package com.first.firstconnect.dao;

import com.first.firstconnect.entity.SubAccount;

//这个是使用sub_Account这张表
public interface SubAccountDao {
    /**
     * 根据名字查找用户
     *
     * @param username 用户名
     * @return 实体类
     */
    SubAccount findByName(String username);
}
