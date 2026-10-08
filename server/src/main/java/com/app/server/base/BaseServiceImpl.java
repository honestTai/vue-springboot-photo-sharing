package com.app.server.base;

import com.app.server.dto.LoginUser;

/**
 * 基类接口
 */
public class BaseServiceImpl implements BaseService {

    /**
     * 500错误
     * @return 接口返回
     */
    public Result wrong() {
        return new Result(500, "系统错误", null);
    }

    /**
     * 接口操作成功，不返回内容
     * @return
     */
    public Result success() {
        return new Result(200, "操作成功", null);
    }

    /**
     * 接口操作成功，返回内容
     * @param data 内容
     * @return
     */
    public Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    /**
     * 登录成功
     * @param loginUser 登录成功返回
     * @return
     */
    public Result success(LoginUser loginUser) {
        return new Result(200, "登录成功", loginUser);
    }

    /**
     * 登录失败
     * @return
     */
    public Result loginFail() {
        return new Result(201, "账号密码错误", null);
    }

    /**
     * 自定义的错误
     * @param msg 错误信息
     * @return
     */
    public Result wrong(String msg) {
        return new Result(202, msg, null);
    }

    /**
     * 修改用户信息，重新登录
     * @return
     */
    public Result loginReload() {
        return new Result(203, "重新登录", null);
    }

    /**
     * 操作成功,自定义返回提示
     * @param msg 自定义提示内容
     * @return
     */
    public Result success(String msg) {
        return new Result(200, msg, null);
    }

}
