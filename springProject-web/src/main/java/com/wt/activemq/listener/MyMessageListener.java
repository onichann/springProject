package com.wt.activemq.listener;

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
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
