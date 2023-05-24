package cn.tedu.csmall.passport.mapper;

import cn.tedu.csmall.passport.pojo.entity.Admin;
import cn.tedu.csmall.passport.pojo.entity.AdminRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class AdminRoleMapperTests {

    @Autowired
    AdminRoleMapper mapper;

    @Test
    void insert(){
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(1L);
        adminRole.setRoleId(1L);
        adminRole.setGmtCreate(LocalDateTime.now());
        adminRole.setGmtModified(LocalDateTime.now());

        long start = System.currentTimeMillis();
        for (int i=0; i<10; i++){
            mapper.insert(adminRole);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Test
    void insertBatch(){
        AdminRole[] adminRoleList = new AdminRole[10];
        for (int i = 0; i < adminRoleList.length; i++) {
            AdminRole adminRole = new AdminRole();
            adminRole.setRoleId(1L);
            adminRole.setGmtCreate(LocalDateTime.now());
            adminRole.setGmtModified(LocalDateTime.now());
            adminRoleList[i] = adminRole;
        }

        long start = System.currentTimeMillis();
        int rows = mapper.insertBatch(adminRoleList); // 800
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("批量插入数据完成，受影响的行数：" + rows);
    }

}
