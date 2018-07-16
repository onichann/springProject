package com.wt.activemq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class Receiver2 {
    public static void main(String[] args) throws JMSException, IOException {
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://localhost:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();

        /** 每个消费者clientId唯一 **/
        connection.setClientID("consumer1");

        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.使用Session创建一个Destination，和消息的发送端一致
        Topic topic = session.createTopic("test-topic");
        //6.使用Session创建一个服务的消费者Consumer
//        MessageConsumer consumer = session.createConsumer(topic);
        /** 修改 **/
        MessageConsumer consumer = session.createDurableSubscriber(topic, "consumer1");
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
//        配置activemq.xml文件
//        在broker的末尾添加一句关于持久化的配置 persistent=”true”

    }
}
