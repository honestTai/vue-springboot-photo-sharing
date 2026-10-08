package com.app.server.dto;

import com.app.server.entity.UserInfo;
import lombok.Data;

/**
 * 登录成功返回
 */
@Data
public class LoginUser {
    /**
     * jwt
     */
    private String loginUserCode;

    /**
     * UserInfo
     */
    private UserInfo userInfo;

    /**
     * new 方法
     * @param userInfo 用户信息实体
     * @param loginUserCode jwt
     */
    public LoginUser(UserInfo userInfo,String loginUserCode){
        this.loginUserCode=loginUserCode;
        this.userInfo=userInfo;
    }
}
