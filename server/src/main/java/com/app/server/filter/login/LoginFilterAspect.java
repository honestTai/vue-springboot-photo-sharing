package com.app.server.filter.login;


import com.app.server.entity.UserInfo;
import com.app.server.mapper.UserInfoMapper;
import com.app.server.util.JwtUtil;
import com.app.server.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截实现类
 */

public class LoginFilterAspect implements HandlerInterceptor {


    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 实现方法
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param object
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(LoginFilter.class)) {
            LoginFilter loginFilter = method.getAnnotation(LoginFilter.class);
            if (loginFilter.required()) {
                // 执行认证
                if (token == null || token.equals("")) {
                    throw new RuntimeException("请登录");
                } else {
                    //解密
                    UserInfo userInfo=userInfoMapper.selectUserInfoByNum(JwtUtil.getUsername(token));
                    boolean overdue = JwtUtil.verify(token, userInfo.getNum(), userInfo.getPwd());
                    // 验证 token
                    if (!overdue) {
                        throw new RuntimeException("请登录");
                    } else {
                        UserThreadLocal.setUser(userInfo);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
