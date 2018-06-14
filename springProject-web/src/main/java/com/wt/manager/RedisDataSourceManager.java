package com.wt.manager;

import com.wt.cache.redis.RedisDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisDataSourceManager {

    private static Logger logger=Logger.getLogger(RedisDataSourceManager.class);

    @Autowired
    private RedisDataSource redisDataSource;



}
