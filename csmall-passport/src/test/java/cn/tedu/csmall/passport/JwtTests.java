package cn.tedu.csmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTests {

    String secretKey = "sbbbcccccccccccccccccc";//不太简单的 难以预测的字符串

//    @Value("${xxx}")
//     String secretKey;

    //生成Jwt
    @Test
    void generate(){

        log.debug("当前secretKey为： "+secretKey);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id",9527);
        claims.put("name", "张三");

        String jwt = Jwts.builder()
                //Header
                .setHeaderParam("alg","HS256")
                .setHeaderParam("typ","JWT")
                //Payload
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+30*24*60*60*1000))//以毫秒为时间单位的
                //Verified
                .signWith(SignatureAlgorithm.HS256,secretKey)
                //生成
                .compact();
        System.out.println(jwt);
    }

//    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOjk1MjcsImV4cCI6MTY4NTA5MjE2NH0.CO0s71Q_tt2UaVP6TNIznMhutOwn8bvX8ax1zforIgg
//    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOjk1MjcsImV4cCI6MTY4NTA5MjIxMX0.0KivbJUcFL5nsGVOHKLw3LyncW6EI1dD0o42puyXBT4

    //解析Jwt
    @Test
    void parse(){
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NCwiZXhwIjoxNjg3NjkxNTA3LCJ1c2VybmFtZSI6IndhbmdrZWppbmcifQ.qv-yDBs4gxG-1p7i06pSNzbGwgQuc4rjPIt6E7vM1gU";
        //这个setSigningKey()在上面创建用的时候是这个 解析的时候还是要这个 保持统一 是保密数据
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
        Object id = claims.get("id", Long.class);
        Object username = claims.get("username", String.class);
        System.out.println("id="+id);
        System.out.println("username="+username);
    }

}
