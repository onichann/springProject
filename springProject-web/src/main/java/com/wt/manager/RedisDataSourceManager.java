package com.wt.manager;

import com.wt.cache.redis.RedisDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;

@Component
public class RedisDataSourceManager {

    private static Logger logger=Logger.getLogger(RedisDataSourceManager.class);

    @Autowired
    private RedisDataSource redisDataSource;

    /**
     * 设值
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            redisDataSource.returnResource(shardedJedis);
        }
        return result;
    }

    /**
     * 获取单个值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        try {
            result = shardedJedis.get(key);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            redisDataSource.returnResource(shardedJedis);
        }
        return result;
    }
}
