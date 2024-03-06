package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.api.domain.po.User;
import com.common.result.TokenData;

import java.io.Serializable;
import java.util.List;


public interface IUserService extends IService<User> {
    boolean insert(User user);

    boolean del(Serializable id, String cookie);

    boolean updateUser(User user, String cookie);

    List<User> teacherList();

    List<User> studentList();

    boolean roles(TokenData tokenData);
}
