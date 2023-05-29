package cn.tedu.csmall.passport.config;

import cn.tedu.csmall.commons.web.JsonResult;
import cn.tedu.csmall.commons.web.ServiceCode;
import cn.tedu.csmall.passport.filter.JwtAuthorizationFilter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Configuration

//开启全局的基于方法的安全检查，即：在方法上添加注解来检查权限
//增加这个在配置类上 是为了全局判断哪些操作可以有哪些权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        //不使用密码加密
        return new BCryptPasswordEncoder();
    }

    @Bean   //只能用Bean 因为这是个接口 非定义的类
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //开启Spring Security自带的CorsFilter 以解决跨域问题
        http.cors();

        //配置Spring Security框架使用（创建) Session的策略
        //STATELESS：无状态的 完全不适用Session
        //NEVER: 从不主动创建session 但是 当session已经被创建后，仍会正常使用
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //将JWT过滤器添加到Spring Security框架的某些过滤器之前
            //因为如果先是Spring Security的过滤器检验发现没有验证信息 直接让它滚
            //但其实自己定义的过滤器还没有进行解析jwt 所以还没有验证信息存入到SecurityContext中
            //合理的流程：先用自己的过滤器先执行 再用相关的认证信息存到Security上下文中 然后Security框架再去检验
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);


        //处理未通过认证时访问受保护的资源时拒绝访问  需要接口类型的对象 定义一个匿名内部类
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setContentType("application/json; charset=utf-8");//文档类型 编码格式
                String message = "您当前未登录，请先登录!";
                log.warn(message);
                //此处为什么要加fastjson依赖？因为要传递字符串发送到前端时需要以json形式 此时需要加此依赖转换（反之json也可转为字符串）
                JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
                String s = JSON.toJSONString(jsonResult);//将字符串对象放进去放入 转变为Json格式的字符串
                PrintWriter printWriter = response.getWriter();
                printWriter.println(s);//显示一串？？？ java内部的编码未ISO-8859-1 不支持中文编码这样
                printWriter.close();
            }
        });

        //框架设计了“防止伪造的跨域攻击”的防御机制 默认自定义的post请求不可用 故要配置防御机制
        //禁用“防止伪造的跨域攻击”
        http.csrf().disable();

//        super.configure(http);

        // 白名单
        // 使用1个星号，可以通配此层级的任何资源，例如：/admin/*，可以匹配：/admin/add-new、/admin/list，但不可以匹配：/admin/password/change
        // 使用2个连续的星可以，可以通配若干层级的资源，例如：/admin/**，可以匹配：/admin/add-new、/admin/password/change
        String[] urls = {
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources",
                "/v2/api-docs",
                "/admin/login"
        };

        // 配置授权访问
        // 注意：以下授权访问的配置，是遵循“第一匹配原则”的，即“以最先匹配到的规则为准”
        // 例如：anyRequest()是匹配任何请求，通常，应该配置在最后，表示“除了以上配置过的以外的所有请求”
        // 所以，在开发实践中，应该将更具体的请求配置在靠前的位置，将更笼统的请求配置在靠后的位置
        http.authorizeRequests() // 开始对请求进行授权
//                .mvcMatchers(HttpMethod.OPTIONS, "/**")//所有只要是OPTIONS请求 就放行
//                .permitAll()
                .mvcMatchers(urls) // 匹配某些请求
                .permitAll() // 许可，即不需要通过认证就可以访问
                .anyRequest() // 任何请求
                .authenticated() // 要求已经完成认证的
        ;

        // 如果调用以下方法，当Security认为需要通过认证，但实际未通过认证时，就会跳转到登录页面
        // 如果未调用以下方法，将会响应403错误
//        http.formLogin();
    }
}
