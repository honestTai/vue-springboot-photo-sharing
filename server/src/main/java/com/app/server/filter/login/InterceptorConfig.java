package com.app.server.filter.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * 登录拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor())
                // 拦截所有请求,决定是否需要登录
                .addPathPatterns("/**");
    }

    /**
     * 是否登录拦截器
     * @return
     */
    @Bean
    public LoginFilterAspect userLoginInterceptor() {
        return new LoginFilterAspect();
    }

}
