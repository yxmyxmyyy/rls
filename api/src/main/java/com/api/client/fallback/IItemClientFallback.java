package com.api.client.fallback;

import com.api.client.IItemClient;
import com.api.client.IVehicleClient;
import com.api.domain.dto.ItemDTO;
import com.api.domain.dto.ItemStockDTO;
import com.api.domain.po.Vehicle;
import com.api.domain.po.VehicleLoad;
import com.common.domain.R;
import com.common.exception.BadRequestException;
import com.common.exception.BizIllegalException;
import com.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
public class IItemClientFallback implements FallbackFactory<IItemClient> {
    @Override
    public IItemClient create(Throwable throwable) {
        return new IItemClient() {
            @Override
            public boolean deductStock(Integer id, List<VehicleLoad> vehicleLoads) {
                return false;
            }

            @Override
            public boolean insertOrUpdate(ItemDTO itemDTO) {
                return false;
            }

            @Override
            public List<ItemStockDTO> findAllProductStocks() {
                return Collections.emptyList();
            }
        };
    }
}
