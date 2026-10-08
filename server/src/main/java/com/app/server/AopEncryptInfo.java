package com.app.server;

import com.app.server.entity.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import static com.app.server.util.encryption.AesUtil.decryptText;
import static com.app.server.util.encryption.AesUtil.encryptText;


/**
 * 加密解密信息注解实现
 */
@Aspect
@Component
public class AopEncryptInfo {

    /**
     * 加密还是解密
     */
    private boolean way;

    /**
     * 切点的获取
     * 获取所有带有此注解的方法
     */
    @Pointcut("@annotation(com.app.server.EncrptInfo)")
    public void controllerAspect() {
    }

    /**
     * 前置通知
     * 对需要加密的信息，进行加密操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取注解中的参数值
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //获取注解的值
        EncrptInfo annotation = method.getAnnotation(EncrptInfo.class);
        // 获取注解Action的value参数的值
        boolean value = annotation.isEnOrDe();
        this.way = value;
        if (value) {
            // 获取所有参数的值
            Object[] args = joinPoint.getArgs();
            // 在方法签名中获取所有参数的名称
            String[] parameterNames = methodSignature.getParameterNames();
            encryptObject(args, parameterNames, annotation.paramName(), annotation.ParamClass(), this.way);
        }
    }

    /**
     * 后置通知
     * 对需要加密的信息，进行解密操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "controllerAspect()", returning = "returnValue")
    public void doAfter(JoinPoint joinPoint, Object returnValue) {
        // 获取注解中的参数值
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //获取注解的值
        EncrptInfo annotation = method.getAnnotation(EncrptInfo.class);
        // 获取注解Action的value参数的值
        boolean value = annotation.isEnOrDe();
        this.way = value;
        if (!value) {
            if (returnValue instanceof UserInfo) {
                UserInfo user = (UserInfo) returnValue;
                user.setPwd(decryptText(user.getPwd(), LOGIN_KEY));
                user.setNum(decryptText(user.getNum(), LOGIN_KEY));
            }

        }
    }

    public static final String LOGIN_KEY = "aesjavaapilogin";

    /**
     * 获取对象的参数信息
     *
     * @param args           参数数组
     * @param parameterNames 参数名称数组
     * @param paramName      需要获取的参数名称
     * @param clazz          需要转换的参数对象
     * @param <T>            泛型定义
     * @return 返回泛型定义的参数对象
     */
    public static <T> T getParamByName(Object[] args, String[] parameterNames, String paramName, Class<T> clazz) {
        // 根据参数名称拿到下标， 参数值的数组和参数名称的数组下标是一一对应的
        int index = ArrayUtils.indexOf(parameterNames, paramName);
        // 在参数数组中取出下标对应参数值
        Object obj = args[index];
        if (obj == null) {
            throw new RuntimeException("参数异常");
        }
        //Java对象
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        } else {
            throw new RuntimeException("参数异常");
        }
    }

    /**
     * 加密解密字段内容
     *
     * @param args           参数数组
     * @param parameterNames 参数名称数组
     * @param paramName      需要获取的参数名称
     * @param clazz          需要转换的参数对象
     * @param <T>            泛型定义
     */
    public static <T> void encryptObject(Object[] args, String[] parameterNames, String paramName, Class<T> clazz, boolean way) {
        T t = getParamByName(args, parameterNames, paramName, clazz);
        for (Field declaredField : t.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            //判断是否带有加密或者解密注解
            boolean encryptColumn = declaredField.isAnnotationPresent(EncryptColumn.class);
            if (encryptColumn) {
                //获取字段名称，然后判断该字段是否有值
                try {
                    String data = (String) declaredField.get(t);
                    if (Objects.nonNull(data)) {
                        //进行加密或者解密操作
                        if (way) {
                            declaredField.set(t, encryptText(data, LOGIN_KEY));
                        } else {
                            declaredField.set(t, decryptText(data, LOGIN_KEY));
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
