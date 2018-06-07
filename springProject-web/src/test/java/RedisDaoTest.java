import org.junit.Test;
import redis.clients.jedis.Jedis;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:applicationContext.xml")
public class RedisDaoTest {

//    @Autowired
//    private RedisDao redisDao;

    @Test
    public void test(){
//        redisDao.save();
//        System.out.println(redisDao.getUser(1));
//
        Jedis jedis=new Jedis("127.0.0.1",6397);
//        jedis.auth("wutong");
        System.out.println(jedis.ping());
    }




}
