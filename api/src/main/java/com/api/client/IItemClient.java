package com.api.client;

import com.api.client.fallback.IItemClientFallback;
import com.api.client.fallback.IVehicleClientFallback;
import com.api.config.DefaultFeignConfig;
import com.api.domain.dto.ItemDTO;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.common.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "item-service",
        configuration = DefaultFeignConfig.class,
        fallbackFactory = IItemClientFallback.class)
public interface IItemClient {

    @PutMapping("/item/deductStock")
    boolean deductStock(@RequestParam Integer id,@RequestBody List<VehicleLoad> vehicleLoads);

    //添加存在的库存，返回不存在的库存
    @PutMapping("/item/insertOrUpdate")
    boolean insertOrUpdate(@RequestBody ItemDTO itemDTO);

}
