package com.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.api.domain.po.User;
import com.user.mapper.UserMapper;
import com.user.service.IUserService;
import com.common.result.TokenData;
import com.common.util.JwtHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    public boolean insert(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", user.getUsername());
        User one = getOne(qw);
        if (one != null) return false;
        //    默认密码为 123456
        String password = DigestUtils.md5DigestAsHex("x123456789".getBytes());
        user.setPassword(password);
        Date date = new Date();
        return save(user);
    }

//    @Override
//    public boolean roles(TokenData tokenData) {
//        String accessToken = tokenData.getAccessToken();
//        Integer roles = tokenData.getRoles();
//        Map<String, Object> map = JwtHelper.decode(accessToken, "payload").asMap();
//        Integer role = (Integer) map.get("role");
//        return role.toString().equals(roles.get(0));
//    }
}

