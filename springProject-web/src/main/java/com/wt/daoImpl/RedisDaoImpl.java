package com.wt.daoImpl;

import com.alibaba.fastjson.JSON;
import com.wt.common.SpringCatch;
import com.wt.dao.RedisDao;
import com.wt.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

@Repository
public class RedisDaoImpl implements RedisDao {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Boolean saveUser(TUser tUser) {
      return  redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                boolean f=false;
                try {
                    redisConnection.set(redisTemplate.getStringSerializer().serialize("tUser_"+tUser.getUserid()),
                            redisTemplate.getStringSerializer().serialize(JSON.toJSONString(tUser)));
                    f=true;
                }catch (Exception e){
                    e.printStackTrace();
                }

                return f;
            }
        });
    }

    @Override
    public TUser getUser(String userId) {
        return redisTemplate.execute(new RedisCallback<TUser>() {
            @Override
            public TUser doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("tUser_" + userId);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String tUserStr = redisTemplate.getStringSerializer().deserialize(value);
                    TUser tUser = JSON.parseObject(tUserStr,TUser.class);
                    return tUser;
                }
                return null;
            }
        });
    }
}
