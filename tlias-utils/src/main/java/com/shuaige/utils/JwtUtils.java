package com.shuaige.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtils {

    // 签名密钥（与原始测试类保持一致）
    private static final String SECRET_KEY = "c2h1YWlnZQ==";
    
    // 过期时间：12小时（毫秒单位）
    private static final long EXPIRATION_TIME = 12 * 3600 * 1000L;

    /**
     * 生成JWT令牌
     * @param claims 自定义声明内容
     * @return 生成的JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 设置签名算法和密钥
                .addClaims(claims) // 添加自定义声明
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .compact(); // 生成令牌
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌字符串
     * @return 包含声明内容的Claims对象
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 设置签名密钥
                .parseClaimsJws(token) // 解析令牌
                .getBody(); // 获取声明内容
    }

}