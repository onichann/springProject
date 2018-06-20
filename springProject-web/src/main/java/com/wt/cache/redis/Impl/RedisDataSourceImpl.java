package com.wt.cache.redis.Impl;

import com.wt.cache.redis.RedisDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public ShardedJedis getRedisClient() {
        ShardedJedis shardedJedis = null;

        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis;
        } catch (Exception e) {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return shardedJedis;
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis) {
//        shardedJedisPool.returnResource(shardedJedis);
        shardedJedis.close();
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
//        if (broken) {
//            shardedJedisPool.returnBrokenResource(shardedJedis);
//        } else {
//            shardedJedisPool.returnResource(shardedJedis);
//        }
        shardedJedis.close();
    }
    
}
