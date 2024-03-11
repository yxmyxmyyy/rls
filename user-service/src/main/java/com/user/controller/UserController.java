package com.user.controller;

import com.api.domain.dto.UserFindDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.api.domain.po.User;
import com.common.domain.R;
import com.user.service.IUserService;
import com.common.result.TokenData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    //查询全部
    @PostMapping("/find")
    public R<Page<User>> find(@RequestBody UserFindDTO userFindDTO,@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
        try {
            return R.ok(userService.find(userFindDTO,pageNum, pageSize));
        } catch (Exception e) {
            return R.error(500, "服务器内部错误");
        }
    }

    //批量删除
    @DeleteMapping("/delete")
    public R<String> delete(@RequestBody List<Serializable> ids) {
        try {
            userService.removeByIds(ids);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.error(500, "服务器内部错误");
        }
    }

    //删除一个
    @DeleteMapping("/deleteOne/{id}")
    public R<String> deleteOne(@PathVariable Serializable id) {
        try {
            userService.removeById(id);
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.error(500, "服务器内部错误");
        }
    }

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


//    @PostMapping("/roles")
//    public boolean roles(@RequestBody TokenData tokenData) {
//        return userService.roles(tokenData);
//    }

}
