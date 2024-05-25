package com.vehicle.listener;

import com.alibaba.fastjson.JSON;
import com.api.domain.dto.TransportDTO;
import com.api.domain.po.VehicleLog;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "VehicleLog-topic", consumerGroup = "VehicleLog-group",messageModel = MessageModel.CLUSTERING, maxReconsumeTimes = 2)
public class VehicleLogConsumer implements RocketMQListener<VehicleLog> {


    @Override
    public void onMessage(VehicleLog vehicleLog) {


        forwardMessageToMqtt(vehicleLog);


    }

    private void forwardMessageToMqtt(VehicleLog vehicleLog) {
        try {
            List<Double> point = List.of(vehicleLog.getLng(), vehicleLog.getLat(), vehicleLog.getTemp());
            Long taskId = vehicleLog.getTaskId();
            String publisherId = "mqtt_publisher_id";
            IMqttClient mqttClient = new MqttClient("tcp://127.0.0.1:1883", publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            mqttClient.connect(options);

            MqttMessage mqttMessage = new MqttMessage(point.toString().getBytes());
            mqttMessage.setQos(0);
            mqttMessage.setRetained(true);

            mqttClient.publish(taskId.toString(), mqttMessage);
            mqttClient.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
