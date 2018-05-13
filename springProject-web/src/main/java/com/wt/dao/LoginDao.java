package com.wt.dao;

import com.wt.model.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertLoginLog(LoginLog log){
        String sqlStr="insert into loginlog(userid,ip,logintime) values(?,?,?)";
        Object[] args=new Object[]{log.getUserId(),log.getIp(),log.getLoginDate()};
        jdbcTemplate.update(sqlStr,args);
    }
}
