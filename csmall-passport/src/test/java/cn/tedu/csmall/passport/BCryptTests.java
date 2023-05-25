package cn.tedu.csmall.passport;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
public class BCryptTests {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void encode(){
        String rawPassword = "123456";
        System.out.println("原文: "+ rawPassword);

        for (int i = 0; i < 5; i++) {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            System.out.println("密文： " + encodedPassword);
        }
    }

    @Test
    void matches(){
        String rawPassword = "123456";
        System.out.println("原文: " + rawPassword);

        String encodedPassword = "$2a$10$N.ZOn9G6/YLFixAOPMg/h.z7pCu6v2XyFDtC4q.jeeGm/TEZyj15C";
        System.out.println("密文: " + encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println(result);

    }
}
