import com.alibaba.fastjson.JSON;
import com.wt.common.SpringCatch;
import com.wt.core.IDUtils;
import com.wt.daoImpl.RedisDaoImpl;
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

    @Test
    public void test(){
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
    public void test2(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        jedis.auth("wutong");
        System.out.println(jedis.ping());
    }
}
