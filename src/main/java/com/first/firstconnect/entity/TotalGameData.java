package com.first.firstconnect.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalGameData {


    private String GroupName;//所在组名
    private String UserName;//用户名
    private String UserID;//用户ID
    private String ShareholderName;//股东名
    private String Deduction;//基础扣分
    private String Fraction;//玩家总分
    private String ShareholderNub;//额外扣分,赢了也得把你分扣了
    private String Amount;//金额
    private Integer follow=0;//网站标记用
    private Integer warningPoints;//警告分一次
    private Integer warningPointsTwo;//警告分两次

    private boolean shareholderNameExist;//股东名字是否存在标识符
    private String shareholderPassword;//股东密码
    private Integer shareholderId;//股东的ID
    private Integer sign;//*******这个等我研究透这个项目再回来打备注

    private Integer check; //是否超时
}
