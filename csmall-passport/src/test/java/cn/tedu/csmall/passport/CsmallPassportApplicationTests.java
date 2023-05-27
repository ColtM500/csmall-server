package cn.tedu.csmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class CsmallPassportApplicationTests {

    @Value("${csmall.jwt.secret-key}")
    String secretKey;

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
        System.out.println(secretKey);
    }

    @Test
    void getConnection() throws Throwable{
        dataSource.getConnection();
    }
}
