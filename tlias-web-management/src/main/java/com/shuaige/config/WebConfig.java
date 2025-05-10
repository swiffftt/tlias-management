package com.shuaige.config;

import com.shuaige.interceptor.DemoInterceptor;
import com.shuaige.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 */
@Configuration// ① 声明为Spring配置类
public class WebConfig implements WebMvcConfigurer {

//      @Autowired
//      private  DemoInterceptor demoInterceptor;

//    @Autowired
//    private TokenInterceptor tokenInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(demoInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**")//拦截所有请求
//                .excludePathPatterns("/login");//不拦截请求

//    }
}
