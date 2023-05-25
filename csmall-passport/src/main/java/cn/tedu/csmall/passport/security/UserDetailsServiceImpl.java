package cn.tedu.csmall.passport.security;

import cn.tedu.csmall.passport.mapper.AdminMapper;
import cn.tedu.csmall.passport.pojo.vo.AdminLoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用这个类实现UserDetailsService接口 实现使用自定义的账号登录
 */
@Slf4j
@Service    //不需要加Component注解 Service上本来就包含了它
//用自定义组件类 实现这个UserDetailsService的接口
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper mapper;

    //项目中存在UserDetailsService接口类型的组件时，尝试登录时，框架会自动使用登录表单中输入的用户名调用loadUserByUsername() 这个用户名就是参数s
    //此结果中应该包含用户的相关信息 密码、账号状态、权限等等
    //然后 框架会自动判断将该账号是否启用或禁用
    //验证密码（在UserDetails中的密码与登录表单中的密码是否匹配等 从而决定此次是否登录成功
    //以下方法只需要 完成 “根据用户名返回匹配的用户详情即可”
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.debug("Spring Security框架自动调用了UserDetailsServiceImpl.loadUserByUsername()方法，用户名：{}", s);
        //根据用户名从数据库查询匹配的用户信息
        AdminLoginInfoVO loginInfoVO = mapper.getLoginInfoByUsername(s);
        if (loginInfoVO==null){
            log.debug("此用户没有匹配的用户数据，将返回null");
            return null;
        }

        log.debug("用户名匹配成功!准备返回此用户匹配的UserDetails类型的对象");
        UserDetails userDetails = User.builder()
                .username(loginInfoVO.getUsername())
                .password(loginInfoVO.getPassword())
                .disabled(loginInfoVO.getEnable()==0)// 账号状态是否禁用
                .accountLocked(false)// 账号状态是否锁定
                .accountExpired(false)// 账号状态是否过期
                .credentialsExpired(false) // 账号的凭证是否过期
                .authorities("这是一个临时使用的山寨的权限！！！")// 权限
                .build();
        log.debug("即将向Spring Security返回UserDetails类型的对象:{}", userDetails);
        return userDetails;
    }
}
