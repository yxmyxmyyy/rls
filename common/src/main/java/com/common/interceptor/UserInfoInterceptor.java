package com.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.common.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;


public class UserInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的用户信息
        String id = request.getHeader("id");
        String username = request.getHeader("username");
        String warehouseId = request.getHeader("warehouseId");
        // 2.判断是否为空
        if (StrUtil.isNotBlank(id)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(Long.valueOf(id));
            userInfo.setUsername(username);
            userInfo.setWarehouseId(Integer.valueOf(warehouseId));
            UserContext.setUser(userInfo);
        }
        // 3.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserContext.removeUser();
    }
}
