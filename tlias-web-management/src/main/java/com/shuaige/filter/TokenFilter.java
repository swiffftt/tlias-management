package com.shuaige.filter;

import com.shuaige.utils.CurrentHolder;
import com.shuaige.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")// 拦截所有请求
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;//强制类型转换
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取到请求路径
        String requestURI =  request.getRequestURI();//  /login

        //2.判断是否登录请求，如果路径中包含/login，则放行
        if(requestURI.contains("/login")){
            log.info("登录请求，放行");
            filterChain.doFilter(request,response);//放行
            return;
        }

        //3.获取请求头中的token
        String token = request.getHeader("token");

        //4.判断token是否存在，如果不存在，说明用户没有登录，则返回未登录信息（响应401状态码），如果存在，则放行
        if(token==null || token.isEmpty()){
            log.info("用户为未登录，请先登录");
            response.setStatus(401);
            return;
        }

        //5.如果token存在，校验令牌，如果校验失败，返回错误信息（响应401状态码）
        try {
            Claims claims = JwtUtils.parseToken(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);//存入ThreadLocal
            log.info("当前登录员工ID:{},将其转入ThreadLocal", empId);
        }
        catch (Exception e){
            log.info("令牌校验失败");
            response.setStatus(401);
            return;
        }

        //6.如果校验成功，则放行
        log.info("令牌合法，放行");
        filterChain.doFilter(request,response);//放行

        //删除ThreadLocal中的数据
        CurrentHolder.remove();
    }
}
