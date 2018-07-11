import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class ActiveMQTest {

    @Autowired
    private  JmsTemplate jmsTemplate;

    @Resource(name = "test-queue")
    private Destination destination;

    @Test
    public void test(){
        // 发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("spring activeMQ");
                System.out.println("测试发送消息:"+textMessage);
                return textMessage;
            }
        });
    }
}
