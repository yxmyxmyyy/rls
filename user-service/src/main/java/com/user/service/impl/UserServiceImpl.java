package com.user.service.impl;

import com.api.domain.dto.UserFindDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.api.domain.po.User;
import com.common.domain.R;
import com.common.exception.BadRequestException;
import com.user.mapper.UserMapper;
import com.user.service.IUserService;
import com.common.result.TokenData;
import com.common.util.JwtHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    public Page<User> find( UserFindDTO userFindDTO, Integer pageNum, Integer pageSize) {
        // 创建Page对象，其中current是当前页数，size是每页显示记录的数量
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> qw = new QueryWrapper<>();
        // 根据条件添加查询条件，这里省略了空值检查，实际使用时应该加上
        if (userFindDTO.getUsername() != null && !userFindDTO.getUsername().isEmpty()){
            qw.eq("username", userFindDTO.getUsername());
        }
        if (userFindDTO.getStatus() != null) {
            qw.eq("status", userFindDTO.getStatus());
        }
        if (userFindDTO.getWarehouseId() != null) {
            qw.eq("warehouse_id", userFindDTO.getWarehouseId());
        }
        if (userFindDTO.getPhone() != null) {
            qw.eq("phone", userFindDTO.getPhone());
        }
        qw.select("id", "username", "status", "warehouse_id", "phone", "role", "sex" ,"create_time");
        // 执行分页和条件查询
        return page(page, qw);
    }
    public boolean insert(User user) {
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        return saveOrUpdate(user);
    }

    //判断用户是否存在
    public boolean isUser(String user){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", user);
        User one = getOne(qw);
        if (one != null) {
            return true;
        };
        return false;
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

