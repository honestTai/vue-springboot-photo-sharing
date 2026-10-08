package com.app.server.filter.globalExceptionHandler;

import com.app.server.base.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 拦截所有接口返回
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义的错误
     *
     * @param e
     * @return
     */
    @ResponseBody
    public Result handleException(Result e) {
        return new Result(e.getCode(), e.getMsg(), e.getData());
    }

    /**
     * 其他错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        String msg = e.getMessage();
        return new Result(500,"系统错误",null);
    }

    /**
     * 空指值错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public Result nullPointerException(NullPointerException e) {
        return new Result(500,"空指针",e.getLocalizedMessage());
    }

    /**
     * 拦截错误
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public Result exceptionHandler(RuntimeException e) {
        return new Result(500,e.getMessage(),null);
    }
}
