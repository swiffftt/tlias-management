package com.shuaige;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

/**
 *  jwt令牌测试
 */
public class JwtTest {

    /**
     * 生成jwt令牌
     */
    @Test
    public void testGenerateJwt(){

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
             objectObjectHashMap.put("id",1);
             objectObjectHashMap.put("username","admin");
        String compact = Jwts.builder().signWith(SignatureAlgorithm.HS256, "c2h1YWlnZQ==")// 生成令牌
                .addClaims(objectObjectHashMap) // 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 设置过期时间))
                .compact();// 生成令牌
        System.out.println(compact);
    }
    /**
     * 解析jwt令牌
     */
    @Test
    public void testParseJWT(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc0NTk0NTg5OH0.eS0YVdTbTs0ziMZGsPyGCN9dShgJQOCR1Sn5_OXYCsE";
       Claims claims = Jwts.parser()
               .setSigningKey("c2h1YWlnZQ==")// 设置签名密钥
               .parseClaimsJws(token)// 解析令牌
               .getBody();//  获取自定义信息
        System.out.println(claims);
    }
}
