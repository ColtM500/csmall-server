package cn.tedu.csmall.passport.filter;

import com.sun.corba.se.spi.ior.ObjectKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


/**
 * JWT过滤器，解决的问题：接收JWT，解析JWT，将解析得到的数据创建为认证信息并存入到SecurityContext
 */
@Slf4j
@Component //标记为组件类 然后在配置类中加上配置
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthorizationFilter开始执行……");
        //根据业内管用的做法，客户端会将JWT放在请求头(Request Header)中的Authorization属性中
        String jwt = request.getHeader("Authorization");
        log.debug("客户端携带的JWT:{}",jwt);

        //判断客户端是否携带了有效的JWT
        if (!StringUtils.hasText(jwt)){
            //如果JWT无效，则放行
            filterChain.doFilter(request, response);
            return;
        }

        //TODO: 当前类和AdminServiceImpl中都声明了同样的secretKey变量，是不合理的
        //TODO: 解析JWT过程中可能会出现异常 需要处理
        //尝试解析JWT
        String secretKey = "sbbbcccccccccccccccccc";
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

        Object id = claims.get("id", Long.class);
        Object username = claims.get("username", String.class);
        System.out.println("id="+id);
        System.out.println("username="+username);

        //TODO: 需要考虑使用什么数据作为当事人
        //创建认证信息
        Object principal = username; //可以是任意类型,暂时使用用户名
        Object credentials = null;  //本次不需要
        Collection<GrantedAuthority> authorities = null;
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
