package com.first.firstconnect.service.impl;

import com.first.firstconnect.dao.OrdinaryUserDao;
import com.first.firstconnect.dao.SubAccountDao;
import com.first.firstconnect.entity.OrdinaryUsers;
import com.first.firstconnect.entity.ServerResponse;
import com.first.firstconnect.entity.SubAccount;
import com.first.firstconnect.entity.User;
import com.first.firstconnect.dao.UserDao;
import com.first.firstconnect.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-04-13 16:45:04
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private SubAccountDao subAccountDao;
    @Resource
    private OrdinaryUserDao ordinaryUserDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */

    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    /**
     * 通过username和password进行验证
     * @return 实例对象
     */
    public ServerResponse loginByUsernameAndPassword(String username, String password){
        User user = this.userDao.loginByUsernameAndPassword(username,password);
        //有这个用户则继续加一些属性，无则报错，把错误信息提交给前台
        if (user!=null){

            //subAccountMapper.findByName从sub_Acconut（子账号表）表获取，符合username = #username的账户信息
            SubAccount subAccount = subAccountDao.findByName(username);

            //ordinaryUsersMapper.findByName从ordinary_users（账号信息表）这个表获取账户信息
            OrdinaryUsers ordinaryUsers = ordinaryUserDao.findByName(subAccount.getMainAccountName());
            user.setGroupId(ordinaryUsers.getGroupId());
            user.setTableName(subAccount.getMainAccountName());
            user.setClubId(ordinaryUsers.getClubId());
            return ServerResponse.createBySuccess(user);
        }else {
            return  ServerResponse.createByErrorMsg("请用子账号登陆");}
    }
}