package com.wt.cache.redis;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisManager {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            Assert.notNull(key, "key is null");
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public boolean delKey(String... key) {
        try {
            if (ArrayUtils.isNotEmpty(key)) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete(Arrays.asList(key));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    ========================String===============================

    /**
     * String 普通缓存获取
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * String 普通缓存放入
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            // redisTemplate.setValueSerializer(new StringRedisSerializer());
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key   普通缓存放入并设置时间
     * @param value
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key
     * @param delta
     * @return
     */
    public long incrBy(String key, long delta) {
        Assert.isTrue(delta >= 0, "递增因子需大于0");
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param delta
     * @return
     */
    public long decrBy(String key, long delta) {
        Assert.isTrue(delta >= 0, "递减因子需大于0");
        return redisTemplate.opsForValue().increment(key, -delta);
    }

//    =========================Map===========================


    /**
     * 获取指定map的key对应的value
     *
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置hashmap
     *
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hashmap 并设置时间
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            boolean f = expire(key, time);
            if (!f) throw new RuntimeException("时间设置失败");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key
     * @param item
     * @param value
     * @param time  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            boolean f = expire(key, time);
            if (!f) throw new RuntimeException("时间设置失败");
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash中的值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hdel(String key, Object... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key
     * @param item
     * @param delta
     * @return
     */
    public double hincr(String key, String item, double delta) {
        Assert.isTrue(delta >= 0, "递增因子需大于0");
        return redisTemplate.opsForHash().increment(key, item, delta);
    }

    /**
     * hash递减
     *
     * @param key
     * @param item
     * @param delta
     * @return
     */
    public double hdecr(String key, String item, double delta) {
        Assert.isTrue(delta >= 0, "递减因子需大于0");
        return redisTemplate.opsForHash().increment(key, item, -delta);
    }

//   ====================set=======================

    /**
     * 将数据放入set缓存
     *
     * @param key
     * @param values
     * @return
     */
    public long sSet(String key, Object... values) {

        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将数据放入set缓存 设置时间
     *
     * @param key
     * @param time
     * @param values
     * @return
     */
    public long sSet(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            boolean f = expire(key, time);
            if (!f) throw new RuntimeException("时间设置失败");
            return count;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断value 在key中是否存在
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取set缓存长度
     *
     * @param key
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除set中值value
     *
     * @param key
     * @param values
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//    ==========================List======================

    /**
     * 获取list缓存内容
     *
     * @param key
     * @param start
     * @param end
     * @return 结束0到-1代表所有值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取list缓存长度
     *
     * @param key
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, Object value,long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            boolean f=expire(key,time);
            if(!f) throw new RuntimeException("时间设置失败");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  将list放入缓存
     * @param key
     * @param values
     * @return
     */
    public boolean lSet(String key,List<Object> values){
        try {
            redisTemplate.opsForList().rightPushAll(key,values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  将list放入缓存
     * @param key
     * @param values
     * @param time 时间
     * @return
     */
    public boolean lSet(String key,List<Object> values,long time){
        try {
            redisTemplate.opsForList().rightPushAll(key,values);
            boolean f=expire(key,time);
            if(!f) throw new RuntimeException("时间设置失败");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移除N个值为value
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lRemove(String key,long count,Object value){
        try {
            Long remove=redisTemplate.opsForList().remove(key,count,value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean lUpdateIndex(String key,long index,Object value){
        try {
            redisTemplate.opsForList().set(key,index,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
