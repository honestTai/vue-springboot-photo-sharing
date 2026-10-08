package com.app.server.base;

import lombok.Data;

/**
 * 接口返回类
 */

@Data
public class Result {

    /**
     * 返回code
     */
    private Integer code;

    /**
     * 返回msg
     */
    private String msg;

    /**
     * 返回内容
     */
    private Object data;

    /**
     * new方法
     * @param code code
     * @param msg 内容
     * @param data 实体Object
     */
    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
