package com.first.firstconnect.entity.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDto {
    private String playerId;//玩家Id
    private String playerName;//玩家名
    private String shareholder;//股东名
    private String tableName;//表名
    private String operator;//操作
    private String oldName;//旧名
    private String oldShareholder;//老股东名
    private String checkMarks;//检查标记
    private Integer warningPoints;//警告一次
    private Integer warningPointsTwo;//警告两次
    private Integer follow;
    //传入的要拆分的名字
    private String playerNameMerge;
    /**
     * 0 踢出 1 未分组 2 限制组
     */
    private Integer manipulate;
}
