package com.wt.service.Impl;

import com.wt.mapper.dao.UserDao;
import com.wt.model.TUser;
import com.wt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public TUser selectOneUser(String userName, String password) {

        return userDao.selectOneUser(userName, password);
    }

    @Override
    public List<TUser> selectAllUsers() {
        List<TUser> users = userDao.selectAllUsers();
        return users;
    }

    @Override
    public TUser selectUserById(String featid) {
        return userDao.selectUserById(featid);
    }

    @Override
    public boolean addUser(TUser tUser) {
        int i=userDao.addUser(tUser);
        return i>0;
    }

    @Override
    public boolean updateUser(TUser tUser) {
        int i=userDao.updateUser(tUser);
        return i>0;
    }

    @Override
    public boolean deleteUser(String featid) {
        int i=userDao.deleteUser(featid);
        return i>0;
    }
}
