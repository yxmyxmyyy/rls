package com.common.util;

import com.common.interceptor.UserInfo;

public class UserContext {
    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    /**
     * 获取当前登录用户信息
     *
     * @return 用户id
     */
    public static UserInfo getUser() {
        return tl.get();
    }

    /**
     * 保存当前登录用户信息到ThreadLocal
     *
     * @param userId 用户id
     */
    public static void setUser(UserInfo userId) {
        tl.set(userId);
    }


    /**
     * 移除当前登录用户信息
     */
    public static void removeUser() {
        tl.remove();
    }
}
