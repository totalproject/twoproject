//package com.first.firstconnect.dao.mongo;
//
//import com.xy.yzj.scoreindicator.pojo.mongo.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Component;
//
//import javax.management.Query;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//
//@SuppressWarnings("Duplicates")
//@Component
//public class LogMapper {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Value("${save.log.day}")
//    public Integer logDay;
//
//    /**
//     * 保存删除战绩的操作
//     *
//     * @param mongoDBRecord
//     * @param username
//     */
//    public void saveRecordDelLog(MongoDBRecord mongoDBRecord, String username) {
//        mongoTemplate.insert(mongoDBRecord, "Record_" + username);
//    }
//
//    /**
//     * 保存超时图补录的记录
//     *
//     * @param mongoDBTimeOut
//     * @param username
//     */
//    public void saveTimeOuTRecord(MongoDBTimeOut mongoDBTimeOut, String username) {
//        mongoTemplate.insert(mongoDBTimeOut, "TimeOut" + username);
//    }
//
//
//    /**
//     * 单点登陆记录
//     *
//     * @param token
//     * @param username
//     */
//    public void saveLoginToken(MongoDBToken token, String username) {
//        mongoTemplate.insert(token, "TOKEN_" + username);
//    }
//
//    public List<MongoDBToken> getLoginToken(String username) {
//        return mongoTemplate.findAll(MongoDBToken.class, "TOKEN_" + username);
//    }
//
//
//    /**
//     * 记录拆分的日志
//     *
//     * @param mongoDBPlayer
//     * @param username
//     */
//    public void insertSplitLog(MongoDBPlayer mongoDBPlayer, String username) {
//        mongoTemplate.insert(mongoDBPlayer, "SPLIT_" + username);
//
//    }
//
//    /**
//     * 合并记录
//     *
//     * @param mongoDBMerge
//     * @param username
//     */
//    public void insertMergeLog(MongoDBMerge mongoDBMerge, String username) {
//        mongoTemplate.insert(mongoDBMerge, "MERGE_" + username);
//    }
//
//    /**
//     * 玩家新增记录
//     */
//    public void insertPlayerAdd(MongoDBPlayer mongoDBPlayer, String username) {
//        mongoTemplate.insert(mongoDBPlayer, "PLAYERADD_" + username);
//    }
//
//    /**
//     * 修改记录
//     *
//     * @param mongoDBPlayer
//     * @param username
//     */
//    public void insertPlayerUpdate(MongoDBPlayer mongoDBPlayer, String username) {
//        mongoTemplate.insert(mongoDBPlayer, "PLAYERUPDATE_" + username);
//    }
//
//
//    /**
//     * 保存登录的日志
//     */
//    public void saveLoginLog(String username, MongoDBLogin mongoLogin) {
//        mongoTemplate.insert(mongoLogin, "LOGIN_" + username);
//    }
//
//
//    /**
//     * 上下分日志插入
//     *
//     * @param list
//     * @param username
//     */
//    public void insertUpDownScoreLog_WEB(List<MongoDBJournal> list, String username) {
//        mongoTemplate.insert(list, "WEB_" + username);
//    }
//
//    /**
//     * 上下分日志插入
//     *
//     * @param mongoDBJournal
//     * @param username
//     */
//    public void insertUpDownScoreLog_WEB(MongoDBJournal mongoDBJournal, String username) {
//        mongoTemplate.insert(mongoDBJournal, "WEB_" + username);
//    }
//
//
//    /**
//     * 获取浏览器上的上下分日志
//     *
//     * @param username 用户名
//     * @return List<MongoDBJournal>
//     */
//    public List<MongoDBJournal> getUpDownScoreLog_WEB(String username, Integer limit) {
//        String name = "WEB_" + username;
//        String pcTime = getTime("PC_" + username);
//        String webTime = getTime(name);
//        if (webTime != null && pcTime != null) {
//            removeThreeDayLog(name, webTime.compareTo(pcTime) >= 0 ? webTime : pcTime);
//        } else {
//            if (webTime == null) {
//                removeThreeDayLog(name, pcTime);
//            }
//            if (pcTime == null) {
//                removeThreeDayLog(name, webTime);
//            }
//        }
//        Query query = new Query();
//        Sort sort = Sort.by(Sort.Direction.DESC, "timer");
//        return mongoTemplate.find(query.with(sort).limit(limit), MongoDBJournal.class, name);
//    }
//
//    /**
//     * 获取PC上的上下分日志
//     *
//     * @param username 用户名
//     * @return List<MongoDBJournal>
//     */
//    public List<MongoDBJournal> getUpDownScoreLog_PC(String username, Integer limit) {
//        String name = "PC_" + username;
//        String webTime = getTime("WEB_" + username);
//        String pcTime = getTime(name);
//        if (webTime != null && pcTime != null) {
//            removeThreeDayLog(name, webTime.compareTo(pcTime) >= 0 ? webTime : pcTime);
//        } else {
//            if (webTime == null) {
//                removeThreeDayLog(name, pcTime);
//            }
//            if (pcTime == null) {
//                removeThreeDayLog(name, webTime);
//            }
//        }
//        Query query = new Query();
//        Sort sort = Sort.by(Sort.Direction.DESC, "timer");
//        return mongoTemplate.find(query.with(sort).limit(limit), MongoDBJournal.class, name);
//    }
//
//
//    private String getTime(String collectionName) {
//        List<MongoDBJournal> all = mongoTemplate.findAll(MongoDBJournal.class, collectionName);
//        if (all.size() > 0) {
//            String lastTime = all.get(all.size() - 1).getTimer();
//            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime parse = LocalDateTime.parse(lastTime, df);
//            return parse.minusDays(7).format(df);
//        }
//        return null;
//    }
//
//    private void removeThreeDayLog(String collectionName, String time) {
//        mongoTemplate.remove(new Query().addCriteria(new Criteria("timer").lte(time)), collectionName);
//    }
//
//    /**
//     * 修改玩家警告线
//     *
//     * @param mongoDBPlayer
//     * @param tableName
//     */
//    public void insertPlayerUpdateWarningPoints(MongoDBPlayer mongoDBPlayer, String tableName) {
//        mongoTemplate.insert(mongoDBPlayer, "PLAYERUPDATEWP_" + tableName);
//    }
//
//    /**
//     * 修改股东密码
//     *
//     * @param mongoShareholder
//     * @param ordinaryUserName
//     */
//    public void insertShareholderUpdatePassword(MongoShareholder mongoShareholder, String ordinaryUserName) {
//        mongoTemplate.insert(mongoShareholder, "SHAREHOLDERUPDATEPASSWORD" + ordinaryUserName);
//    }}
