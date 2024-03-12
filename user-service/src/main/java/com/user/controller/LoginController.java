package com.user.controller;

import com.api.domain.po.User;
import com.common.exception.BadRequestException;
import com.user.service.ILoginService;
import com.common.result.TokenData;
import com.common.result.UserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
@RequiredArgsConstructor
public class LoginController {

    private final ILoginService ILoginService;

    @PostMapping("/login")
    public UserResult login(@RequestBody User user) {
        TokenData tokenData = ILoginService.login(user);
        if (tokenData != null) return UserResult.success(tokenData);
        throw new BadRequestException("用户名或密码错误");
    }

    @GetMapping("/get/{token}")
    public User getUserInfoByToken(@PathVariable String token) {
        return ILoginService.getUserInfoByToken(token);
    }

    @PostMapping("/refreshToken")
    public UserResult refresh(@RequestBody TokenData tokenData) {
        TokenData t = ILoginService.refresh(tokenData.getRefreshToken());
        if (t != null) return UserResult.success(t);
        return UserResult.fail();
    }

    @PutMapping("/updatePassword")
    public boolean updatePassword(@RequestBody User user) {
        return ILoginService.updatePassword(user);
    }
}
