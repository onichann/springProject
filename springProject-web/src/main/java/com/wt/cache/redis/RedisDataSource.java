package com.wt.cache.redis;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
    public abstract ShardedJedis getRedisClient();
    public abstract void returnResource(ShardedJedis shardedJedis);
    public abstract void returnResource(ShardedJedis shardedJedis,boolean broken);
}
