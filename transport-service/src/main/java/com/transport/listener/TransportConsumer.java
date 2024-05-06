package com.transport.listener;

import com.alibaba.fastjson.JSON;
import com.api.domain.dto.TransportDTO;
import com.api.domain.vo.TransportVO;
import com.transport.service.ITransportService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "transport-topic", consumerGroup = "Transport-group",messageModel = MessageModel.CLUSTERING, maxReconsumeTimes = 2)
public class TransportConsumer implements RocketMQListener<TransportDTO>, RocketMQPushConsumerLifecycleListener {

    private final ITransportService transportService;

    @Override
    public void onMessage(TransportDTO transportDTO) {

    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt msg = msgs.get(0); // 假设一次只处理一个消息
            byte[] body = msg.getBody();
            TransportDTO transportDTO = JSON.parseObject(body, TransportDTO.class);// 反序列化
            try {
                transportService.processNewTransport(transportDTO);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                transportDTO.getTransportVO().setDescription(e.getMessage());// 记录失败原因
                int reconsumeTimes = msg.getReconsumeTimes();
                if (reconsumeTimes >= 2) { // 0是第一次消费，1和2是接下来的两次重试
                    // 达到最大重试次数后的特定逻辑
                    handleMaxRetryReached(transportDTO);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 防止进一步重试
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER; // 否则请求稍后重试
            }
        });
    }

    private void handleMaxRetryReached(TransportDTO msg) {
        System.out.println("重试次数达到上限，消息处理失败");
        transportService.processFailedTransport(msg);
    }
}
