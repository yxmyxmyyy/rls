package com.api.client.fallback;

import com.api.client.IVehicleClient;
import com.api.domain.po.Vehicle;
import com.common.exception.BadRequestException;
import com.common.exception.BizIllegalException;
import com.common.util.CollUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
public class IVehicleClientFallback implements FallbackFactory<IVehicleClient> {
    @Override
    public IVehicleClient create(Throwable throwable) {
        return new IVehicleClient() {
            @Override
            public List<Vehicle> findAll(){
                return Collections.emptyList();
            }

            @Override
            public boolean update(List<Vehicle> vehicle) {
                return false;
            }

            @Override
            public boolean use(List<Long> idList) {
                return false;
            }
        };
    }
}
