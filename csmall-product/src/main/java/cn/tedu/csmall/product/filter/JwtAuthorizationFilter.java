package cn.tedu.csmall.product.filter;

import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.product.security.LoginPrincipal;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        try {
            claims = Jwts
            .parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwt)
            .getBody();
        } catch (ExpiredJwtException e) {
            //用于返回Json格式的响应给客户端
            String message = "您的登录信息已经过期，请重新登录！";
            log.warn("程序运行过程中出现了ExpiredJwtException，将向客户端响应错误信息！");
            log.warn("错误信息：{}", message);
            //此处为什么要加fastjson依赖？因为要传递字符串发送到前端时需要以json形式 此时需要加此依赖转换（反之json也可转为字符串）
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_JWT_EXPIRED, message);
            String s = JSON.toJSONString(jsonResult);//将字符串对象放进去放入 转变为Json格式的字符串
            PrintWriter printWriter = response.getWriter();
            printWriter.println(s);//显示一串？？？ java内部的编码未ISO-8859-1 不支持中文编码这样
            printWriter.close();
            return;
        } catch (MalformedJwtException e) {
            //用于返回Json格式的响应给客户端
            String message = "非法访问!";
            log.warn("程序运行过程中出现了MalformedJwtException，将向客户端响应错误信息！");
            log.warn("错误信息:{}",message);
            //此处为什么要加fastjson依赖？因为要传递字符串发送到前端时需要以json形式 此时需要加此依赖转换（反之json也可转为字符串）
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_JWT_MALFORMED, message);
            String s = JSON.toJSONString(jsonResult);//将字符串对象放进去放入 转变为Json格式的字符串
            PrintWriter printWriter = response.getWriter();
            printWriter.println(s);//显示一串？？？ java内部的编码未ISO-8859-1 不支持中文编码这样
            printWriter.close();
            return;
        } catch (SignatureException e) {
            //用于返回Json格式的响应给客户端
            String message = "非法访问!";
            log.warn("程序运行过程中出现了SignatureException，将向客户端响应错误信息！");
            log.warn("错误信息：{}", message);
            //此处为什么要加fastjson依赖？因为要传递字符串发送到前端时需要以json形式 此时需要加此依赖转换（反之json也可转为字符串）
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_JWT_SIGNATURE, message);
            String s = JSON.toJSONString(jsonResult);//将字符串对象放进去放入 转变为Json格式的字符串
            PrintWriter printWriter = response.getWriter();
            printWriter.println(s);//显示一串？？？ java内部的编码未ISO-8859-1 不支持中文编码这样
            printWriter.close();
            return;
        } catch (Throwable e) {
            //用于返回Json格式的响应给客户端
            String message = "服务器忙，请稍后再试！【在开发过程中，如果看到此提示，应该检查服务器端的控制台，分析异常，并在解析JWT的过滤器中补充处理对应异常的代码块】";
            log.warn("程序运行过程中出现了Throwable，将向客户端响应错误信息！");
            log.warn("异常：", e);
            //此处为什么要加fastjson依赖？因为要传递字符串发送到前端时需要以json形式 此时需要加此依赖转换（反之json也可转为字符串）
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNKNOWN, message);
            String s = JSON.toJSONString(jsonResult);//将字符串对象放进去放入 转变为Json格式的字符串
            PrintWriter printWriter = response.getWriter();
            printWriter.println(s);//显示一串？？？ java内部的编码未ISO-8859-1 不支持中文编码这样
            printWriter.close();
            return;
        }

        //尝试获取JWT中的数据
        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        String authoritiesJsonString = claims.get("authoritiesJsonString", String.class);

        System.out.println("id="+id);
        System.out.println("username="+username);

        //创建认证信息
        Object principal = new LoginPrincipal().setId(id).setUsername(username);
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
