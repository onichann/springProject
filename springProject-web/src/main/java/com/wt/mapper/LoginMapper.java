package com.wt.mapper;

import com.wt.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginMapper {

    public User selectByEntity(@Param("userName") String username, @Param("password") String password);
    public List<User> selectAllUsers();
    public int addUserInfo(@Param("id")String id ,@Param("userName") String username, @Param("password") String password);
    public User seletOneUser(@Param("userid") String userid);
    public int updateUser(User user);
    public int deleteUser(@Param("id") String id);
}
