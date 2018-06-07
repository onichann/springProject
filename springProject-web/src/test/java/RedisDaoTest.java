import com.wt.dao.RedisDao;
import com.wt.model.T_USER;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Test
    public void test(){
        redisDao.save();
        System.out.println(redisDao.getUser(1));

//        Jedis jedis=new Jedis("127.0.0.1",6397);
//        jedis.auth("wutong");
//        System.out.println(jedis.ping());
    }




}
