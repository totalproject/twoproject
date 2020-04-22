package com.first.firstconnect.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EachGameData {
    private Integer id;//表中Id标识符
    private String userId;//用户id
    private String score;//每局分数
    private String photo;//时间(名字不是我取的，很乱就是)
    private int delScore = 0;//扣除的分数
    private String timer;//读取分数的时间
    private String BigWinner = "0";//大赢家
    private String UserName;//用户名
    private Integer fraction;//当前总分

    private int flag = 0;
}
