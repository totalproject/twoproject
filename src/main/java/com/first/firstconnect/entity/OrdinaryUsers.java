package com.first.firstconnect.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdinaryUsers {
    //当地时间
    private LocalDateTime localDateTime = LocalDateTime.now();

    private Integer id;//用户ID
    private String userName;//用户名
    private Integer state;//登陆状态
    private Integer delBeanBase;//状态基数
    private String machineId;//机器ID
    private Integer invitation;//
    private Integer groupId;//小组ID
    private Integer excelAndTxt;
    private Integer totalBeans;
    private Integer delTotalBeans;
    private Integer picAnalysisFrequency;
    private Integer loginAuthority;
    private String createTime;//创建时间
    private String updateTime;//更新时间
    private Integer logicalDel;
    private String clubId;//用户所在俱乐部
    private String reservedFields2;
    private String reservedFields3;
}
