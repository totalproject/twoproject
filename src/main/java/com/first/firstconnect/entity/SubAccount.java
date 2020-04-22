package com.first.firstconnect.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubAccount {
    private Date date = new Date();//创建时间
    private Integer id;//用户ID
    private String userName;//用户名
    private String userPassword;//用户密码
    private String mainAccountName;//主账号
    private Integer delTotalBeans;//
    private String machineId;//机器ID
    private Integer excelAndTxt;
    private String createTime;//创建时间
    private String updateTime;//更新时间
}
