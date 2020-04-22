package com.first.firstconnect.controller;

import com.first.firstconnect.entity.ServerResponse;
import com.first.firstconnect.service.OrdinaryUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/ordinary")
public class OrdinaryUserController {

    @Resource
    private OrdinaryUserService ordinaryUserService;

    //查询总数据
    @PostMapping("total/game")
    public ServerResponse findTotalGameData(String username,Integer type,Integer begin,Integer size)
    {
        //,begin,size
        return ordinaryUserService.findTotalGameData(username,type);
    }
    //查询总数据里面的每局

    @PostMapping("find/alone/user/info")
    public ServerResponse findAloneUserInfo(String username,Integer type,String playerName) {

        System.out.println("进来了");

        return ordinaryUserService.findAloneUserInfo(username, type, playerName);
    }



    //查询每局数据
    @PostMapping("each/game")
    public ServerResponse findEachGameData(String username,Integer type){

        return ordinaryUserService.findEachGameData(username,type);
    }
}
