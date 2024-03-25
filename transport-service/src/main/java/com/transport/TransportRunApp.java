package com.transport;

import com.api.config.DefaultFeignConfig;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@Import(RocketMQAutoConfiguration.class)
@ComponentScans({
        @ComponentScan("com.common.config"),
        @ComponentScan("com.api.config"),
        @ComponentScan("com.common.advice")
})
@EnableFeignClients(basePackages = "com.api.client",
defaultConfiguration = DefaultFeignConfig.class)
@EnableAsync
public class TransportRunApp {
    public static void main(String[] args) {
        SpringApplication.run(TransportRunApp.class, args);
    }
}
