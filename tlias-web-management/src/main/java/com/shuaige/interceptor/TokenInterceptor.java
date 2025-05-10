package com.shuaige.interceptor;

import com.shuaige.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 令牌校验的拦截器
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //1.获取到请求路径
//        String requestURI =  request.getRequestURI();//  /login
//
//        //2.判断是否登录请求，如果路径中包含/login，则放行
//        if(requestURI.contains("/login")){
//            log.info("登录请求，放行");
//            return true;
//        }

        //3.获取请求头中的token
        String token = request.getHeader("token");

        //4.判断token是否存在，如果不存在，说明用户没有登录，则返回未登录信息（响应401状态码），如果存在，则放行
        if(token==null || token.isEmpty()){
            log.info("用户为未登录，请先登录");
            response.setStatus(401);
            return false;
        }

        //5.如果token存在，校验令牌，如果校验失败，返回错误信息（响应401状态码）
        try {
            JwtUtils.parseToken(token);
            log.info("令牌校验成功");
        }
        catch (Exception e){
            log.info("令牌校验失败");
            response.setStatus(401);
            return false;
        }

        //6.如果校验成功，则放行
        log.info("令牌合法，放行");
        return true;
    }
}
