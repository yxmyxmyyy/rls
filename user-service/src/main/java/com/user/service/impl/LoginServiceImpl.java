package com.user.service.impl;

import com.api.enums.UserEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.api.domain.po.User;
import com.user.mapper.UserMapper;
import com.user.service.ILoginService;
import com.common.result.TokenData;
import com.common.util.JwtHelper;
import com.common.util.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    public TokenData login(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", user.getUsername());
        qw.eq("password", DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        qw.eq("status", 1);
        User u = getOne(qw);
        if (u == null) return null;
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (u.getPassword().equals(password)) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", u.getId());
            map.put("username", u.getUsername());
            map.put("roles", u.getRoles());
            map.put("warehouseId", u.getWarehouseId());
            TokenData tokenData = JwtHelper.createToken("payload", map);
            tokenData.setWarehouseId(u.getWarehouseId());
            tokenData.setRoles(new ArrayList<>(List.of(u.getRoles().toString())));
            tokenData.setUsername(u.getUsername());
            return tokenData;
        }
        return null;
    }

    public User getUserInfoByToken(String token) {
        User user = new User();
        Map<String, Object> map = JwtHelper.decode(token, "payload").asMap();
        Long id = (Long) map.get("id");
        user.setId(id);
        String name = (String) map.get("name");
        user.setUsername(name);
        Integer role = (Integer) map.get("role");
        user.setRoles(role);
        return user;
    }

    @Override
    public TokenData refresh(String refresh) {
        if (refresh == null) return null;
        if (JwtHelper.verify(refresh)) return null;
        Map<String, Object> map = JwtHelper.decode(refresh, "payload").asMap();
        return JwtHelper.createToken("payload", map);
    }

    //修改密码
    @Override
    public boolean updatePassword(User user) {
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setId(UserContext.getUser().getId());
        user.setPassword(password);
        return updateById(user);
    }

}

