package com.transport.listener;

import com.api.domain.dto.TransportDTO;
import com.transport.service.ITransportService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "%DLQ%Transport-group", consumerGroup = "DLQConsumerGroup", maxReconsumeTimes = 2)
public class DLQConsumer implements RocketMQListener<TransportDTO> {
    private final ITransportService TransportService;
    @Override
    public void onMessage(TransportDTO transportDTO) {
        TransportService.processFailedTransport(transportDTO);
        // 处理死信消息的逻辑
        System.out.println("你已经死了: " + transportDTO);
    }
}
