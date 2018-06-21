package com.wt.mapper.dao;

import com.wt.model.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    public TUser selectOneUser(@Param("userName") String username, @Param("passWord") String password);
    public List<TUser> selectAllUsers();
    public TUser selectUserById(@Param("featid") String Id);
    public int addUser(TUser user);
    public int updateUser(TUser user);
    public int deleteUser(@Param("id") String id);
}
