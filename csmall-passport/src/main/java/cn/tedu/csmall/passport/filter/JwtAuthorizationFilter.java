package cn.tedu.csmall.passport.filter;

import cn.tedu.csmall.passport.security.LoginPrincipal;
import com.alibaba.fastjson.JSON;
import com.sun.corba.se.spi.ior.ObjectKey;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


/**
 * JWT过滤器，解决的问题：接收JWT，解析JWT，将解析得到的数据创建为认证信息并存入到SecurityContext
 */
@Slf4j
@Component //标记为组件类 然后在配置类中加上配置
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${csmall.jwt.secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter开始执行……");

        //根据业内惯用的做法，客户端会将JWT放在请求头(Request Header)中的Authorization属性中
        String jwt = request.getHeader("Authorization");
        log.debug("客户端携带的JWT:{}",jwt);
        log.debug("输出当前的secretKey1: "+secretKey);

        //判断客户端是否携带了有效的JWT
        if (!StringUtils.hasText(jwt)){
            //如果JWT无效，则放行
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("输出当前的secretKey11: "+secretKey);

        //尝试解析JWT
        response.setContentType("application/json; charset=utf-8");
        Claims claims = null;
            claims = Jwts
            .parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwt)
            .getBody();

        //尝试获取JWT中的数据
        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        String authoritiesJsonString = claims.get("authoritiesJsonString", String.class);
        System.out.println("id="+id);
        System.out.println("username="+username);

        //创建当事人数据
        LoginPrincipal loginPrincipal = new LoginPrincipal()
                .setId(id).setUsername(username);

        //创建认证信息
        Object principal = loginPrincipal; //可以是任意类型,暂时使用用户名
        Object credentials = null;  //本次不需要
        Collection<SimpleGrantedAuthority> authorities =
                JSON.parseArray(authoritiesJsonString, SimpleGrantedAuthority.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,credentials,authorities
        );

        //将认证结果存入到SecurityContext中
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //放行
        filterChain.doFilter(request, response);
    }
}
