package com.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.api.domain.po.User;
import com.user.service.IUserService;
import com.common.result.TokenData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    // 新增用户
    @PostMapping("/insert")
    public boolean insert(@RequestBody User user) {
        return userService.insert(user);
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public boolean del(@PathVariable Serializable id) {
        return userService.removeById(id);
    }

    // 修改用户
    @PutMapping("/update")
    public boolean updatePassWd(@RequestBody User user) {
        return userService.updateById(user);
    }

    // 分页查询
    @GetMapping("/find")
    public IPage<User> find(Integer pageNum, Integer pageSize) {
        IPage<User> ip = new Page<>(pageNum, pageSize);
        return userService.page(ip);
    }

//    @PostMapping("/roles")
//    public boolean roles(@RequestBody TokenData tokenData) {
//        return userService.roles(tokenData);
//    }

}
