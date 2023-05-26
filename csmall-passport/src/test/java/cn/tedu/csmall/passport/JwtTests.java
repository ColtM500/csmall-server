package cn.tedu.csmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {

    String secretKey = "sbwwwcccccccccccccccccccccccccccccccccccccccccccccc1";//不太简单的 难以预测的字符串

    //生成Jwt
    @Test
    void generate(){

        Map<String, Object> claims = new HashMap<>();
        claims.put("id",9527);
        claims.put("name", "张三");

        String jwt = Jwts.builder()
                //Header
                .setHeaderParam("alg","HS256")
                .setHeaderParam("typ","JWT")
                //Payload
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+3*60*1000))//以毫秒为时间单位的
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
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoi5byg5LiJIiwiaWQiOjk1MjcsImV4cCI6MTY4NTA5MzA2M30.VAMpBScNxYjf2YLffbiObrtHJohJ00aulLOvgGFAWKs";
        //这个setSigningKey()在上面创建用的时候是这个 解析的时候还是要这个 保持统一 是保密数据
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
        Object id = claims.get("id");
        Object name = claims.get("name");
        System.out.println("id="+id);
        System.out.println("name="+name);
    }

}
