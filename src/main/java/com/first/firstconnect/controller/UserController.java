package com.first.firstconnect.controller;

import com.first.firstconnect.entity.ServerResponse;
import com.first.firstconnect.entity.User;
import com.first.firstconnect.service.OrdinaryUserService;
import com.first.firstconnect.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2020-04-13 16:45:04
 */
//@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 对象的服务
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne/{id}")
    public User selectOne(@PathVariable Integer id, HttpServletRequest httpServletRequest,HttpSession session) {

        session = httpServletRequest.getSession();

        return this.userService.queryById(id);
    }

    @PostMapping("/login")
    public ServerResponse login(String username,String password){

        ServerResponse login = userService.loginByUsernameAndPassword(username,password);

        return login;
    }




}