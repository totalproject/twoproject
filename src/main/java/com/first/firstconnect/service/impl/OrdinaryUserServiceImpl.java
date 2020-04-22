package com.first.firstconnect.service.impl;

import com.first.firstconnect.dao.OrdinaryUserDao;
import com.first.firstconnect.entity.EachGameData;
import com.first.firstconnect.entity.ServerResponse;
import com.first.firstconnect.entity.TotalGameData;
import com.first.firstconnect.service.OrdinaryUserService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdinaryUserServiceImpl implements OrdinaryUserService {

    @Autowired
    OrdinaryUserDao ordinaryUserDao;


//    findTotal,Integer begin,Integer size
    //vvv和vvvv联合查询获取总数据
    @Override
    public ServerResponse findTotalGameData(String username,Integer type) {
        List<TotalGameData> gameTotalData = ordinaryUserDao.findTotalGameData(username);
        //带标记负分
        List<TotalGameData> gameTotalData1 = new ArrayList<>();
        //带标记正分
        List<TotalGameData> gameTotalData2 = new ArrayList<>();
        //不带标记负分
        List<TotalGameData> gameTotalData3 = new ArrayList<>();
        //不带标记正分
        List<TotalGameData> gameTotalData4 = new ArrayList<>();
        //0分
        List<TotalGameData> gameTotalData5 = new ArrayList<>();

        for (TotalGameData gameTotalDatum : gameTotalData) {
            //分数不能为空不能为0
            if (gameTotalDatum.getFraction() != null && Integer.parseInt(gameTotalDatum.getFraction()) != 0) {
                //踢人线不能为空不能0并且分数小于踢人线
                if (gameTotalDatum.getWarningPointsTwo() != null && gameTotalDatum.getWarningPointsTwo() != 0
                        && Integer.parseInt(gameTotalDatum.getFraction()) <= gameTotalDatum.getWarningPointsTwo()) {
                    gameTotalDatum.setFollow(3);
                    gameTotalData1.add(gameTotalDatum);
                    //警告线不能为空，并且分数小于警告线
                } else if (gameTotalDatum.getWarningPoints() != null && gameTotalDatum.getWarningPoints() != 0
                        && Integer.parseInt(gameTotalDatum.getFraction()) <= gameTotalDatum.getWarningPoints()) {
                    gameTotalDatum.setFollow(2);
                    gameTotalData2.add(gameTotalDatum);
                    //添加分数大于0的数据
                } else if (Integer.parseInt(gameTotalDatum.getFraction()) < 0) {
                    gameTotalData3.add(gameTotalDatum);
                    //添加分数小于0的数据
                } else if (Integer.parseInt(gameTotalDatum.getFraction()) > 0) {
                    gameTotalData4.add(gameTotalDatum);
                } else {
                    gameTotalData5.add(gameTotalDatum);
                }
            } else {
                gameTotalData5.add(gameTotalDatum);
            }
        }
        gameTotalData1.addAll(gameTotalData2);
        gameTotalData1.addAll(gameTotalData3);
        gameTotalData1.addAll(gameTotalData4);
        gameTotalData1.addAll(gameTotalData5);
        return ServerResponse.createBySuccess(gameTotalData1);
    }

    /*
     *
     * 查询总数据的每局
     *
     * */
    public ServerResponse findAloneUserInfo(String username, Integer type, String playerName){
        List<EachGameData> aloneUserInfo = ordinaryUserDao.findAloneUserInfo(username, playerName);
        List<EachGameData> aloneUserInfos = new ArrayList<>();
        for (EachGameData eachGameData : aloneUserInfo) {
            String[] s = eachGameData.getTimer().split(" ");
            String[] split = s[0].split("-");
            String timer = split[1] + "-" + split[2] + " " + s[1];
            eachGameData.setTimer(timer);
            aloneUserInfos.add(eachGameData);
        }
        return ServerResponse.createBySuccess(aloneUserInfos);
    }







    //查看每局分数
    @Override
    public ServerResponse findEachGameData(String username,Integer type) {

        List<EachGameData> eachGameData = ordinaryUserDao.findEachGameData(username);
        List<List<EachGameData>> lists = eachGameDataDistinguish(eachGameData);
        return ServerResponse.createBySuccess(lists);
    }

    /*根据时间段区分每一局的数据*/
    private List<List<EachGameData>> eachGameDataDistinguish(List<EachGameData> list) {
        List<List<EachGameData>> lists = new ArrayList<>();
        List<EachGameData> eachInfos1 = new ArrayList<>();
        List<EachGameData> eachInfos2 = new ArrayList<>();
        List<EachGameData> eachInfos3 = new ArrayList<>();

        if (list.size() == 1) {
            eachInfos1.add(list.get(0));
            lists.add(eachInfos1);
            return lists;
        }

        int var = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 < list.size()) {
                if (list.get(i).getPhoto() != null
                        && list.get(i + 1).getPhoto() != null
                        && list.get(i).getTimer() != null
                        && list.get(i + 1).getTimer() != null
                        && list.get(i).getPhoto().length() >= 2
                        && list.get(i + 1).getPhoto().length() >= 2
                        && StringUtils.equals(list.get(i).getTimer(), list.get(i + 1).getTimer())
                        && !StringUtils.equals(list.get(i).getPhoto().substring(0, 1), "上")
                        && !StringUtils.equals(list.get(i).getPhoto().substring(0, 1), "下")
                        && !StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2, list.get(i).getPhoto().length() - 1), "下")
                        && !StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2, list.get(i).getPhoto().length() - 1), "上")

                ) {
                    if (var == 0) {
                        if (eachInfos2.size() > 0) {
                            lists.add(eachInfos2);
                            eachInfos2 = new ArrayList<>();
                        }
                        list.get(i).setFlag(0);
                        list.get(i).setTimer(list.get(i).getTimer());
                        eachInfos1.add(list.get(i));
                    } else {
                        if (eachInfos1.size() > 0) {
                            lists.add(eachInfos1);
                            eachInfos1 = new ArrayList<>();
                        }
                        list.get(i).setFlag(1);
                        list.get(i).setTimer(list.get(i).getTimer());
                        eachInfos2.add(list.get(i));
                    }
                } else if (list.get(i).getPhoto() != null && list.get(i).getPhoto().length() >= 2) {
                    if (!StringUtils.equals(list.get(i).getPhoto().substring(0, 1), "上")
                            && !StringUtils.equals(list.get(i).getPhoto().substring(0, 1), "下")
                            && !StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2, list.get(i).getPhoto().length() - 1), "下")
                            && !StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2, list.get(i).getPhoto().length() - 1), "上")
                    ) {

                        if (var == 0) {
                            var = 1;
                            list.get(i).setFlag(0);
                            list.get(i).setTimer(list.get(i).getTimer());
                            eachInfos1.add(list.get(i));
                        } else {
                            var = 0;
                            list.get(i).setFlag(1);
                            list.get(i).setTimer(list.get(i).getTimer());
                            eachInfos2.add(list.get(i));
                        }
                    } else {
                        if (eachInfos3.size() > 0) {
                            lists.add(eachInfos3);
                            eachInfos3 = new ArrayList<>();
                        }
                        list.get(i).setFlag(3);
                        list.get(i).setTimer(list.get(i).getTimer());
                        eachInfos3.add(list.get(i));
                    }
                }
            } else {
                if (list.get(i).getPhoto() != null && list.get(i).getPhoto().length() >= 2) {
                    if (!StringUtils.equals(list.get(i).getPhoto().substring(0, 1), "上")
                            && !StringUtils.equals(list.get(i).getPhoto().substring(0, 1), "下")
                            && !StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2, list.get(i).getPhoto().length() - 1), "下")
                            && !StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2, list.get(i).getPhoto().length() - 1), "上")
                    ) {
                        if (var == 0) {
                            var = 1;
                            list.get(i).setFlag(0);
                            list.get(i).setTimer(list.get(i).getTimer());
                            eachInfos1.add(list.get(i));
                        } else {
                            var = 0;
                            list.get(i).setFlag(1);
                            list.get(i).setTimer(list.get(i).getTimer());
                            eachInfos2.add(list.get(i));
                        }
                    } else {
                        if (eachInfos3.size() > 0) {
                            lists.add(eachInfos3);
                            eachInfos3 = new ArrayList<>();
                        }
                        list.get(i).setFlag(3);
                        list.get(i).setTimer(list.get(i).getTimer());
                        eachInfos3.add(list.get(i));
                    }
                }
            }

        }
        if (eachInfos1.size() > 0) {
            lists.add(eachInfos1);
        }
        if (eachInfos2.size() > 0) {
            lists.add(eachInfos2);
        }
        if (eachInfos3.size() > 0) {
            lists.add(eachInfos3);
        }
        return lists;
    }

//    /*根据时间段区分每一局的数据*/
//    private List<List<EachGameData>> eachGameDataDistinguish(List<EachGameData> list){
//        List<List<EachGameData>> lists = new ArrayList<>();
//        //同个时间段，且不属于上下分列别
//        List<EachGameData> sameTime  = new ArrayList<>();
//        //属于上分列别
//        List<EachGameData> upGrade   = new ArrayList<>();
//        //属于上分列别
//        List<EachGameData> downGrade = new ArrayList<>();
//
//        //如果list(List<EachGameData>)里面只有一组数据，那么就把他放进这个Lists(List<List<EachGameData>>)里面
//        if(list.size() == 1){
//            sameTime.add(list.get(0));
//            lists.add(sameTime);
//            return lists;
//        }
//
//        //注释也不打脑子有病，这个作用是什么也不说
//        int fuck = 0;
//        //循环遍历list里面的值，根据条件进行分类
//        for (int i = 0; i < list.size();i++){
//            //list.size是从1开始，而集合是从0开始，故需要下面这个if
//            if (i + 1 < list.size()){
//                //Photo不是相片的意思而是时间，好气，lqbz的命名
//                if(list.get(i).getPhoto() != null
//                    //下一段时间不为空
//                    && list.get(i + 1).getPhoto() != null
//                    //timer是无中文的时间表示
//                    && list.get(i).getTimer() != null
//                    //下一个数据里面的timer不为空
//                    && list.get(i + 1).getTimer() != null
//                    //时间长度大于2
//                    &&list.get(i).getPhoto().length() >= 2
//                    //下一个时间长度大于2
//                    &&list.get(i+1).getPhoto().length() >= 2
//                    //前后时间一致划分到一个集合里面
//                    &&StringUtils.equals(list.get(i).getTimer(),list.get(i + 1).getTimer())
//                    //Photo(创建时间，字段好乱的说)字段的首字符不为"上"
//                    &&!StringUtils.equals(list.get(i).getPhoto().substring(0,1),"上")
//                    //Photo(创建时间，字段好乱的说)字段的首字符不为"下"
//                    &&!StringUtils.equals(list.get(i).getPhoto().substring(0,1),"下")
//                    //
//                    &&!StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2, list.get(i).getPhoto().length() - 1), "下")
//                    //
//                    &&!StringUtils.equals(list.get(i).getPhoto().substring(list.get(i).getPhoto().length() - 2,
//                        list.get(i).getPhoto().length() - 1), "上")
//                ){
//                    if(fuck == 0) {
//                        //让它单独入集合里面的集合，带上时间信息，方便前端使用
//                        if (upGrade.size() > 0) {
//                            lists.add(upGrade);
//                            upGrade = new ArrayList<>();
//                        }
//                        list.get(i).setFlag(0);
//                        list.get(i).setTimer(list.get(i).getTimer());
//                        sameTime.add(list.get(i));
//                    }//if
//                     else {
//                            //如果sameTime不为空，插入到lists中
//                            if (sameTime.size() > 0){
//                                lists.add(sameTime);
//                                sameTime = new ArrayList<>();
//                            }
//                            list.get(i).setFlag(1);
//                            list.get(i).setTimer(list.get(i).getTimer());
//                            upGrade.add(list.get(i));
//                        }//else
//                    }
//                }
//                //如果时间段首字符是"上"，则单独分组，放进List<List<T>>中
//                else if (StringUtils.equals(list.get(i).getPhoto().substring(0,1),"上")){
//                    upGrade.add(list.get(i));
//                    lists.add(upGrade);
//
//                }
//                //如果时间段首字符是"下"，则单独分组，放进List<List<T>>中
//                else if (StringUtils.equals(list.get(i).getPhoto().substring(0,1),"下")){
//                    downGrade.add(list.get(i));
//                    lists.add(downGrade);
//                }
//
//            }
//
//
//
//        //同一个时间段且不属于上下分的分成一堆，属于上下分的另外再分一堆
//
//        return null;
//
//    }
//
//    public ServerResponse findIntervalEachGameData(String username, Integer type, String timer, Integer id) {
//        return null;
//    }
}
