package com.first.firstconnect.dao;

import com.first.firstconnect.entity.EachGameData;
import com.first.firstconnect.entity.OrdinaryUsers;
import com.first.firstconnect.entity.TotalGameData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrdinaryUserDao {
    /*
    * 通过名字查询用户数据
    *
    *
    * */
    OrdinaryUsers findByName(String username);

    /*
     * 查询总数据
     *,Integer begin
     *
     * */
    List<TotalGameData> findTotalGameData(String username);

    /*
    *
    * 查询总数据里面的每局
    *
    * */
    List<EachGameData> findAloneUserInfo(@Param("username") String username, @Param("playerName") String playerName);


    /*
     * 一级功能里面查询每局数据
     *
     *
     * */

    List<EachGameData> findEachGameData(@Param("username") String username);

}
