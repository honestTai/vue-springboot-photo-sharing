package com.app.server;

import com.app.server.entity.UserInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncrptInfo {
    /**
     * 加密还是解密
     * true 加密，在before
     * false 解密，在after
     */
    boolean isEnOrDe() default false;

    Class ParamClass() default UserInfo.class;

    String paramName() ;
}
