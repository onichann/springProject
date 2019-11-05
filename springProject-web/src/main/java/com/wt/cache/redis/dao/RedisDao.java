package com.wt.cache.redis.dao;

import com.wt.model.TUser;

public interface RedisDao {

    public abstract Boolean saveUser(TUser tUser);

    public abstract TUser getUser(String userId);
}
