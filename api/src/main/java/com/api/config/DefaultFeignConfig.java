package com.api.config;

import com.api.client.fallback.IItemClientFallback;
import com.api.client.fallback.IVehicleClientFallback;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public IVehicleClientFallback VehicleClientFallback(){
        return new IVehicleClientFallback();
    }

    @Bean
    public IItemClientFallback ItemClientFallback(){
        return new IItemClientFallback();
    }
}
