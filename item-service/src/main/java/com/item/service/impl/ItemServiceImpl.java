package com.item.service.impl;


import com.api.domain.po.Item;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.item.mapper.ItemMapper;
import com.item.service.IItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {
}
