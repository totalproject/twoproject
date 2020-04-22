package com.first.firstconnect.service;

import com.first.firstconnect.entity.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrdinaryUserService {

    /*
    *
    * 查询总数据
    *, Integer begin,Integer size
    * */
    public ServerResponse findTotalGameData(String username, Integer typem);
    /*
    *
    * 查询总数据的每局
    *
    * */
    public ServerResponse findAloneUserInfo(String username, Integer type, String playerName);



    /*
    *
    * 查询每局比赛 scare
    *
    *
    * */
    public ServerResponse findEachGameData(String username,Integer type);
}
