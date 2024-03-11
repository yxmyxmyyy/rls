package com.user.service;

import com.api.domain.dto.UserFindDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.api.domain.po.User;
import com.common.result.TokenData;

import java.io.Serializable;
import java.util.List;


public interface IUserService extends IService<User> {
    boolean insert(User user);

    Page<User> find(UserFindDTO userFindDTO, Integer pageNum, Integer pageSize);

//    boolean roles(TokenData tokenData);
}
