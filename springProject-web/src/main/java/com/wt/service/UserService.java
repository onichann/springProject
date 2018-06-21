package com.wt.service;

import com.wt.model.TUser;

import java.util.List;

public interface UserService {
    public TUser selectOneUser(String userName,String password);
    public List<TUser> selectAllUsers();
    public TUser selectUserById(String featid);
    public boolean addUser(TUser tUser);
    public boolean updateUser(TUser tUser);
    public boolean deleteUser(String featid);
}
