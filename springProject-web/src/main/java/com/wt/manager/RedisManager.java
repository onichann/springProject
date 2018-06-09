package com.wt.manager;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisManager {

    @Resource(name="redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key
     *            键
     * @param time
     *            时间(秒)
     * @return
     */
    public boolean expire(String key, long time){
        try {
            Assert.notNull(key,"key is null");
            return  redisTemplate.expire(key,time,TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key
     *            键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     *            键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key
     * @return
     */
    public boolean delKey(String...key){
        try {
            if(ArrayUtils.isNotEmpty(key)){
                if(key.length==1){
                    redisTemplate.delete(key[0]);
                }else{
                    redisTemplate.delete(Arrays.asList(key));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
