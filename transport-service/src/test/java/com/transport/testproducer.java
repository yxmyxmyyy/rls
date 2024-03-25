package com.transport;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

import static java.lang.Thread.sleep;

@SpringBootTest
class testproducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    @Async
    public void testSimpleMsg() throws Exception {
        for (int i=0;i<10;i++){
            System.out.println(i);
        }
        for (int i=0;i<10;i++){
            System.out.println(i);
        }
    }
    @Test
    public void test2() throws InterruptedException {
        sleep(5000);
        System.out.println("222");
    }




}

