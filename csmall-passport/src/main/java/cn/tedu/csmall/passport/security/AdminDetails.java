package cn.tedu.csmall.passport.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class AdminDetails extends User {

    //到时候当事人信息需要取出来id信息的 所以要getter
    //但是不能在类上要@Data注解 因为@Data注解需要父类有无参构造方法 可User中两个构造方法都带参数 故用@Data报错
    @Getter
    private Long id;

    public AdminDetails(Long id,String username, String password, boolean enabled,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled,
                true, true, true, authorities);
        this.id = id;
    }
}
