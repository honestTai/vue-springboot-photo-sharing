package com.app.server.api;

import com.app.server.EncrptInfo;
import com.app.server.base.Result;
import com.app.server.entity.UserInfo;
import com.app.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 */
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户登录
     * @param userInfo 用户基本信息
     * @return
     */
    @PostMapping("login")
    @EncrptInfo(isEnOrDe = true, paramName = "userInfo")
    public Result login(@RequestBody UserInfo userInfo) {
        return userInfoService.login(userInfo);
    }
}
