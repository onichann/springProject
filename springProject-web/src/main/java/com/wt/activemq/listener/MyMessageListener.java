package com.wt.activemq.listener;

import com.wt.exception.SpringWebException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        // 接收到消息
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            // 打印消息
            System.out.println("activeMQ监听器监听到消息:"+text);
            //只要被确认后  就会出队，接受失败没有确认成功，会在原队列里面
//            textMessage.acknowledge();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        throw new SpringWebException("抛出异常,模拟重发");
    }
}
