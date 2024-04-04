package com.transport.controller;


import cn.hutool.core.util.IdUtil;
import com.api.client.IItemClient;
import com.api.domain.dto.ItemStockDTO;
import com.api.domain.po.Transport;
import com.api.domain.po.TransportLog;
import com.api.domain.vo.CountVO;
import com.api.domain.vo.TransportVO;
import com.api.domain.vo.WeekCountVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.domain.R;
import com.common.exception.BadRequestException;
import com.transport.service.ITransportLogService;
import com.transport.service.ITransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/Statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final ITransportService TransportService;

    private final ITransportLogService TransportLogService;

    private final IItemClient itemClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("findAllProductStocks")
    public R<List<ItemStockDTO>> findAllProductStocks() {
        String key = "findAllProductStocks:";

        // 尝试从缓存获取数据
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<ItemStockDTO> cache = (List<ItemStockDTO>) valueOperations.get(key);

        if (cache != null) {
            // 缓存命中，直接返回缓存数据
            return R.ok(cache);
        }

        // 执行分页和条件查询
        List<ItemStockDTO> result = itemClient.findAllProductStocks();

        // 生成10到20分钟之间的随机数
        int expirationTime = ThreadLocalRandom.current().nextInt(4, 6);

        // 将查询结果存入缓存，设置过期时间为随机的10到20分钟
        valueOperations.set(key, result, expirationTime, TimeUnit.MINUTES);

        return R.ok(result);
    }

    @GetMapping("/count")
    public R<CountVO> count() {
        String key = "count:";

        // 尝试从缓存获取数据
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        CountVO cache = (CountVO) valueOperations.get(key);

        if (cache != null) {
            // 缓存命中，直接返回缓存数据
            return R.ok(cache);
        }
        CountVO count = new CountVO();
        count.setNow(inProgress());
        count.setInProgress(countOrdersCreatedAtToday());
        count.setEndProgress(countOrdersEnd());
        count.setErrorProgress(countOrdersError());

        // 生成10到20分钟之间的随机数
        int expirationTime = ThreadLocalRandom.current().nextInt(4, 6);

        // 将查询结果存入缓存，设置过期时间为随机的10到20分钟
        valueOperations.set(key, count, expirationTime, TimeUnit.MINUTES);

        return R.ok(count);
    }

    @GetMapping("/weekCount")
    public R<WeekCountVO> weekCount(){

        String key = "weekCount:";

        // 尝试从缓存获取数据
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        WeekCountVO cache = (WeekCountVO) valueOperations.get(key);

        if (cache != null) {
            // 缓存命中，直接返回缓存数据
            return R.ok(cache);
        }

        // 执行分页和条件查询
        WeekCountVO result = TransportService.collectWeeklyOrderData();

        // 生成10到20分钟之间的随机数
        int expirationTime = ThreadLocalRandom.current().nextInt(4, 6);

        // 将查询结果存入缓存，设置过期时间为随机的10到20分钟
        valueOperations.set(key, result, expirationTime, TimeUnit.MINUTES);

        return R.ok(result);
    }




    //进行中订单数量统计
    @GetMapping("/inProgress")
    public Long inProgress() {
        QueryWrapper<Transport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "进行中");
        return TransportService.count(queryWrapper);
    }

    @GetMapping("/newProgress")
    public Long countOrdersCreatedAtToday() {
        // 获取今天的开始时间和结束时间
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        QueryWrapper<Transport> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("created_at", startOfDay)
                .le("created_at", endOfDay);

        return TransportService.count(queryWrapper);
    }

    @GetMapping("/endProgress")
    public Long countOrdersEnd() {
        // 获取今天的开始时间和结束时间
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        QueryWrapper<Transport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "已完成");
        queryWrapper.ge("updated_at", startOfDay)
                .le("updated_at", endOfDay);

        return TransportService.count(queryWrapper);
    }

    @GetMapping("/errorProgress")
    public Long countOrdersError() {
        // 获取今天的开始时间和结束时间
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        QueryWrapper<Transport> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status", "进行中");
        queryWrapper.ne("status", "已完成");
        queryWrapper.ge("updated_at", startOfDay)
                .le("updated_at", endOfDay);

        return TransportService.count(queryWrapper);
    }
}
