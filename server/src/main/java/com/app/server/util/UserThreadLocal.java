package com.app.server.util;

import com.app.server.entity.UserInfo;

/**
 * 本地安全线程存入登入用户信息
 */
public class UserThreadLocal {

    private static ThreadLocal<UserInfo> local = new ThreadLocal<UserInfo>();

    /**
     * 设置用户信息
     *
     * @param user
     */
    public static void setUser( UserInfo user )
    {
        local.set( user );
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public static UserInfo getUser()
    {
        return local.get();
    }
}
