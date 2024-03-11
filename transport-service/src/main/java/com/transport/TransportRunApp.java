package com.transport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScans({
        @ComponentScan("com.common.config"),
        @ComponentScan("com.api.config")
})
@EnableFeignClients(basePackages = "com.api.client")
public class TransportRunApp {
    public static void main(String[] args) {
        SpringApplication.run(TransportRunApp.class, args);
    }
}
