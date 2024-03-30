package com.vehicle.listener;
import io.moquette.BrokerConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class MqttBrokerServer {

    @PostConstruct
    public void startBroker() throws IOException {
        Properties configProps = new Properties();
        // 配置MQTT端口
        configProps.put("port", "1883");
        // 配置WebSocket端口
        configProps.put("websocket_port", "1884");
        // 允许WebSocket
        configProps.put("websocket_enabled", "true");
        // 更多配置可以根据需要设置

        IConfig config = new MemoryConfig(configProps);

        Server mqttBroker = new Server();
        mqttBroker.startServer(config);

        // 添加钩子，以确保在应用关闭时停止服务器
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Stopping MQTT broker...");
            mqttBroker.stopServer();
            System.out.println("MQTT broker stopped");
        }));

        System.out.println("MQTT broker started");
    }
}