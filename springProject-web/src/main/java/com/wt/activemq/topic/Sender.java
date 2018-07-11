package com.wt.activemq.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//http://localhost:8161/admin/      admin/admin

public class Sender {
    public static void main(String[] args) throws JMSException {
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://localhost:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();

        /** 每一个生产者的clientId必须唯一 **/
        connection.setClientID("client1");

        //3、开启连接
        connection.start();
        // 4.使用connction创建回话Session
        /**
         ** 1.是否开启事务（分布式事务(跨数据库)）一般不使用事务,保证数据的最终一致，可以使用消息队列实现，如果第一个参数为true，第二个参数自动忽略
         * 2.如果不开启事务false，第二个参数表示消息的应答模式（1.自动应答 2：手动应答）一般是自动应答
         */
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Topic topic = session.createTopic("test-topic");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(topic);

        /** DeliveryMode设置为持久化 **/
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        //7、使用会话对象创建一个消息对象
        for (int i = 0; i < 5; i++) {
            TextMessage textMessage = session.createTextMessage("hello!test-queue"+i);
            System.out.println("ActiveMq 发送的消息:" + "hello!test-queue"+i);
            //8、发送消息
            producer.send(textMessage);
        }

        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }

}
