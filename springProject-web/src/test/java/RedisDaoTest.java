import com.alibaba.fastjson.JSON;
import com.wt.common.SpringCatch;
import com.wt.core.IDUtils;
import com.wt.dao.Impl.RedisDaoImpl;
import com.wt.manager.RedisManager;
import com.wt.model.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class RedisDaoTest {

    @Autowired
    private RedisDaoImpl redisDao;
    @Autowired
    private RedisManager redisManager;

    @Test
    public void testRedisDaoImpl(){
        TUser tUser=SpringCatch.getInstance().applicationContext().getBean("tUser",TUser.class);
        tUser.setFeatid(IDUtils.getUUID());
        tUser.setUserid("iriya");
        tUser.setPassword("000000");
        tUser.setUsername("伊利亚");
        tUser.setAge(17);
        System.out.println("save:========"+redisDao.saveUser(tUser));
        System.out.println(JSON.toJSONString(redisDao.getUser("iriya")));
    }

    @Test
    public void testJedis(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.auth("wutong");
        System.out.println(jedis.ping());
    }

    @Test
    public void testRedisManager_String() throws InterruptedException {
//        redisManager.set("time","2018-02-02");
//        System.out.println(redisManager.get("time"));
//        redisManager.set("number", "10");
//        System.out.println(redisManager.incrBy("number",8L));
//        System.out.println(redisManager.decrBy("number",7L));
        //redisManager.set("number_time","2018-01-01",100L);
        //TimeUnit.SECONDS.sleep(10);
        System.out.println("time:"+redisManager.getExpire("number"));
    }

    @Test
    public void testRedisManager_map(){
//        System.out.println(redisManager.hget("map","field1"));
        //redisManager.hset("cat","name","小仓唯");
//        System.out.println(redisManager.hget("map","field1"));
        redisManager.hset("cat","age","10");
        redisManager.hdecr("cat","age",5);

        System.out.println(redisManager.hget("cat","age"));
    }

    @Test
    public void testRedisManager_list(){
        System.out.println(redisManager.lGet("list",0,-1));
        System.out.println(redisManager.lRemove("list",0,"1"));
        System.out.println(redisManager.lGet("list",0,-1));
    }




}
