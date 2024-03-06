package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.api.domain.po.User;
import com.common.result.TokenData;

public interface ILoginService extends IService<User> {

    TokenData login(User user);

    User getUserInfoByToken(String token);

    TokenData refresh(String refresh);

    boolean updatePassword(User user);
}
