package com.wt.dao;

import com.wt.common.SpringCatch;
import com.wt.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public int getMatchCount(String userName,String password){
      String sqlStr="select count(*) from t_user where username=? and password=?";
      return jdbcTemplate.queryForObject(sqlStr,new Object[]{userName,password},Integer.class);
    }

    public User findUserByUserName(String userName){
        String sqlStr="select * from t_user where username=?";

        ApplicationContext applicationContext1=SpringCatch.getInstance().applicationContext();
        ApplicationContext applicationContext2=SpringCatch.getInstance().applicationContext();
        System.out.println(applicationContext1==applicationContext1);

        final User user = SpringCatch.getInstance().applicationContext().getBean("user",User.class);
        jdbcTemplate.query(sqlStr, new Object[]{userName}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                user.setUserId(rs.getString("userid"));
                user.setUserName(rs.getString("username"));
                user.setCredits(rs.getInt("credits"));
            }
        });
        return user;
    }

    public void updateLoginInfo(User user){
        String strSql="update t_user set lastvisit=?,lastip=?,credits=? where userid=?";
        jdbcTemplate.update(strSql,new Object[]{user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()});
    }


}
