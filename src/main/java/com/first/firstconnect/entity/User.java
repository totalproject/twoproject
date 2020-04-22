package com.first.firstconnect.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-04-13 16:45:04
 */
@Data
//JsonInclude注解的作用是，如果下面哪个属性值为null的话，Json数据里面就不显示属性值为null的key
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
    //实时时间
    private LocalDateTime localDateTime = LocalDateTime.now();
    private Integer id;//用户ID
    private String userName;//用户名
    private String userPassword;//用户账号密码
    //用户类型 0超管 1代理 2普通用户 3子账户 5特殊股东账户
    private Integer userType;
    private String createTime;//用户创建时间
    private Integer groupId;//用户所在组ID
    private String tableName;//用户信息表
    private String clubId;//俱乐部ID
    //token密码锁，到时候把密码先进行jwt加密，并把密码字段设为0
    private String token;
}