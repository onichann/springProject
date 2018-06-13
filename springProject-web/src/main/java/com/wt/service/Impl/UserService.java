package com.wt.service.Impl;

import com.wt.common.SpringCatch;
import com.wt.dao.LoginDao;
import com.wt.dao.UserDao;
import com.wt.mapper.LoginMapper;
import com.wt.model.LoginLog;
import com.wt.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private LoginDao loginDao;

    @Resource
    private LoginMapper loginMapper;

    public boolean hasMatchUser(String userName,String password){
        int matchCount=userDao.getMatchCount(userName,password);
        return matchCount>0;
    }



    public User findUserByUsernName(String userName){
       return userDao.findUserByUserName(userName);
    }

    public void loginSuccess(User user){
        user.setCredits(user.getCredits()+5);
        ApplicationContext applicationContext1=SpringCatch.getInstance().applicationContext();
        ApplicationContext applicationContext2=SpringCatch.getInstance().applicationContext();
        System.out.println(applicationContext1==applicationContext1);
        LoginLog loginLog= SpringCatch.getInstance().applicationContext().getBean("loginLog",LoginLog.class);
        loginLog.setUserId(user.getUserId());
        loginLog.setLoginDate(user.getLastVisit());
        loginLog.setIp(user.getLastIp());
        userDao.updateLoginInfo(user);
        loginDao.insertLoginLog(loginLog);
    }

    public boolean hasMatchUser2(String userName,String password){
        User user=loginMapper.selectByEntity(userName,password);
        return user!=null;
    }

    public List<User> selectAllUsers(){
        List<User> users=loginMapper.selectAllUsers();
        return users;
    }

    public boolean add(String uuid,String username,String password){
        int i=loginMapper.addUserInfo(uuid,username,password);
        return i>0;
    }

    public User selectOneUser(String userid){
        User user=SpringCatch.getInstance().applicationContext().getBean("user",User.class);
        user=loginMapper.seletOneUser(userid);
        return user;
    }

    public boolean update(User user){
        int i=loginMapper.updateUser(user);
        return  i>0;
    }

    public boolean delete(String id){
        int i=loginMapper.deleteUser(id);
        return i>0;
    }

}
