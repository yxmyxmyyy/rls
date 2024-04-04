package com.api.client;

import com.api.client.fallback.IVehicleClientFallback;
import com.api.config.DefaultFeignConfig;
import com.api.domain.po.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "vehicle-service",
configuration = DefaultFeignConfig.class,
fallbackFactory = IVehicleClientFallback.class)
public interface IVehicleClient {

    @GetMapping("/Vehicle/findAll")
    List<Vehicle> findAll();

    @PutMapping("/Vehicle/update")
    boolean update(@RequestBody List<Vehicle> vehicle);

    @PutMapping("/Vehicle/use")
    boolean use(@RequestBody List<Long> idList);

    @PostMapping("/Vehicle/findMore")
    List<Vehicle> findMore(@RequestBody List<Long> ids);

}
